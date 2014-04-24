package com.ecarinfo.traffic.service.impl;

import java.util.HashMap;
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
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.TaskInfoDao;
import com.ecarinfo.traffic.dto.TaskInfoDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.ProvinceInfo;
import com.ecarinfo.traffic.po.ServiceCity;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.InterfaceCarinfoRM;
import com.ecarinfo.traffic.rm.InterfaceInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.rm.TaskCarinfoRM;
import com.ecarinfo.traffic.rm.TaskInfoRM;
import com.ecarinfo.traffic.service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskInfoDao taskInfoDao;
	
	@Resource
	private GenericDao genericDao;

	@Override
	public List<InterfaceInfo> findInterfaceCarinfo() {
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);
		whereBy.eq("cc."+CustomerCarinfoRM.isExpired,1);
		whereBy.groupBy("ii." + InterfaceInfoRM.pk);
		whereBy.orderBy(
				"ii." + InterfaceInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = taskInfoDao
				.findInterfaceInfoByCriteria(whereBy);
		List<InterfaceInfo> list = RowMapperUtils
				.map2List(map, InterfaceInfo.class);
		return list;
	}


	@Override
	public List<TaskInfoDto> findInterfaceCar() {
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);
		whereBy.eq("cc."+CustomerCarinfoRM.isExpired,1,CondtionSeparator.AND);
		whereBy.orderBy(
				"ic." + InterfaceInfoRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = taskInfoDao.findInterCarInfoByCriteria(whereBy);
		List<TaskInfoDto> list = RowMapperUtils
				.map2List(map, TaskInfoDto.class);
		return list;
	}


	@Override
	public ECPage<CarInfo> findTaskCars(Integer interfaceId,Integer page) {
		Map<Integer, List<CarInfo>> map=new HashMap<Integer, List<CarInfo>>();
		int pagerOffset = page;
		try{
			if(StringUtils.isNotEmpty(interfaceId.toString())){
//				Integer carFrameNo=0;
//				Integer CarEngineNo=0;
//				Integer carRegisterNo=0;
//				/*
//				 * 根据城市信息配置返回数据的结构，是否要车架,证书号,引擎号及位数
//				 */
//				ServiceCityInterface cityInterface = this.genericDao.findOne(ServiceCityInterface.class, new Criteria().eq(ServiceCityInterfaceRM.interfaceId, interfaceId));
//				if(cityInterface.getCityId()!=null&&cityInterface.getCityId()>0){
//					ServiceCity city = this.genericDao.findOne(ServiceCity.class, new Criteria().eq( ServiceCityRM.pk,cityInterface.getCityId()));
//					carFrameNo = city.getCarFrameNo();
//					CarEngineNo = city.getCarEngineNo();
//					carRegisterNo = city.getCarRegisterNo();
//				}else if(cityInterface.getCityPid()!=null&&cityInterface.getCityPid()>0){
//					ProvinceInfo province = this.genericDao.findOne(ProvinceInfo.class,new Criteria().eq( ServiceCityRM.pk,cityInterface.getCityId()));
//					carFrameNo = province.getCarFrameNo();
//					CarEngineNo = province.getCarEngineNo();
//					carRegisterNo = province.getCarRegisterNo();
//				}
				TaskInfo taskinfo = this.taskInfoDao.findOne(new Criteria().eq(TaskInfoRM.interfaceId, interfaceId).eq(TaskInfoRM.status,"ready",CondtionSeparator.AND));
				if(taskinfo==null){
					return null;
				}
				Criteria whereBy = new Criteria();
				whereBy.eq("tc."+TaskCarinfoRM.taskId, taskinfo.getId());
				whereBy.eq("cc."+CustomerCarinfoRM.isDisable, 1, CondtionSeparator.AND);
				whereBy.eq("cc."+CustomerCarinfoRM.isExpired, 1, CondtionSeparator.AND);
				long counts = taskInfoDao.findCarInfoCountByCriteria(whereBy);
				whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
						CarInfoRM.pk, OrderType.ASC);

				List<Map<String, Object>> map2 = taskInfoDao.findTaskCarsByCriteria(whereBy);
				List<CarInfo> carlist = RowMapperUtils.map2List(map2, CarInfo.class);
		

//				if(carlist!=null&&carlist.size()>0){
//					//根据配置组装carinfo
//					for(CarInfo carinfo:carlist){
//						if(carFrameNo>0){
//							carinfo.setCarFrameNo(carinfo.getCarFrameNo().substring((carinfo.getCarFrameNo().length()-carFrameNo)>0?
//									carinfo.getCarFrameNo().length()-carFrameNo:0
//									));
//						}
//						if(CarEngineNo>0){
//							carinfo.setCarEngineNo(carinfo.getCarEngineNo().substring((carinfo.getCarEngineNo().length()-CarEngineNo)>0?
//									carinfo.getCarEngineNo().length()-CarEngineNo:0
//									));
//						}
//						if(carRegisterNo>0){
//							carinfo.setCarRegistNo(carinfo.getCarRegistNo().substring((carinfo.getCarRegistNo().length()-carRegisterNo)>0?
//									carinfo.getCarRegistNo().length()-carRegisterNo:0
//									));
//						}
//					}
//				}

				ECPage<CarInfo> pageCar = PageHelper.list(counts, carlist, whereBy);
				return pageCar;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}


	@Override
	public List<InterfaceCarinfo> findInterfaceCars(Integer interfaceId,
			String day) {
		Criteria whereBy = new Criteria();
		whereBy.eq("cc."+CustomerCarinfoRM.isDisable, 1);
		whereBy.eq("cc."+CustomerCarinfoRM.isExpired, 1, CondtionSeparator.AND);
		whereBy.eq("ic."+InterfaceCarinfoRM.interfaceId, interfaceId,CondtionSeparator.AND);
		whereBy.lessThenOrEquals(
				"ic."+InterfaceCarinfoRM.lastRequestDate,
				day, CondtionSeparator.AND).isNull("ic."+InterfaceCarinfoRM.lastRequestDate, CondtionSeparator.OR);
		whereBy.orderBy(
				"ic."+InterfaceCarinfoRM.carId, OrderType.ASC);

		List<Map<String, Object>> map = taskInfoDao
				.findInterfaceCarsByCriteria(whereBy);
		List<InterfaceCarinfo> list = RowMapperUtils
				.map2List(map, InterfaceCarinfo.class);
		return list;
	}
	

}
