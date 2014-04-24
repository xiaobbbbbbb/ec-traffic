package com.ecarinfo.traffic.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.InterfaceInfoDao;
import com.ecarinfo.traffic.dto.InterfaceInfoDto;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.InterfaceInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.service.InterfaceInfoService;
import com.ecarinfo.traffic.view.CarInfoView;

@Service("interfaceInfoService")
public class InterfaceInfoServiceImpl implements InterfaceInfoService {

	@Resource
	private GenericDao genericDao;

	@Resource
	private CarInfoDao carInfoDao;
	
	@Resource
	private InterfaceInfoDao interfaceInfoDao;

	@Override
	public void saveInterfaceInfo(InterfaceInfoDto dto) {
		try {
			InterfaceInfo interfaceInfo = new InterfaceInfo();
			Date date = new Date();
			interfaceInfo.setName(DtoUtil.decode(dto.getName()));
			interfaceInfo.setDesction(DtoUtil.decode(dto.getDesction()));
			interfaceInfo.setMaxNum(dto.getMaxNum());
			interfaceInfo.setIsSpider(dto.getIsSpider());
			interfaceInfo.setIsValid(dto.getIsValid());
			interfaceInfo.setCtime(date);
			interfaceInfo.setUtime(date);
			//interfaceInfo.setIsValid(dto.getIsValid());//是否审核
			
			genericDao.insert(interfaceInfo);
			InterfaceInfo info = genericDao.findOne(InterfaceInfo.class,
					new Criteria().eq(InterfaceInfoRM.name, dto.getName()));
			ServiceCityInterface scinfo = new ServiceCityInterface();
			scinfo.setCityId(dto.getCityId());
			scinfo.setCityPid(dto.getCityPid());
			scinfo.setInterfaceId(info.getId());
			scinfo.setCtime(date);
			genericDao.insert(scinfo);
			// TODO Auto-generated method stub
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public ECPage<CarInfoView> queryCarInfoPages(Integer disabled, String carNo) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1);
		long counts = carInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"car." + CarInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = carInfoDao
				.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public ECPage<CarInfoView> queryCarInfoNobindPages(String customerName,String carNo,String startTime,String endTime) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("cc." + CustomerCarinfoRM.isDisable, 1)
		.isNull("car."+CarInfoRM.interfaceId,CondtionSeparator.AND);
		
		if(StringUtils.isNotEmpty(customerName)){
			whereBy.like("cus."+CustomerInfoRM.name,"%"+ customerName+"%",CondtionSeparator.AND );
		}
		if(StringUtils.isNotEmpty(carNo)){
			whereBy.like("car."+CarInfoRM.carNo,"%"+ carNo+"%",CondtionSeparator.AND );
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals("car." + CarInfoRM.utime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals("car." + CarInfoRM.utime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		long counts = carInfoDao.findCarInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"car." + CarInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = carInfoDao
				.findCarInfoByCriteria(whereBy);
		List<CarInfoView> list = RowMapperUtils
				.map2List(map, CarInfoView.class);

		ECPage<CarInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	@Override
	public InterfaceInfoDto findById(Integer carid) {
		// TODO Auto-generated method stub
		Criteria whereBy = new Criteria();
		if (carid != null) {
			whereBy.eq("ii." + InterfaceInfoRM.id, carid);
		}
		List<Map<String, Object>> map = interfaceInfoDao
				.findInterfaceInfoByCriteria(whereBy);
		List<InterfaceInfoDto> list = RowMapperUtils
				.map2List(map, InterfaceInfoDto.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else {
			return new InterfaceInfoDto();
		}
	}

	@Override
	public ECPage<InterfaceInfoDto> listInterfaces() {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		long counts = interfaceInfoDao.findInterfaceInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(
				"sci." + ServiceCityInterfaceRM.pk, OrderType.DESC);
		List<Map<String, Object>> map = interfaceInfoDao
				.findInterfaceInfoByCriteria(whereBy);
		List<InterfaceInfoDto> list = RowMapperUtils
				.map2List(map, InterfaceInfoDto.class);
		
		ECPage<InterfaceInfoDto> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

}
