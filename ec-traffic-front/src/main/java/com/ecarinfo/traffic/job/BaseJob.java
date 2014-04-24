package com.ecarinfo.traffic.job;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.traffic.service.TaskService;

public class BaseJob {
	protected static Logger logger = Logger.getLogger(BaseJob.class);
	@Resource
	GenericDao genericDao;
	
	@Resource
	TaskService taskService;
	
	@Resource
	SqlSessionFactory sqlSessionFactory;
	
	public void run(){
		
	}
}
