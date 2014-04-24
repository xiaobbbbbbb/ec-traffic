package com.ecarinfo.traffic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.traffic.dao.CustomerInfoDao;
import com.ecarinfo.traffic.dto.CustomerInfoDto;
import com.ecarinfo.traffic.dto.InterfaceInfoDto;
import com.ecarinfo.traffic.dto.TreeDto;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.InterfaceInfoRM;
import com.ecarinfo.traffic.service.CustomerInfoService;
import com.ecarinfo.traffic.view.CarInfoView;
@Service("customerInfoService")
public class CustomerInfoServiceImpl implements CustomerInfoService {
	@Resource
	CustomerInfoDao customerInfoDao;

	@Override
	public List<TreeDto> findTree(Criteria whereby) {
		try{
			
			List<Map<String, Object>> map = customerInfoDao
				.findTree(whereby);
			List<TreeDto> list = RowMapperUtils
				.map2List(map, TreeDto.class);
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}
	
	@Override
	public CustomerInfoDto findById(Integer id) {
		// TODO Auto-generated method stub
		Criteria whereBy = new Criteria();
		if (id != null) {
			whereBy.eq("ci." + CustomerInfoRM.id, id);
		}
		List<Map<String, Object>> map = customerInfoDao
				.findById(whereBy);
		List<CustomerInfoDto> list = RowMapperUtils
				.map2List(map, CustomerInfoDto.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else {
			return new CustomerInfoDto();
		}
	}

}
