package com.ecarinfo.traffic.service;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.po.TrafficItems;
import com.ecarinfo.traffic.view.CarInfoView;
import com.ecarinfo.traffic.view.ServiceCityView;
import com.ecarinfo.traffic.view.TrafficItemsView;
import com.ecarinfo.utils.HainanTrafficUtils.HainanTrafficInfo;

public interface TrafficService {
	
	ECPage<TrafficItemsView> queryTrafficPages(Integer customerName,String carNo,String carFrameNo,String carEngineNo,String carRegistNo
			,String startDate,String endDate);

	List<TrafficItemsView> findTrafficItemsByCustomerId(Integer customerId,String startDate,String endDate);
	
	public List<HainanTrafficInfo> gripCarTraffics(Long carId, String car_no, String car_frame_no);
}
