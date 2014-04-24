package com.ecarinfo.traffic.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.CustomerCarinfoDao;
import com.ecarinfo.traffic.dao.ServiceCityDao;
import com.ecarinfo.traffic.dao.TrafficItemsDao;
import com.ecarinfo.traffic.po.CustomerCarinfo;
import com.ecarinfo.traffic.po.TrafficItems;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.rm.TrafficItemsRM;
import com.ecarinfo.traffic.service.TrafficService;
import com.ecarinfo.traffic.view.CarInfoView;
import com.ecarinfo.traffic.view.ServiceCityView;
import com.ecarinfo.traffic.view.TrafficItemsView;
import com.ecarinfo.utils.HainanTrafficUtils;
import com.ecarinfo.utils.HainanTrafficUtils.HainanTrafficInfo;

@Service("trafficService")
public class TrafficServiceImpl implements TrafficService {
	private static final Logger logger = Logger.getLogger(TrafficServiceImpl.class);
	@Resource
	private ServiceCityDao serviceCityDao;

	@Resource
	private TrafficItemsDao trafficItemsDao;
	@Resource
	private CustomerCarinfoDao customerCarinfoDao;

	public ECPage<ServiceCityView> queryCityInfoPages(Integer disabled) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		long counts = serviceCityDao.findCityInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"sc." + ServiceCityRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = serviceCityDao
				.findCityInfoByCriteria(whereBy);
		List<ServiceCityView> list = RowMapperUtils.map2List(map,
				ServiceCityView.class);

		ECPage<ServiceCityView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public ECPage<TrafficItemsView> queryTrafficPages(Integer customerId,
			String carNo, String carFrameNo, String carEngineNo,
			String carRegistNo, String startDate, String endDate) {
		Criteria whereBy = new Criteria().eq("1", 1);
		int pagerOffset = SystemContext.getPageOffset();
		// 公司

		List<CustomerCarinfo> ccinfo = customerCarinfoDao
				.findList(new Criteria().eq(CustomerCarinfoRM.customerId,
						customerId));
		Long ob[];
		if (ccinfo != null && ccinfo.size() > 0) {
			ob = new Long[ccinfo.size()];
			for (int i = 0; i < ccinfo.size(); i++) {
				ob[i] = ccinfo.get(i).getCarId();
			}
			whereBy.in("ti." + TrafficItemsRM.carId, ob, CondtionSeparator.AND);
		}

		// 车牌
		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like("ci." + CarInfoRM.carNo,"%"+ carNo+"%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carFrameNo)) {
			whereBy.eq("ci."+CarInfoRM.carFrameNo,carFrameNo, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carEngineNo)) {
			whereBy.eq("ci."+CarInfoRM.carEngineNo,carEngineNo, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carRegistNo)) {
			whereBy.eq("ci."+ CarInfoRM.carRegistNo,carRegistNo, CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startDate)) {
			whereBy.greateThenOrEquals("ti." + TrafficItemsRM.ctime, startDate
					+ " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			whereBy.lessThenOrEquals(
					"ti." + TrafficItemsRM.ctime ,endDate+ " 23:59:59",
					CondtionSeparator.AND);
		}

		long counts = trafficItemsDao.findTrafficItemsCountByCustomer(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"ti." + TrafficItemsRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = trafficItemsDao
				.findTrafficItemsByCustomer(whereBy);
		List<TrafficItemsView> list = RowMapperUtils.map2List(map,
				TrafficItemsView.class);

		ECPage<TrafficItemsView> page = PageHelper.list(counts, list, whereBy);
		// return page;
		// ECPage<TrafficItems> page1 =PagingManager.list(trafficItemsDao, new
		// Criteria().setPage(1, 20));
		return page;
	}

	@Override
	public List<TrafficItemsView> findTrafficItemsByCustomerId(
			Integer customerId,String startDate,String endDate) {
		Criteria whereBy = new Criteria().eq("1", 1);
		int pagerOffset = SystemContext.getPageOffset();
		// 公司

		List<CustomerCarinfo> ccinfo = customerCarinfoDao
				.findList(new Criteria().eq(CustomerCarinfoRM.customerId,
						customerId));
		Long ob[];
		
		if (ccinfo != null && ccinfo.size() > 0) {
			ob = new Long[ccinfo.size()];
			for (int i = 0; i < ccinfo.size(); i++) {
				ob[i] = ccinfo.get(i).getCarId();
			}
		whereBy.in("ti." + TrafficItemsRM.carId, ob, CondtionSeparator.AND);
		if (StringUtils.isNotEmpty(startDate)) {
			whereBy.greateThenOrEquals("ti." + TrafficItemsRM.trafficTime, startDate
					+ " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			whereBy.lessThenOrEquals(
					"ti." + TrafficItemsRM.trafficTime ,endDate+ " 23:59:59",
					CondtionSeparator.AND);
		}

		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"ti." + TrafficItemsRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = trafficItemsDao
				.findTrafficItemsByCustomer(whereBy);
		List<TrafficItemsView> list = RowMapperUtils.map2List(map,
				TrafficItemsView.class);

//		ECPage<TrafficItemsView> page = PageHelper.list(counts, list, whereBy);
		// return page;
		// ECPage<TrafficItems> page1 =PagingManager.list(trafficItemsDao, new
		// Criteria().setPage(1, 20));
		return list;
		}else{
			return null;
		}
	}

	@Override
	public List<HainanTrafficInfo> gripCarTraffics(Long carId, String car_no, String car_frame_no) {
		if (!car_no.startsWith("琼")) {
			return null ; //暂时只查海南的车牌
		}
		logger.info(String.format("------------------gripCarTraffics:carId(%s),carNo(%s), car_frame_no(%s)", carId, car_no, car_frame_no));
		//抓取违章信息
		List<HainanTrafficInfo> traffics = null;
		try {
			traffics = HainanTrafficUtils.getTrafficInfos(car_no, car_frame_no);
		} catch (IOException e) {
			logger.error(String.format("ERROR by call HainanTrafficUtils.getTrafficInfos(%s, %s)", car_no, car_frame_no),e);
			return null;
		}
		
		//保存违章信息
//		baseService.delete(EtTrafficItemDao.class, new Criteria().eq(EtTrafficItemRM.carId, carId, CondtionSeparator.NONE));
		if (!CollectionUtils.isEmpty(traffics)) {
			for (HainanTrafficInfo info : traffics) {
				TrafficItems item = new TrafficItems();
				item.setAddress(info.getAddress());
				item.setCarId(carId);
				item.setCarNo(info.getCarNo());
				if (info.getTime()!=null) {
					try {
						item.setTrafficTime(DateUtils.stringToDate(info.getTime(),TimeFormatter.FORMATTER1));
					} catch (Exception e) {		
						logger.error(" item.setTrafficTime(DateUtils.stringToDate(info.getTime())) error", e);
					}
				}
				item.setItem(info.getTrafficType());
				item.setCtime(new Date());
				item.setUnit(info.getExecutedOrg());
				this.trafficItemsDao.insert(item);
			}			
			//给app发送违章提醒
//			List<Long> carIds= new ArrayList<Long>();
//			carIds.add(carId);
//			esSystemNoticeService.newSystemNoticeWith5s(NoticeType.TYPE_WEIZHANG, "您有未处理的车辆违章信息", 1, carIds);
			logger.info("----------------esSystemNoticeService.newSystemNoticeWith5s called, carId:" + carId + " ,traffics nums:" + traffics.size());
		}	
		return traffics;
	}

}
