package com.ecarinfo.traffic.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.HttpClientUtils;
import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.CustomerCarinfoDao;
import com.ecarinfo.traffic.dao.InterfaceCarinfoDao;
import com.ecarinfo.traffic.dto.OrgCarinfoDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.CustomerCarinfo;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.po.TrafficItems;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.service.CarInfoService;
import com.ecarinfo.traffic.view.HainanTrafficInfo;
import com.ecarinfo.traffic.view.UploadView;

@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

	@Resource
	CarInfoDao carInfoDao;

	@Resource
	CarInfoService carInfoService;

	@Resource
	SqlSessionFactory sqlSessionFactory;

	@Resource
	GenericDao genericDao;

	private static final Logger logger = Logger.getLogger(ApiController.class);

	// 上传保存/删除车辆
	@RequestMapping(value = "/batchUploadCarinfos")
	@ResponseBody
	public Json batchUploadCarinfos(OrgCarinfoDto dto) {
		Json json = new Json();
		logger.info("------------------get in batchUploadCarinfos!!!!----------------");
		// 根据机构编码判断机构是否存在
		CustomerInfo cinfo = this.genericService.findOne(CustomerInfo.class,
				new Criteria().eq(CustomerInfoRM.code, dto.getOrgCode()));
		if (StringUtils.isEmpty(dto.getAction())) {
			logger.error("action为空");
			json.setMsg("action为空");
			json.setSuccess(false);
			return json;
		}
		// 校验参数
		if (!checkParam(dto, json, cinfo)) {
			return json;
		}
		List<UploadView> list = dto.getCarinfolist();
		
		Set<UploadView> set = new HashSet<UploadView>();// 解析数据过滤一下重复的
		for (UploadView view : list) {
			set.add(view);
		}
		// 添加操作
		if (dto.getAction().equals("add")) {
			
			// validateCarinfo(set);
			// 插入车辆信息
			saveWithBatch(validateCarinfo(set));
			// 插入对应关系
			Set<CustomerCarinfo> cs = validateCustomerCarinfo(set,
					cinfo.getId());
			saveCusCarWithBatch(cs);
			json.setMsg("添加成功！");
			return json;
		}
		// 删除
		if(dto.getAction().equals("del")){
			this.genericService.updateWithCriteria(
					CustomerCarinfo.class,
					new Criteria()
							.update(CustomerCarinfoRM.utime,
									DateUtils.currentDateStr())
							.update(CustomerCarinfoRM.isDisable, 0)
							.in(CustomerCarinfoRM.carId, findCarIds(set,cinfo.getId())));
			json.setMsg("删除成功!");
		}
		return json;
	}

	
	//单个查询 
//	@RequestMapping(value = "/getTrafficItemsBycar")
//	@ResponseBody
//	public Json getTrafficItemsBycar(
//			String orgCode,String carNo,String carEngineNo,String carRegistNo,String carFrameNo) {
//		Json json = new Json();
//		//机构编码
//		if(StringUtils.isEmpty(orgCode)){
//			json.setMsg("orgCode is null ！");
//			json.setSuccess(false);
//			return json;
//		}
//		//车牌号
//		if(StringUtils.isEmpty(carNo)){
//			json.setMsg("carNo is null ！");
//			json.setSuccess(false);
//			return json;
//		}
//		//车架号
//		if(StringUtils.isEmpty(carFrameNo)){
//			json.setMsg("carFrameNo is null！");
//			json.setSuccess(false);
//			return json;
//		}
//		// 根据机构编码判断机构是否存在
//		CustomerInfo cinfo = this.genericService.findOne(CustomerInfo.class,
//						new Criteria().eq(CustomerInfoRM.code, orgCode));
//		if(cinfo==null){
//			json.setMsg("没有此机构信息！");
//			json.setSuccess(false);
//			return json;
//		}
//		
//		//判断是否存在 不存在保存进去
//		CarInfo carInfo = this.genericService.findOne(
//				CarInfo.class,
//				new Criteria().eq(CarInfoRM.carNo, carNo).eq(
//						CarInfoRM.carFrameNo,carFrameNo,
//						CondtionSeparator.AND));
//		
//		if (carInfo == null) {
//			CarInfo car = new CarInfo();
//			car.setCarNo(carNo);
////			car.setUserName(view.getUserName());
//			car.setCarEngineNo(carEngineNo);
//			car.setCarFrameNo( carFrameNo);
//			car.setCarRegistNo(carRegistNo);
//			car.setCtime(new Date());
//			this.genericDao.insert(carInfo);
//		}
//		//在查一次
//		carInfo = this.genericService.findOne(
//				CarInfo.class,
//				new Criteria().eq(CarInfoRM.carNo, carNo).eq(
//						CarInfoRM.carFrameNo,carFrameNo,
//						CondtionSeparator.AND));
//		CustomerCarinfo ccinfos = this.genericService.findOne(
//				CustomerCarinfo.class,
//				new Criteria().eq(CustomerCarinfoRM.carId, carInfo.getId())
//						.eq(CustomerCarinfoRM.customerId, cinfo.getId(),
//								CondtionSeparator.AND));
//
//		if (ccinfos == null) {// 如果表中没有的添加
//			CustomerCarinfo ccinfo = new CustomerCarinfo();
//			ccinfo.setCarId(carInfo.getId());
//			ccinfo.setCustomerId( cinfo.getId());
////			ccinfo.setIsAllowchange(view.getIsAllowchange());
//			ccinfo.setCtime(new Date());
//			this.genericDao.insert(ccinfo);
//		}
//		singleQuery(cinfo,carInfo,json);
//		return json;
//	}
	
	
	// 批量查询
	@RequestMapping(value = "/getTrafficItemsBycars")
	@ResponseBody
	public Json getTrafficItemsBycars(OrgCarinfoDto dto) {
		Json json = new Json();
		// 根据机构编码判断机构是否存在
		CustomerInfo cinfo = this.genericService.findOne(CustomerInfo.class,
				new Criteria().eq(CustomerInfoRM.code, dto.getOrgCode()));

		// 校验参数
		if (!checkParam(dto, json, cinfo)) {
			return json;
		}
		List<UploadView> list = dto.getCarinfolist();
		Set<UploadView> viewset = new HashSet<UploadView>();// 解析数据封装
		// 去重复先
		for (UploadView view : list) {
			viewset.add(view);
		}

		// 先保存
		// validateCarinfo(set);
		// 插入车辆信息
		saveWithBatch(validateCarinfo(viewset));
		// 插入对应关系
		Set<CustomerCarinfo> cs = validateCustomerCarinfo(viewset,
				cinfo.getId());
		saveCusCarWithBatch(cs);

		// 查询工作
		queryTrafficItems(viewset, cinfo, json);

		json.setMsg("查询完毕！");
		return json;
	}

	/*
	 * @Description 过滤carinfo库中已存在的车辆
	 * 
	 * @Param set
	 * 
	 * @Return set
	 */
	public Set<CarInfo> validateCarinfo(Set<UploadView> set) {
		Iterator<UploadView> it = set.iterator();
		Set<CarInfo> carSet = new HashSet();
		while (it.hasNext()) {
			UploadView view = it.next();
			CarInfo carInfo = this.genericService.findOne(
					CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(
							CarInfoRM.carFrameNo, view.getCarFrameNo(),
							CondtionSeparator.AND));
			if (carInfo == null) {
				CarInfo car = new CarInfo();
				car.setCarNo(view.getCarNo());
				car.setUserName(view.getUserName());
				car.setCarEngineNo(view.getCarEngineNo());
				car.setCarFrameNo(view.getCarFrameNo());
				car.setCarRegistNo(view.getCarRegistNo());
				car.setCtime(new Date());
				carSet.add(car);
			}
		}
		logger.info("carlistsize==" + carSet.size());
		return carSet;
	}

	/*
	 * 筛选出customer-carinfo需要删除的车辆IDS
	 */
	
	private String[] findCarIds(Set<UploadView> set,Integer customerId){
		Iterator<UploadView> it = set.iterator();
		String [] ids=null;
		List<CustomerCarinfo> carSet = new ArrayList();
		while (it.hasNext()) {
			UploadView view = it.next();

			// 找出carid
			CarInfo carinfo = this.genericService.findOne(
					CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(
							CarInfoRM.carFrameNo, view.getCarFrameNo(),
							CondtionSeparator.AND));

			CustomerCarinfo ccinfos = this.genericService.findOne(
					CustomerCarinfo.class,
					new Criteria().eq(CustomerCarinfoRM.carId, carinfo.getId())
							.eq(CustomerCarinfoRM.customerId, customerId,
									CondtionSeparator.AND));

			if (ccinfos!= null&&ccinfos.getCarId()!=null) {// 如果表中没有的添加
				
				carSet.add(ccinfos);
			}
		}
		if(carSet.size()>0){
			ids =new String[carSet.size()];
			for(int i=0;i<carSet.size();i++){
				ids[i]=carSet.get(i).getCarId().toString();
			}
		}
		
		return ids;
		
	}
	
	/*
	 * 筛选出customer-carinfo需添加的车辆 暂时根据需求全部添加进去（无重复对应关系）
	 */
	public Set<CustomerCarinfo> validateCustomerCarinfo(Set<UploadView> set,
			Integer customerId) {
		Iterator<UploadView> it = set.iterator();
		Set<CustomerCarinfo> carSet = new HashSet();
		while (it.hasNext()) {
			UploadView view = it.next();

			// 找出carid
			CarInfo carinfo = this.genericService.findOne(
					CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(
							CarInfoRM.carFrameNo, view.getCarFrameNo(),
							CondtionSeparator.AND));

			CustomerCarinfo ccinfos = this.genericService.findOne(
					CustomerCarinfo.class,
					new Criteria().eq(CustomerCarinfoRM.carId, carinfo.getId())
							.eq(CustomerCarinfoRM.customerId, customerId,
									CondtionSeparator.AND));

			if (ccinfos == null) {// 如果表中没有的添加
				CustomerCarinfo ccinfo = new CustomerCarinfo();
				ccinfo.setCarId(carinfo.getId());
				ccinfo.setCustomerId(customerId);
				ccinfo.setIsAllowchange(view.getIsAllowchange());
				ccinfo.setCtime(new Date());
				carSet.add(ccinfo);
			}
		}
		return carSet;
	}

	// 批量保存
	private void saveWithBatch(Set<CarInfo> set) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,
				false);
		int idx = 1;
		Iterator<CarInfo> it = set.iterator();
		try {
			while (it.hasNext()) {
				CarInfo car = it.next();
				session.insert(CarInfoDao.class.getName() + ".insert", car);
				if (idx % 100 == 0) {// 100条提交事务
					session.commit();
				}
				idx++;
			}
			session.commit();
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}

			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	// 校验数据
	private boolean checkParam(OrgCarinfoDto dto, Json json, CustomerInfo cinfo) {
		if (dto == null) {
			json.setMsg("参数为空");
			json.setSuccess(false);
			logger.error("未传参");
			return false;
		}
		if (StringUtils.isEmpty(dto.getOrgCode())) {
			json.setMsg("机构代码为空。");
			json.setSuccess(false);
			logger.error("batchUploadCarinfos---------" + "机构代码为空。");
			return false;
		}
		// 根据机构编码判断机构是否存在
		if (cinfo == null) {
			logger.error("未根据模板查询到公司信息");
			json.setMsg("根据机构代码未找到机构信息！");
			return false;
		}
		if (dto.getCarinfolist() == null && dto.getCarinfolist().size() == 0) {
			logger.error("没有车辆");
			json.setMsg("车辆为空！");
			return false;
		}

		return true;
	}

	// 批量保存
	private void saveCusCarWithBatch(Set<CustomerCarinfo> set) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,
				false);
		int idx = 1;
		Iterator<CustomerCarinfo> it = set.iterator();
		try {
			while (it.hasNext()) {
				CustomerCarinfo car = it.next();
				session.insert(CustomerCarinfoDao.class.getName() + ".insert",
						car);
				if (idx % 100 == 0) {// 100条提交事务
					session.commit();
				}
				idx++;
			}
			session.commit();
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}

			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	//单个查违章
	private void singleQuery(
			CustomerInfo cusinfo,CarInfo carinfo,Json json){
		/*
		 * 查询一次就会生成一个任务 1.绑定接口 2.生成任务 3.执行查询 跳过1,2 直接查询 需要对应接口
		 */
		// 根据机构查询相应的接口 取一个自动绑定

		ServiceCityInterface interfaceinfo = this.genericDao.findOne(
				ServiceCityInterface.class,
				new Criteria().eq(ServiceCityInterfaceRM.cityId,
						cusinfo.getCityId()).eq(ServiceCityInterfaceRM.cityPid,
								cusinfo.getCityPid(), CondtionSeparator.AND));
		InterfaceCarinfo info = new InterfaceCarinfo();
		info.setCarId(carinfo.getId());
		info.setCtime(new Date());
		info.setCustomerId(cusinfo.getId().toString());
		info.setInterfaceId(interfaceinfo.getId());
		info.setLastRequestDate(new Date());
		this.genericDao.insert(info);
		
		// 此方法现在是固定的 要根据接口配置来动态获取
		List<HainanTrafficInfo> lists = gripCarTraffics(
				carinfo.getCarNo(), carinfo.getCarFrameNo());

		Map<Long, List<HainanTrafficInfo>> map = new HashMap();
		// 保存违章信息
		if (lists != null && lists.size() > 0) {
			for (HainanTrafficInfo trainfo : lists) {
				TrafficItems item = new TrafficItems();
				item.setAddress(trainfo.getAddress());
				item.setCarId(carinfo.getId());
				item.setTrafficTime(trainfo.getTime());
				item.setItem(trainfo.getTrafficType());
				item.setCtime(new Date());
				item.setInterfaceId(interfaceinfo.getCityId());
				genericDao.insert(item);
			}
		}
		map.put(carinfo.getId(), lists);
		json.setObj(map);
		json.setMsg("成功");
	}

	// 查违章
	private void queryTrafficItems(Set<UploadView> set, CustomerInfo cus,
			Json json) {
		/*
		 * 查询一次就会生成一个任务 1.绑定接口 2.生成任务 3.执行查询 跳过1,2 直接查询 需要对应接口
		 */
		// 根据机构查询相应的接口 取一个自动绑定

		ServiceCityInterface interfaceinfo = this.genericDao.findOne(
				ServiceCityInterface.class,
				new Criteria().eq(ServiceCityInterfaceRM.cityId,
						cus.getCityId()).eq(ServiceCityInterfaceRM.cityPid,
						cus.getCityPid(), CondtionSeparator.AND));
		Iterator<UploadView> it = set.iterator();
		List<InterfaceCarinfo> list = new ArrayList();
		Map<Long, List<HainanTrafficInfo>> map = new HashMap();
		while (it.hasNext()) {
			UploadView view = it.next();

			InterfaceCarinfo info = new InterfaceCarinfo();
			// 找出carid
			CarInfo carinfo = this.genericService.findOne(
					CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(
							CarInfoRM.carFrameNo, view.getCarFrameNo(),
							CondtionSeparator.AND));

			if (carinfo != null) {
				info.setCarId(carinfo.getId());
				info.setCtime(new Date());
				info.setCustomerId(cus.getId().toString());
				info.setInterfaceId(interfaceinfo.getId());
				info.setLastRequestDate(new Date());
				list.add(info);
			}

		}
		// 批量保存
		saveWithBatch(list);

		Iterator<UploadView> its = set.iterator();
		while (its.hasNext()) {
			try {
				UploadView view = its.next();
				// 找出carid
				CarInfo carinfo = this.genericService.findOne(
						CarInfo.class,
						new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(
								CarInfoRM.carFrameNo, view.getCarFrameNo(),
								CondtionSeparator.AND));
				// 此方法现在是固定的 要根据接口配置来动态获取
				List<HainanTrafficInfo> lists = gripCarTraffics(
						view.getCarNo(), view.getCarFrameNo());

				// 保存违章信息
				if (lists != null && lists.size() > 0) {
					for (HainanTrafficInfo trainfo : lists) {
						TrafficItems item = new TrafficItems();
						item.setAddress(trainfo.getAddress());
						item.setCarId(carinfo.getId());
						item.setTrafficTime(trainfo.getTime());
						item.setItem(trainfo.getTrafficType());
						item.setCtime(new Date());
						item.setInterfaceId(interfaceinfo.getCityId());
						genericDao.insert(item);
					}
				}
				map.put(carinfo.getId(), lists);
				json.setObj(map);
				json.setMsg("成功");
			} catch (Exception e) {
				json.setMsg("程序出现异常！");
				json.setSuccess(false);
				logger.error(String
						.format("-------------------error for gripCarTraffics(%s,%s, %s)"));
			}
		}
		// 查询完了之后的回调
	}

	// 模拟接口
	List<HainanTrafficInfo> gripCarTraffics(String carNo, String carFrameNo) {
		// 抓取违章信息
		List<HainanTrafficInfo> traffics = new ArrayList<HainanTrafficInfo>();

		HainanTrafficInfo info = new HainanTrafficInfo();
		info.setCarNo(carNo);
		info.setTrafficType("违章停车");
		info.setTime(new Date());
		info.setAddress("软件园二期");
		info.setExecutedOrg("");
		traffics.add(info);
		return traffics;

	}

	// 批量保存
	private void saveWithBatch(List<InterfaceCarinfo> list) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,
				false);
		int idx = 1;
		try {
			for (InterfaceCarinfo g : list) {
				System.err.println(g.getCarId());
				session.insert(InterfaceCarinfoDao.class.getName() + ".insert",
						g);
				if (idx % 30 == 0) {
					session.commit();
				}
				idx++;
			}
			session.commit();
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}

			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
