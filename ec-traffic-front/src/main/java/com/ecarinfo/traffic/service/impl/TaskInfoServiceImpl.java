package com.ecarinfo.traffic.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.TaskInfoDao;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.service.TaskInfoService;
import com.ecarinfo.traffic.service.TaskService;
import com.ecarinfo.traffic.view.CarInfoView;

@Service("taskInfoService")
public class TaskInfoServiceImpl implements TaskInfoService {

	@Resource
	private GenericDao genericDao;

	@Resource
	private CarInfoDao carInfoDao;

	@Resource
	private TaskInfoDao taskInfoDao;

	@Override
	public void saveTaskInfo(TaskInfo dto) {
		Date date = new Date();
		dto.setCtime(date);
		dto.setStatus("ready");
		taskInfoDao.insert(dto);
		CarInfo carinfo = new CarInfo();

	}

	@Override
	public ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);
		long counts = carInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"car." + CarInfoRM.pk, OrderType.DESC);

		List<Map<String, Object>> map = carInfoDao
				.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public ECPage<CarInfoView> queryCarInfoNobindPages(String customerName,
			String carNo, String startTime, String endTime) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1).isNull(
				"car." + CarInfoRM.interfaceId, CondtionSeparator.AND);

		if (StringUtils.isNotEmpty(customerName)) {
			whereBy.like("cus." + CustomerInfoRM.name,
					"%" + customerName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like("car." + CarInfoRM.carNo, "%" + carNo + "%",
					CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals("car." + CarInfoRM.utime, startTime
					+ " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals("car." + CarInfoRM.utime, endTime
					+ " 23:59:59", CondtionSeparator.AND);
		}
		long counts = carInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"car." + CarInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = carInfoDao
				.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public CarInfoView findByCarId(Integer carid) {
		// TODO Auto-generated method stub
		Criteria whereBy = new Criteria();
		if (carid != null) {
			whereBy.eq("car." + CarInfoRM.id, carid);
		}
		List<Map<String, Object>> map = carInfoDao
				.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);
		return list.get(0);
	}

	@Override
	public void task(TaskService taskService,SqlSessionFactory sqlSessionFactory) {

		Calendar calendar = Calendar.getInstance();

		/*** 定制每日2:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date date = calendar.getTime(); // 第一次执行定时任务的时间

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}
	}

	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}
