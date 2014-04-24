package com.ecarinfo.traffic.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.traffic.po.CustomerInfo;

public interface CustomerInfoDao extends ECDao<CustomerInfo> {
	
	List<Map<String, Object>> findTree(Criteria whereBy);
	
	List<Map<String, Object>> findById(Criteria whereBy);

}
