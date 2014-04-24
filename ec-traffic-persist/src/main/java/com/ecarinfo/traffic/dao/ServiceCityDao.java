package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.ServiceCity;

public interface ServiceCityDao extends ECDao<ServiceCity> {

	long findCityInfoCountByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findCityInfoByCriteria(Criteria whereBy);
}
