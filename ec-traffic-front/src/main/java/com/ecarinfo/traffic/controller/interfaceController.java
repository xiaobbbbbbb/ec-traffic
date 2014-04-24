package com.ecarinfo.traffic.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

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
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.InterfaceInfoDao;
import com.ecarinfo.traffic.dto.InterfaceInfoDto;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.ProvinceInfo;
import com.ecarinfo.traffic.po.ServiceCity;
import com.ecarinfo.traffic.po.ServiceCityInterface;
import com.ecarinfo.traffic.rm.InterfaceInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityInterfaceRM;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.service.InterfaceInfoService;

@Controller
@RequestMapping("/interface")
public class interfaceController extends BaseController {

	@Resource
	InterfaceInfoDao interfaceInfoDao;

	@Resource
	InterfaceInfoService interfaceInfoService;

	private static final Logger logger = Logger
			.getLogger(interfaceController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {

			ECPage<InterfaceInfoDto> ECPage =
			this.interfaceInfoService.listInterfaces();
			model.addAttribute("ECPage", ECPage);
			return "traffic/interface/list";
		} catch (Exception e) {
			logger.error("客户信息列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<ServiceCity> listCities = this.genericService.findList(
				ServiceCity.class, new Criteria());
		model.addAttribute("listCities", listCities);
		return "traffic/interface/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加接口", code = "接口管理")
	public Json add(@RequestBody InterfaceInfoDto dto) {
		Json json = new Json();
		try {

			interfaceInfoService.saveInterfaceInfo(dto);
			// this.genericService.save(dto);
			json.setMsg("接口添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("接口添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		List<ServiceCity> listCities = this.genericService.findList(
				ServiceCity.class, new Criteria());
		model.addAttribute("listCities", listCities);
		InterfaceInfoDto dto = this.interfaceInfoService.findById(id);
		if(dto.getCityPname()!=null){
			dto.setCityName(dto.getCityPname());
		}
		model.addAttribute("dto", dto);
		return "traffic/interface/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody InterfaceInfoDto dto) {
		Json json = new Json();
		try {
			this.genericService
					.updateWithCriteria(
							InterfaceInfo.class,
							new Criteria()
									.update(InterfaceInfoRM.name, dto.getName())
									.update(InterfaceInfoRM.isSpider,
											dto.getIsSpider())
									.update(InterfaceInfoRM.isValid,
											dto.getIsValid())
									.update(InterfaceInfoRM.maxNum,
											dto.getMaxNum())
									.update(InterfaceInfoRM.desction,
											dto.getDesction())
									.update(InterfaceInfoRM.utime,
											DateUtils.currentDateStr())
									.eq(InterfaceInfoRM.pk, dto.getId()));
			this.genericService.updateWithCriteria(ServiceCityInterface.class, new Criteria()
									.update(ServiceCityInterfaceRM.cityId, dto.getCityId())
									.update(ServiceCityInterfaceRM.cityPid, dto.getCityPid())
									.eq(ServiceCityInterfaceRM.interfaceId, dto.getId()));

			json.setMsg("修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("修改失败!");
			logger.error("修改失败!", e);
		}
		return json;
	}

	// 删除(改为无效)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	@Action(description = "删除接口", code = "接口管理")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.updateWithCriteria(
						InterfaceInfo.class,
						new Criteria().update(InterfaceInfoRM.isValid, 0).in(
								InterfaceInfoRM.pk, ids));
				json.setMsg("删除成功!");
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
	public Json check( String name) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			
			count = this.genericService.count(InterfaceInfo.class,
					new Criteria().eq(InterfaceInfoRM.name, name));
			if (count > 0) {
				json.setMsg("接口名称已存在");// 车牌存在
				json.setSuccess(false);
				return json;
			}
		} catch (Exception e) {
			logger.error("检查是否存在失败!", e);
			json.setMsg("验证出错");// 车牌存在
			json.setSuccess(false);
			return json;
		}
		json.setSuccess(true);
		return json;
	}
	
}
