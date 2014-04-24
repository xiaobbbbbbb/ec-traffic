package com.ecarinfo.traffic.service;

import java.util.List;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.traffic.dto.CustomerInfoDto;
import com.ecarinfo.traffic.dto.TreeDto;

public interface CustomerInfoService {
	
	public List<TreeDto> findTree(Criteria whereby);
	
	public CustomerInfoDto findById(Integer id);
}
