package com.ecarinfo.traffic.service;

import org.apache.ibatis.session.SqlSessionFactory;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.view.CarInfoView;

public interface TaskInfoService {
	
	void saveTaskInfo(TaskInfo dto);
	
	ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo);
	
	ECPage<CarInfoView> queryCarInfoNobindPages(String customerName,String carNo,String startTime,String endTime);
	
	CarInfoView findByCarId(Integer carid);
	
	void task(TaskService service,SqlSessionFactory sqlSessionFactory);
}
