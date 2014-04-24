package com.ecarinfo.traffic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.InterfaceCarinfoDao;
import com.ecarinfo.traffic.dao.InterfaceInfoDao;
import com.ecarinfo.traffic.dto.InterfaceCarDto;
import com.ecarinfo.traffic.dto.ResultDto;
import com.ecarinfo.traffic.po.InterfaceCarinfo;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.service.CarInfoService;
import com.ecarinfo.traffic.view.CarInfoView;

@Controller
@RequestMapping("/interface")
public class interfaceCarController extends BaseController {

	@Resource
	InterfaceInfoDao interfaceInfoDao;

	@Resource
	CarInfoService carInfoService;
	
	@Resource
	CarInfoDao carInfoDao;
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	private static final Logger logger = Logger
			.getLogger(interfaceCarController.class);

	// 列表
	@RequestMapping(value = "/carlist", method = { RequestMethod.GET,
			RequestMethod.POST })
		public String list(
				Integer interfaceId,String customerName, String carNo, String startTime,String endTime,Model model) {
			try {
				
				if(null!=carNo){
					carNo = DtoUtil.incode(carNo);
				}
				if(customerName!=carNo){
					customerName = DtoUtil.incode(customerName);
				}
				ServiceCityInterface serviceCity = this.genericService.findOne(ServiceCityInterface.class, new Criteria().eq(ServiceCityInterfaceRM.interfaceId, interfaceId));
				InterfaceCarDto dto = new InterfaceCarDto(interfaceId,carNo,customerName,startTime,endTime);
				ECPage<CarInfoView> ECPage=carInfoService.queryCarInfoNobindPages(interfaceId,serviceCity.getCityId(),serviceCity.getCityPid(),customerName,carNo,startTime,endTime);
//				ECPage<CarInfoView> ECPage = carInfoService.queryCarInfoNobindPages(customerName,carNo,startTime,endTime);
				model.addAttribute("ECPage", ECPage);
				model.addAttribute("dto", dto);
				return "traffic/interface/car_list";
			} catch (Exception e) {
				logger.error("车辆信息列表加载失败", e);
			}
			return null;
		}

	// 绑定车辆
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto bindCars(String[] ids,Integer interfaceId) {
		try {
			if(interfaceId==null){
				return new ResultDto(501,"绑定失败，没有绑定接口");
			}
			List<InterfaceCarinfo> list= new ArrayList();
			Integer carId;
			String customerId;
			//封装对象
			for(String id:ids){
				
				InterfaceCarinfo info = new InterfaceCarinfo();
				carId = new Integer(id.split("_")[0]);
				customerId = id.split("_")[1];
				info.setCarId((long)carId);
				info.setInterfaceId(interfaceId);
				info.setCtime(new Date());
				info.setCustomerId(customerId);
				
				list.add(info);
				//carInfoDao.updateWithCriteria(new Criteria().update(CarInfoRM.interfaceId, interfaceId).eq(CarInfoRM.pk,carId ));//更新车辆表
			}
			//批量保存
			saveWithBatch(list);
			
			ResultDto dto=new ResultDto(200,"绑定成功");
			return dto;
		} catch (Exception e) {
			logger.error("保存失败!", e);
			return new ResultDto(500,"绑定失败");
		}
	}

	// 批量保存
	private void saveWithBatch(List<InterfaceCarinfo> list) {
			SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
			int idx = 1;
			try {
				for (InterfaceCarinfo g : list) {
					System.err.println(g.getCarId());
					session.insert(InterfaceCarinfoDao.class.getName() + ".insert", g);
					if (idx % 30== 0) {
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
