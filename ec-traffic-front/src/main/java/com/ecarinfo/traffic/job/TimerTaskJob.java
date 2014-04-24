package com.ecarinfo.traffic.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.alibaba.druid.stat.TableStat.Condition;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.traffic.dao.TaskCarinfoDao;
import com.ecarinfo.traffic.dto.TaskCarinfoBase;
import com.ecarinfo.traffic.dto.TaskInfoDto;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.TaskCarinfo;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.rm.InterfaceCarinfoRM;
import com.ecarinfo.traffic.rm.InterfaceInfoRM;
import com.ecarinfo.traffic.rm.TaskInfoRM;
import com.ecarinfo.utils.EcNumUtil;

public class TimerTaskJob extends BaseJob {

	protected static Logger logger = Logger.getLogger(TimerTaskJob.class);
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		if (lock.tryLock()) {
			logger.info("---------------" + this.getClass().getSimpleName()
					+ " getLock SUCCESS!");
			try {
				// 在这里写你要执行的内容
				System.out.println("任务开始");

				/*
				 * 1.查询出所有绑定车辆的接口 2.任务每天生成，过滤车辆（生成任务时，更新接口车辆表的时间，下次生成任务根据此时间过滤）
				 */
				List<InterfaceInfo> list = this.taskService
						.findInterfaceCarinfo();
				List<InterfaceCarinfo> icinfo = null;
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				//读取资源配置的查询周期
				Integer day=new Integer(PropUtil.getProperties("timer.properties").getProperty("car.query.day"));
				calendar.add(Calendar.DAY_OF_MONTH, -day);//时间间隔
				String curDate = s.format(calendar.getTime()); // 当前日期

				if (list != null && list.size() > 0) {
					System.out.println("size:" + list.size());
					// 生成任务
					for (InterfaceInfo info : list) {
						List<TaskCarinfo> tasklist = new ArrayList<TaskCarinfo>();
//						TaskInfo task = this.genericDao.findOne(
//								TaskInfo.class,
//								new Criteria().eq(TaskInfoRM.interfaceId,
//										info.getId()));
						// if (task != null) {// 任务存在就跳出
						// continue;
						// } else {
						// 生成任务查询接口车辆 查询时间间隔大于3天
						icinfo = this.taskService.findInterfaceCars(info.getId(), curDate);
						
						if (icinfo != null&&icinfo.size()>0) {
							TaskInfo task = new TaskInfo();
							Date date = new Date();
							task.setInterfaceId(info.getId());
							task.setRequestNums(icinfo.size());
							task.setCtime(date);
							// task.setRequestTime(date);
							task.setStatus("ready");
							task.setTaskNo(EcNumUtil.getBsnum());
							this.genericDao.insert(task);

							// 同步接口车辆到任务中
							TaskInfo taskInfo = this.genericDao.findOne(
									TaskInfo.class,
									new Criteria().eq(TaskInfoRM.taskNo,
											task.getTaskNo()));
							// 封装
							for (InterfaceCarinfo carinfo : icinfo) {
								TaskCarinfo taskcar = new TaskCarinfo();
								taskcar.setCarId(carinfo.getCarId());
								taskcar.setTaskId((long) taskInfo.getId());
								taskcar.setCtime(date);
								tasklist.add(taskcar);
								this.genericDao
										.updateWithCriteria(
												InterfaceCarinfo.class,
												new Criteria()
														.update(InterfaceCarinfoRM.lastRequestDate,
																DateUtils
																		.dateToString(
																				date,
																				TimeFormatter.YYYY_MM_DD))
														.eq(InterfaceCarinfoRM.carId,
																carinfo.getCarId())
														.eq(InterfaceCarinfoRM.interfaceId,
																info.getId(),
																CondtionSeparator.AND));

							}
							// 批量保存
							saveTaskCarWithBatch(tasklist);
						}
					}
				}

				System.out.println("任务结束:list===" + list.size());

			} catch (Exception e) {
				logger.error("-------------解析信息发生异常--------------", e);
			}
		} else {
			logger.info("---------------" + this.getClass().getSimpleName()
					+ " getLock FAILED!");
		}
	}

	/*
	 * 同步接口中车辆到任务车辆表中，已存在的过滤掉
	 */
	private List<TaskCarinfo> validateCar(List<TaskInfoDto> ilist,
			List<TaskCarinfo> tlist) {

		List<TaskCarinfo> tasklist = new ArrayList<TaskCarinfo>();
		List<TaskCarinfo> tlist2 = new ArrayList<TaskCarinfo>();

		if (ilist != null && ilist.size() > 0) {
			for (TaskInfoDto icinfo : ilist) {
				TaskCarinfoBase taskcar = new TaskCarinfoBase();
				taskcar.setCarId(icinfo.getCarId());
				taskcar.setTaskId((long) icinfo.getTaskId());
				taskcar.setCtime(new Date());
				tasklist.add(taskcar);
			}
		}
		// 去掉重复的
		if (tlist != null && tlist.size() > 0) {
			for (TaskCarinfo info : tasklist) {
				if (!tlist.contains((TaskCarinfoBase) info)) {
					tlist2.add(info);
				}
			}
			return tlist2;
		} else {
			return tasklist;
		}
	}

	// 批量保存
	private void saveTaskCarWithBatch(List<TaskCarinfo> list) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,
				false);
		int idx = 1;
		// Iterator<CustomerCarinfo> it = set.iterator();
		try {
			for (TaskCarinfo info : list) {
				session.insert(TaskCarinfoDao.class.getName() + ".insert", info);
				if (idx % 50 == 0) {// 100条提交事务
					session.commit();
				}
				idx++;
			}
			session.commit();
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}

			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
