package com.ecarinfo.traffic.service;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.TaskInfoDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.view.CarInfoView;

public interface TaskService {
	
	/*
	 * 查询有绑定车辆的接口
	 */
	List<InterfaceInfo> findInterfaceCarinfo();
	
	/*
	 * 查询接口绑定车辆
	 */
	List<InterfaceCarinfo> findInterfaceCars(Integer interfaceId,String day);
	
	/*
	 * 查询接口中的绑定车辆 
	 */
	List<TaskInfoDto> findInterfaceCar();
	
	/*
	 * @Description 提供给接口的任务车辆
	 * @param interfaceId
	 */
	ECPage<CarInfo> findTaskCars(Integer interfaceId,Integer page);
	
}
