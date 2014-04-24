package com.ecarinfo.traffic.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.InterfaceInfoDto;
import com.ecarinfo.traffic.view.CarInfoView;

public interface InterfaceInfoService {
	
	void saveInterfaceInfo(InterfaceInfoDto dto);
	
	ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo);
	
	ECPage<CarInfoView> queryCarInfoNobindPages(String customerName,String carNo,String startTime,String endTime);
	
	InterfaceInfoDto findById(Integer carid);
	
	ECPage<InterfaceInfoDto> listInterfaces();
	
}
