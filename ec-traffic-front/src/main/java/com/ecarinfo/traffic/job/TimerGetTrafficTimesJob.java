package com.ecarinfo.traffic.job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.rm.TaskInfoRM;
import com.ecarinfo.traffic.service.TrafficService;
import com.ecarinfo.utils.HainanTrafficUtils.HainanTrafficInfo;

public class TimerGetTrafficTimesJob extends BaseJob {

	Logger logger = Logger.getLogger(TimerGetTrafficTimesJob.class);
	private Lock lock = new ReentrantLock();

	@Resource
	private TrafficService trafficService;
	@Override
	public void run() {
		if (lock.tryLock()) {
			logger.info("task:" + this.getClass().getSimpleName()
					+ " getLock SUCCESS!");
			execute();
		} else {
			logger.info("task:" + this.getClass().getSimpleName()
					+ " getLock FAILED!");
		}
	}

	public void execute() {
		ECPage<CarInfo> pageHolder = null;
		int page = 1;
		int interfaceId = 1;
		int count = 0;//返回总数据条数
		logger.info("=====================TimerGetTrafficTimesJob begin==========interfaceId:"+interfaceId);
		pageHolder = taskService.findTaskCars(interfaceId, page);
		//任务状态改变
		genericDao.updateWithCriteria(TaskInfo.class, new Criteria().update(TaskInfoRM.status, "processing")
				.update(TaskInfoRM.utime, DateUtils.dateToString(new Date(),
						TimeFormatter.FORMATTER1)).update(TaskInfoRM.searchStime, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1))
						.eq(TaskInfoRM.interfaceId, interfaceId).eq(TaskInfoRM.status, "ready", CondtionSeparator.AND));
		//调用接口爬取违章信息
		while (pageHolder != null && pageHolder.getList() != null
				&& pageHolder.getList().size() > 0) {
			for (CarInfo car : pageHolder.getList()) {
				logger.debug(String
						.format("---------------------page(%s) TrafficInfoGripJob process car(%s,%s, %s)",
								page, car.getId(), car.getCarNo(),
								car.getCarFrameNo()));
				if (car.getId() != null && car.getId() > 0
						&& StringUtils.isNotEmpty(car.getCarNo())
						&& StringUtils.isNotEmpty(car.getCarFrameNo())) {
					try {
						logger.info(String
								.format("-------------------trafficRpcForMessageCenterFacade.gripCarTraffics(%s,%s, %s)",
										car.getId(), car.getCarNo(),
										car.getCarFrameNo()));
						List<HainanTrafficInfo> list =	this.trafficService.gripCarTraffics(car.getId(), car.getCarNo(), car.getCarFrameNo());
						if (list != null && list.size() > 0) {
							
							count+=list.size();
							
						}
						
					} catch (Exception e) {
						logger.error(
								String.format(
										"-------------------error for gripCarTraffics(%s,%s, %s)",
										car.getId(), car.getCarNo(),
										car.getCarFrameNo()), e);
					}
				}
			}
			page++;
			pageHolder = taskService.findTaskCars(interfaceId, page);
		}
		//完成状态done
		genericDao.updateWithCriteria(TaskInfo.class, new Criteria().update(TaskInfoRM.status, "done").update(TaskInfoRM.searchEtime,DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1))
				.update(TaskInfoRM.utime,  DateUtils.dateToString(new Date(),
						TimeFormatter.FORMATTER1)).update(TaskInfoRM.responseNums, count).eq(TaskInfoRM.interfaceId, interfaceId).eq(TaskInfoRM.status, "processing", CondtionSeparator.AND));
		logger.info("=====================TimerGetTrafficTimesJob end=====================");
	}

	
	
	private static void main(String []s ){
		
	}
}
