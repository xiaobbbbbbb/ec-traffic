package com.ecarinfo.traffic.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.traffic.po.SystemLog;
import com.ecarinfo.traffic.service.SystemLogService;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController {

	private static final Logger logger = Logger.getLogger(SystemLogController.class);

	@Resource
	private SystemLogService systemLogService;

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listPost(String opName, String info, String code, String startDate, String endDate, ModelMap model) {
		try {
			opName = DtoUtil.incode(opName);
			info = DtoUtil.incode(info);
			code = DtoUtil.incode(code);
			ECPage<SystemLog> ECPage = systemLogService.querySystemLogPages( opName, info, code, startDate, endDate);
			model.addAttribute("ECPage", ECPage);
			RalUser user = EcUtil.getCurrentUser();
			model.put("loginName", user.getLoginName());
			return "manager/systemlog/list";
		} catch (Exception e) {
			logger.error("列表加载失败", e);
		}
		return null;
	}

	// 搜索UI
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI() {
		return "admin/systemlog/search";
	}
}
