package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.InterfaceInfo;

public interface InterfaceInfoDao extends ECDao<InterfaceInfo> {
	
	List<Map<String, Object>> findInterfaceInfoByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findCarNobindByCriteria(Criteria whereBy);
	
	long findCarInfoCountByCriteria(Criteria whereBy);
	
	long findInterfaceInfoCountByCriteria(Criteria whereBy);

}
