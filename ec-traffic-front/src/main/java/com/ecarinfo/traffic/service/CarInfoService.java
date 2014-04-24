package com.ecarinfo.traffic.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.view.CarInfoView;

public interface CarInfoService {
	
	void saveCarInfo(CarInfoDto dto);
	
	ECPage<CarInfoView> queryCarInfoPages(Integer disabled,Integer customerId, String carNo,String carFrameNo,String carEngineNo,String carRegistNo
			,String startDate,String endDate);
	
	ECPage<CarInfoView> queryCarInfoNobindPages(String customerName,String carNo,String startTime,String endTime);
	
	ECPage<CarInfoView> queryCarInfoNobindPages(Integer interfaceId,Integer id,Integer pid,String customerName, String carNo, String startTime,String endTime);
	
	CarInfoView findByCarId(Integer carid);
}
