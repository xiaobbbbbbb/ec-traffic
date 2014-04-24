package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.TaskInfo;

public interface TaskInfoDao extends ECDao<TaskInfo> {
	
	List<Map<String, Object>> findInterfaceInfoByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findInterfaceCarsByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findInterCarInfoByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findTaskCarsByCriteria(Criteria whereBy);
	
	long findCarInfoCountByCriteria(Criteria whereBy);

}
