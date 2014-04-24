package com.ecarinfo.traffic.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.CustomerCarinfoDao;
import com.ecarinfo.traffic.dao.InterfaceCarinfoDao;
import com.ecarinfo.traffic.dao.InterfaceInfoDao;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.CustomerCarinfo;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.InterfaceCarinfoRM;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.rm.TrafficItemsRM;
import com.ecarinfo.traffic.service.CarInfoService;
import com.ecarinfo.traffic.view.CarInfoView;

@Service("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {

	@Resource
	private GenericDao genericDao;

	@Resource
	private CarInfoDao carInfoDao;

	@Resource
	InterfaceCarinfoDao interfaceCarinfoDao;

	@Resource
	InterfaceInfoDao interfaceInfoDao;
	@Resource
	CustomerCarinfoDao customerCarinfoDao;

	@Override
	public void saveCarInfo(CarInfoDto dto) {
		try {
			CarInfo carinfo = new CarInfo();
			Date date = new Date();
			carinfo.setCarEngineNo(dto.getCarEngineNo());
			carinfo.setCarFrameNo(dto.getCarFrameNo());
			carinfo.setCarNo(dto.getCarNo());
			carinfo.setCarRegistNo(dto.getCarRegistNo());
			carinfo.setCtime(date);
			carinfo.setUtime(date);
			// carinfo.setIsValid(dto.getIsValid());//是否审核

			carinfo.setUserName(DtoUtil.decode(dto.getUserName()));

			genericDao.insert(carinfo);
			CarInfo car = genericDao.findOne(CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, dto.getCarNo()));
			CustomerCarinfo ccinfo = new CustomerCarinfo();
			ccinfo.setCarId(car.getId());
			ccinfo.setCustomerId(dto.getCustomerId());
			ccinfo.setIsAllowchange(dto.getIsAllowchange());
			ccinfo.setCtime(date);
			ccinfo.setIsExpired(dto.getIsExpired());
			ccinfo.setIsDisable(1);// 可以用，删除设置0
			genericDao.insert(ccinfo);
			// TODO Auto-generated method stub
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public ECPage<CarInfoView> queryCarInfoPages(Integer disabled,
			Integer customerId, String carNo, String carFrameNo,
			String carEngineNo, String carRegistNo, String startDate,
			String endDate) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);
		// 公司
		if (customerId!=null) {
			whereBy.eq("cc."+ CustomerCarinfoRM.customerId, customerId,CondtionSeparator.AND);
		}
		// 车牌
		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like("car." + CarInfoRM.carNo, "%" + carNo + "%",
					CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carFrameNo)) {
			whereBy.eq("car."+CarInfoRM.carFrameNo,carFrameNo, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carEngineNo)) {
			whereBy.eq("car."+ CarInfoRM.carEngineNo,carEngineNo,CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carRegistNo)) {
			whereBy.eq("car."+ CarInfoRM.carRegistNo,carRegistNo, CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startDate)) {
			whereBy.greateThenOrEquals("cc." + CustomerCarinfoRM.ctime,
					startDate + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			whereBy.lessThenOrEquals("cc." + CustomerCarinfoRM.ctime, endDate
					+ " 23:59:59", CondtionSeparator.AND);
		}
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
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);

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
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public ECPage<CarInfoView> queryCarInfoNobindPages(Integer interfaceId,
			Integer cityId, Integer cityPid, String customerName, String carNo,
			String startTime, String endTime) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria().eq("cc."
				+ CustomerCarinfoRM.isDisable, 1);
		//接口本身或者其他接口已绑定的排除掉
		ServiceCityInterface serviceCityInterface = this.genericDao.findOne(ServiceCityInterface.class, new Criteria().eq(ServiceCityInterfaceRM.interfaceId,interfaceId ));
		//查询同一城市的接口 
		List<ServiceCityInterface> interfacelist = this.genericDao.findList(ServiceCityInterface.class, new Criteria().
				eq(ServiceCityInterfaceRM.cityId, serviceCityInterface.getCityId()).eq(ServiceCityInterfaceRM.cityPid,serviceCityInterface.getCityPid(),CondtionSeparator.AND));
		String interfaceList[];
		List<InterfaceCarinfo> carlist=null;
		if(interfacelist!=null && interfacelist.size()>0){
			interfaceList =new String[interfacelist.size()];
			for(int i = 0; i < interfacelist.size(); i++){
				interfaceList[i] = interfacelist.get(i).getInterfaceId().toString();
				
			}
			carlist = this.interfaceCarinfoDao
					.findList(new Criteria().
							in(InterfaceCarinfoRM.interfaceId,
							interfaceList));
		}
		
	
		Long ob[];
		if (carlist != null && carlist.size() > 0) {
			ob = new Long[carlist.size()];
			for (int i = 0; i < carlist.size(); i++) {
				ob[i] = carlist.get(i).getCarId();
			}
			whereBy.notIn("cc." + CustomerCarinfoRM.carId, ob,
					CondtionSeparator.AND);
		}

		whereBy.eq("cui." + CustomerInfoRM.cityId, cityId,
				CondtionSeparator.AND);
		whereBy.eq("cui." + CustomerInfoRM.cityPid, cityPid,
				CondtionSeparator.AND);

		if (StringUtils.isNotEmpty(customerName)) {
			whereBy.like("cui." + CustomerInfoRM.name,
					"%" + customerName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(carNo)) {
			whereBy.like("ci." + CarInfoRM.carNo, "%" + carNo + "%",
					CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals("cc." + CustomerCarinfoRM.ctime,
					startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals("cc." + CustomerCarinfoRM.ctime, endTime
					+ " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.groupBy("cc." + CustomerCarinfoRM.carId);

		long counts = interfaceInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"ci." + CarInfoRM.pk, OrderType.DESC);
		List<Map<String, Object>> map = interfaceInfoDao
				.findCarNobindByCriteria(whereBy);

		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

}
