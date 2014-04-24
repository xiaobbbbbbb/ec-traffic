package com.ecarinfo.traffic.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.view.ServiceCityView;

public interface CityInfoService {
	
	void saveCarInfo(CarInfoDto dto);
	
	ECPage<ServiceCityView> queryCityInfoPages(Integer disabled);
	
}
