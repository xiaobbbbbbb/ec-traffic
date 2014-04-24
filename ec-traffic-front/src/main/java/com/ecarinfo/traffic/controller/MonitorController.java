package com.ecarinfo.traffic.controller;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.dto.InterfaceInfoDto;
import com.ecarinfo.traffic.service.InterfaceInfoService;

@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

	@Resource
	InterfaceInfoService interfaceInfoService;

	@Resource
	SqlSessionFactory sqlSessionFactory;

	private static final Logger logger = Logger
			.getLogger(MonitorController.class);

	@RequestMapping(value = "/interface", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {

			ECPage<InterfaceInfoDto> ECPage = this.interfaceInfoService
					.listInterfaces();
			model.addAttribute("ECPage", ECPage);
			return "monitor/interface/list";
		} catch (Exception e) {
			logger.error("客户信息列表加载失败", e);
		}
		return null;
	}

}
