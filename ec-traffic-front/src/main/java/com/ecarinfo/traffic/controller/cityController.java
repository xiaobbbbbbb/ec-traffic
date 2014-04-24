package com.ecarinfo.traffic.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.po.ProvinceInfo;
import com.ecarinfo.traffic.po.ServiceCity;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.service.CityInfoService;
import com.ecarinfo.traffic.view.ServiceCityView;

@Controller
@RequestMapping("/city")
public class cityController extends BaseController {

	@Resource
	CityInfoService cityInfoService;
	
	private static final Logger logger = Logger.getLogger(cityController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {
			
			ECPage<ServiceCityView>  ECPage=cityInfoService.queryCityInfoPages(disabled);
			model.addAttribute("ECPage", ECPage);
			return "traffic/city/list";
		} catch (Exception e) {
			logger.error("客户信息列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		List<ProvinceInfo> listProvince = this.genericService.findList(
				ProvinceInfo.class, new Criteria());
		model.addAttribute("listProvince",listProvince);
		return "traffic/city/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加城市", code = "城市信息")
	public Json add(@RequestBody ServiceCity dto) {
		Json json = new Json();
		try {
			if (dto.getParentId() == null) {
				dto.setParentId(0);
			}
			Date date = new Date();
			dto.setCtime(date);
			dto.setUtime(date);
			genericService.save(dto);
			json.setMsg("城市添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("城市添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}
	
	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		ServiceCity dto = this.genericService.findByPK(ServiceCity.class, id);
		model.addAttribute("dto", dto);
		List<ProvinceInfo> listProvince = this.genericService.findList(
				ProvinceInfo.class, new Criteria());
		model.addAttribute("listProvince",listProvince);
		return "traffic/city/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody ServiceCity dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					ServiceCity.class,
					new Criteria()
							.update(ServiceCityRM.name, dto.getName())
							.update(ServiceCityRM.code, dto.getCode())
							.update(ServiceCityRM.isHot, dto.getIsHot())
							.update(ServiceCityRM.carFrameNo,
									dto.getCarFrameNo())
							.update(ServiceCityRM.carRegisterNo, dto.getCarRegisterNo())
							.update(ServiceCityRM.carEngineNo, dto.getCarEngineNo())
							.update(ServiceCityRM.parentId,dto.getParentId())
							.update(ServiceCityRM.utime,
									DateUtils.currentDateStr())
//							.update(CarInfoRM.endDate,
//									DateUtils.dateToString(dto.getStartDate(),
//											TimeFormatter.YYYY_MM_DD))
							.eq(ServiceCityRM.pk, dto.getId()));
		
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
	@Action(description = "删除城市", code = "城市信息")
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				this.genericService.deleteByPK(ServiceCity.class, ids);
				
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
		public Json check(String code,String name) {
			Json json = new Json();
			long count1 = 0;
			long count2 = 0;
			try {
				name = DtoUtil.incode(name);
				count1 = this.genericService.count(ServiceCity.class,
						new Criteria().eq(ServiceCityRM.code, code));
				if (count1 > 0) {
					json.setMsg("城市代码已存在");// 车牌存在
					json.setSuccess(false);
					return json;
				}
				count2 = this.genericService.count(ServiceCity.class,
						new Criteria().eq(ServiceCityRM.name, name));
				if (count2 > 0) {
					json.setMsg("城市名称已存在");// 车牌存在
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
