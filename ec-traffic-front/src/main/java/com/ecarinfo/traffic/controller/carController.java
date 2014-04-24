package com.ecarinfo.traffic.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.CustomerInfoDao;
import com.ecarinfo.traffic.dto.CarInfoDto;
import com.ecarinfo.traffic.dto.TrafficItemsDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.CustomerCarinfo;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.service.CarInfoService;
import com.ecarinfo.traffic.view.CarInfoView;

@Controller
@RequestMapping("/car")
public class carController extends BaseController {
	
	@Resource
	CarInfoDao carInfoDao;

	@Resource
	CarInfoService carInfoService;

	private static final Logger logger = Logger.getLogger(carController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer disabled,Integer customerId, String carNo,String carFrameNo,String carEngineNo,String carRegistNo
			,String startDate,String endDate,Model model) {
		try {
			carNo = DtoUtil.incode(carNo);
			List<CustomerInfo> listCustomer = this.genericService.findList(
					CustomerInfo.class, new Criteria());
			model.addAttribute("listCustomer", listCustomer);
			ECPage<CarInfoView> ECPage =carInfoService.queryCarInfoPages(disabled,customerId, carNo,carFrameNo,carEngineNo,carRegistNo, startDate, endDate);
			model.addAttribute("ECPage", ECPage);
			return "traffic/car/list";
		} catch (Exception e) {
			logger.error("车辆信息列表加载失败", e);
		}
		return null;
	}
	// 列表
		@RequestMapping(value = "/queryList", method = { RequestMethod.GET,
				RequestMethod.POST })
		public String queryList(Integer disabled,Integer customerId, String carNo,String carFrameNo,String carEngineNo,String carRegistNo
				,String startDate,String endDate,Model model) {
			try {
				carNo = DtoUtil.incode(carNo);
				TrafficItemsDto dto=new TrafficItemsDto(customerId,carNo,carFrameNo,carEngineNo,carRegistNo,startDate,endDate);
				model.addAttribute("dto", dto);
				List<CustomerInfo> listCustomer = this.genericService.findList(
						CustomerInfo.class, new Criteria());
				model.addAttribute("listCustomer", listCustomer);
				ECPage<CarInfoView> ECPage =carInfoService.queryCarInfoPages(disabled,customerId, carNo,carFrameNo,carEngineNo,carRegistNo, startDate, endDate);
				model.addAttribute("ECPage", ECPage);
				return "traffic/car/list";
			} catch (Exception e) {
				logger.error("车辆信息列表加载失败", e);
			}
			return null;
		}
	
	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<CustomerInfo> listCustomer = this.genericService.findList(
				CustomerInfo.class, new Criteria());
		model.addAttribute("listCustomer", listCustomer);
		return "traffic/car/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加车辆", code = "车辆信息")
	public Json add(@RequestBody CarInfoDto dto) {
		Json json = new Json();
		try {
			carInfoService.saveCarInfo(dto);
//			CustomerCarinfo in=dto.getCustomerCarinfo();
			System.out.println(dto.getCustomerId());
			// this.ralGroupService.save(dto);
			json.setMsg("车辆添加成功!");
			json.setObj(dto.getCarNo());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("车辆添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		CarInfoView dto = this.carInfoService.findByCarId(id);
		model.addAttribute("dto", dto);
		List<CustomerInfo> listCustomer = this.genericService.findList(
				CustomerInfo.class, new Criteria());
		model.addAttribute("listCustomer", listCustomer);
		return "traffic/car/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody CarInfoView dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					CarInfo.class,
					new Criteria()
							.update(CarInfoRM.carNo, dto.getCarNo())
							.update(CarInfoRM.carEngineNo, dto.getCarEngineNo())
							.update(CarInfoRM.carFrameNo,
									dto.getCarFrameNo())
							.update(CarInfoRM.carRegistNo, dto.getCarRegistNo())
							.update(CarInfoRM.userName, dto.getUserName())
							.update(CarInfoRM.utime,
									DateUtils.currentDateStr())
//							.update(CarInfoRM.endDate,
//									DateUtils.dateToString(dto.getStartDate(),
//											TimeFormatter.YYYY_MM_DD))
							.eq(CarInfoRM.pk, dto.getId()));
			this.genericService.updateWithCriteria(CustomerCarinfo.class,
					new Criteria().update(CustomerCarinfoRM.isAllowchange, dto.getIsAllowchange())
					.update(CustomerCarinfoRM.isExpired, dto.getIsExpired())
					.eq(CustomerCarinfoRM.carId, dto.getId())
					.eq(CustomerCarinfoRM.customerId, dto.getCustomerId(), CondtionSeparator.AND)
					);
			json.setMsg("修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(隐藏不可见)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除公司", code = "公司信息")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(
						CustomerCarinfo.class,
						new Criteria()
								.update(CustomerCarinfoRM.utime,
										DateUtils.currentDateStr())
								.update(CustomerCarinfoRM.isDisable, 0)
								.in(CustomerCarinfoRM.carId, ids));
				json.setMsg("删除成功!");

//				List<CustomerInfo> dtos = this.genericService.findList(
//						CustomerInfo.class,
//						new Criteria().in(CustomerInfoRM.pk, ids));
//				json.setObj(dtos.get(0).getName());

			} else {
				json.setSuccess(false);
				json.setMsg("删除失败!");
			}
		} catch (Exception e) {
			logger.error("删除失败!", e);
		}
		return json;
	}
	
	// 表单验证
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String carNo) {
		Json json = new Json();
		long count = 0;
		try {
			carNo = DtoUtil.incode(carNo);
			count = this.genericService.count(CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, carNo));
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
		}
		if(count>0){
			json.setMsg("100");//车牌存在
		}
		json.setSuccess(true);
		return json;
	}
}
