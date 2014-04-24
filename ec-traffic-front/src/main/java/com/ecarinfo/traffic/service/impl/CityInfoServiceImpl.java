package com.ecarinfo.traffic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.ServiceCityDao;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.service.CityInfoService;
import com.ecarinfo.traffic.view.ServiceCityView;

@Service("cityInfoService")
public class CityInfoServiceImpl implements CityInfoService {

	@Resource
	private GenericDao genericDao;

	@Resource
	private ServiceCityDao serviceCityDao;

	
	@Override
	public ECPage<ServiceCityView> queryCityInfoPages(Integer disabled) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		long counts = serviceCityDao.findCityInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"sc." + ServiceCityRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = serviceCityDao
				.findCityInfoByCriteria(whereBy);
		List<ServiceCityView> list = RowMapperUtils
				.map2List(map, ServiceCityView.class);

		ECPage<ServiceCityView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public void saveCarInfo(CarInfoDto dto) {
		// TODO Auto-generated method stub
		
	}

}
