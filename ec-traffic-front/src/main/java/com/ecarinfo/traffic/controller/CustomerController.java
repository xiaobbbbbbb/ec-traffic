package com.ecarinfo.traffic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.CustomerInfoDao;
import com.ecarinfo.traffic.dto.CustomerInfoDto;
import com.ecarinfo.traffic.dto.TreeDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.po.ServiceCity;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.rm.ServiceCityRM;
import com.ecarinfo.traffic.service.CustomerInfoService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	@Resource
	CustomerInfoDao customerDao;
	
	@Resource
	CustomerInfoService customerInfoService;

	private static final Logger logger = Logger
			.getLogger(CustomerController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {

			ECPage<CustomerInfo> ECPage = PagingManager.list(customerDao,
					new Criteria().setPage(1, 20));
			model.addAttribute("ECPage", ECPage);
			return "traffic/customer/customer_list";
		} catch (Exception e) {
			logger.error("客户信息列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "traffic/customer/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加公司", code = "公司管理")
	public Json add(@RequestBody CustomerInfo dto) {
		Json json = new Json();
		try {
			RalUser currentUser = EcUtil.getCurrentUser();
			dto.setCtime(new Date());
			dto.setOpName(currentUser.getName());
			dto.setAuthCode("999999");
			this.genericService.save(dto);
			json.setMsg("公司添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("公司公司添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/updateUI", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
//		CustomerInfo dto = this.genericService.findByPK(CustomerInfo.class, id);
		CustomerInfoDto dto = this.customerInfoService.findById(id);
		if(dto.getCityPname()!=null){
			dto.setCityName(dto.getCityPname());
		}
		model.addAttribute("dto", dto);
		return "traffic/customer/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody CustomerInfoDto dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					CustomerInfo.class,
					new Criteria()
							.update(CustomerInfoRM.carNums, dto.getCarNums())
							.update(CustomerInfoRM.code, dto.getCode())
							.update(CustomerInfoRM.isExpired,
									dto.getIsExpired())
							.update(CustomerInfoRM.isValid, dto.getIsValid())
							.update(CustomerInfoRM.cityId, dto.getCityId())
							.update(CustomerInfoRM.cityPid, dto.getCityPid())
							.update(CustomerInfoRM.startDate,
									DateUtils.dateToString(dto.getStartDate(),
											TimeFormatter.YYYY_MM_DD))
							.update(CustomerInfoRM.name, dto.getName())
							.update(CustomerInfoRM.updateTime,
									DateUtils.currentDateStr())
							.update(CustomerInfoRM.endDate,
									DateUtils.dateToString(dto.getStartDate(),
											TimeFormatter.YYYY_MM_DD))
							.eq(CustomerInfoRM.pk, dto.getId()));
			json.setMsg("查勘员修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查勘员修改失败!");
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
						CustomerInfo.class,
						new Criteria()
								.update(CustomerInfoRM.updateTime,
										DateUtils.currentDateStr())
								.update(CustomerInfoRM.isExpired, 0)
								.in(CustomerInfoRM.pk, ids));
				json.setMsg("删除成功!");

				List<CustomerInfo> dtos = this.genericService.findList(
						CustomerInfo.class,
						new Criteria().in(CustomerInfoRM.pk, ids));
				json.setObj(dtos.get(0).getName());

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
			count1 = this.genericService.count(CustomerInfo.class,
					new Criteria().eq(CustomerInfoRM.code, code));
			if (count1 > 0) {
				json.setMsg("公司代码已存在");// 车牌存在
				json.setSuccess(false);
				return json;
			}
			count2 = this.genericService.count(CustomerInfo.class,
					new Criteria().eq(CustomerInfoRM.name, name));
			if (count2 > 0) {
				json.setMsg("公司名称已存在");// 车牌存在
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
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(Boolean showUrl, Integer id, HttpServletRequest request) {
		List<ZtreeDto> zTree = new ArrayList<ZtreeDto>();
		try {
			Integer pid = (id != null && id > 0) ? id : 0;

			Criteria whereBy = new Criteria();
			whereBy.eq(ServiceCityRM.parentId, pid);
			List<TreeDto> dtos = this.customerInfoService.findTree(whereBy);

			String webpath = request.getContextPath();
			String target = "group_rightFrame";
			String url = webpath + "/ralasafe/group/updateUI?id=";
			for (TreeDto dto : dtos) {
				ZtreeDto tree = new ZtreeDto();
				tree.setId(dto.getId());
				tree.setName(dto.getName());
				if(dto.getParentId()==0){
					tree.setIsParent(true);
					tree.setOpen(true);
				}
				if (showUrl) {
					tree.setUrl(url);
					tree.setTarget(target);
				}
				tree.setpId(dto.getParentId());
				zTree.add(tree);
			}
		} catch (Exception e) {
			logger.error("公司树获取失败!", e);
		}
		System.out.println(zTree.size());
		return zTree;
	}
}
