package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.TrafficItems;

public interface TrafficItemsDao extends ECDao<TrafficItems> {
	
	long findTrafficItemsCountByCustomer(Criteria whereBy);
	
	long findTrafficItemsCountByCar(Criteria whereBy);
	
	List<Map<String, Object>> findTrafficItemsByCustomer(Criteria whereBy);
	
	List<Map<String, Object>> findTrafficItemsByCar(Criteria whereBy);
}
