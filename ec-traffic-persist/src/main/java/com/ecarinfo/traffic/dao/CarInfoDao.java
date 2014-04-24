package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.CarInfo;

public interface CarInfoDao extends ECDao<CarInfo> {
	
	long findCarInfoCountByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findCarInfoByCriteria(Criteria whereBy);
	
}
