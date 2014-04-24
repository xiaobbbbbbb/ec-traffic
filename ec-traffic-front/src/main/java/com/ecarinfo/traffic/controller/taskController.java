package com.ecarinfo.traffic.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.log.Action;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.TaskInfoDao;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.InterfaceInfo;
import com.ecarinfo.traffic.po.TaskInfo;
import com.ecarinfo.traffic.rm.TaskInfoRM;
import com.ecarinfo.traffic.service.CarInfoService;
import com.ecarinfo.traffic.service.TaskInfoService;
import com.ecarinfo.traffic.service.TaskService;

@Controller
@RequestMapping("/task")
public class taskController extends BaseController {
	
	@Resource
	TaskInfoDao taskInfoDao;

	@Resource
	CarInfoService carInfoService;
	
	@Resource
	TaskInfoService taskInfoService;
	
	@Resource
	TaskService taskservice;
	
	@Resource
	SqlSessionFactory sqlSessionFactory;
	
	private static final Logger logger = Logger.getLogger(taskController.class);

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer disabled, String carNo, Model model) {
		try {
			int pagerOffset = SystemContext.getPageOffset();
			ECPage<TaskInfo>  ECPage=PagingManager.list(taskInfoDao, new Criteria().setPage(pagerOffset, Constant.DEFAULT_SIZE));
			model.addAttribute("ECPage", ECPage);
			List<InterfaceInfo> listInterfaces = this.genericService.findList(
					InterfaceInfo.class, new Criteria());
			model.addAttribute("listInterfaces", listInterfaces);
			return "traffic/task/list";
		} catch (Exception e) {
			logger.error("车辆信息列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<InterfaceInfo> listInterfaces = this.genericService.findList(
				InterfaceInfo.class, new Criteria());
		model.addAttribute("listInterfaces", listInterfaces);
		return "traffic/task/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加任务", code = "任务分配")
	public Json add(@RequestBody TaskInfo dto) {
		Json json = new Json();
		try {
			taskInfoService.saveTaskInfo(dto);
//			CustomerCarinfo in=dto.getCustomerCarinfo();
			// this.ralGroupService.save(dto);
			json.setMsg("任务添加成功!");
			json.setObj(dto.getTaskNo());
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
		TaskInfo dto = this.genericService.findByPK(TaskInfo.class, id);
		model.addAttribute("dto", dto);
		List<InterfaceInfo> listInterfaces = this.genericService.findList(
				InterfaceInfo.class, new Criteria());
		model.addAttribute("listInterfaces", listInterfaces);
		return "traffic/task/add_update";
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody TaskInfo dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(
					TaskInfo.class,
					new Criteria()
							.update(TaskInfoRM.taskNo, dto.getTaskNo())
							.update(TaskInfoRM.interfaceId, dto.getInterfaceId())
							.eq(TaskInfoRM.pk, dto.getId()));
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
//		try {
//			if (ids != null && ids.length > 0) {
//				this.genericService.updateWithCriteria(
//						CustomerCarinfo.class,
//						new Criteria()
//								.update(CustomerCarinfoRM.utime,
//										DateUtils.currentDateStr())
//								.update(CustomerCarinfoRM.isDisable, 0)
//								.in(CustomerCarinfoRM.carId, ids));
//				json.setMsg("删除成功!");
//
////				List<CustomerInfo> dtos = this.genericService.findList(
////						CustomerInfo.class,
////						new Criteria().in(CustomerInfoRM.pk, ids));
////				json.setObj(dtos.get(0).getName());
//
//			} else {
//				json.setSuccess(false);
//				json.setMsg("删除失败!");
//			}
//		} catch (Exception e) {
//			logger.error("删除失败!", e);
//		}
		return null;
	}
	
	// 表单验证
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Json check( String taskNo) {
		Json json = new Json();
		long count = 0;
		try {
			taskNo = DtoUtil.incode(taskNo);
			count = this.genericService.count(TaskInfo.class,
					new Criteria().eq(TaskInfoRM.taskNo, taskNo));
			
			if (count > 0) {
				json.setMsg("任务编号存在");// 车牌存在
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
	
	 
		// 列表
		@RequestMapping(value = "/start", method = { RequestMethod.GET,
					RequestMethod.POST })
		@ResponseBody
		public Json start( Model model) {
			Json json = new Json();
			try{
				taskInfoService.task(taskservice,sqlSessionFactory);
				json.setSuccess(true);
				json.setMsg("定时任务已启动！");
				
			}catch (Exception e) {
				logger.error("定时任务启动失败!", e);
				json.setMsg("任务生成失败");// 车牌存在
				json.setSuccess(false);
				return json;
			}
			return json;
		}
		
		// 列表
		@RequestMapping(value = "/debug", method = { RequestMethod.GET,
							RequestMethod.POST })
				@ResponseBody
		public void debug( Model model) {
			
//			Map<Integer ,List<CarInfo>>	map=this.taskservice.findTaskCars(1);
			
//			this.trafficRpcForMessageCenterFacade.gripCarTraffics(arg0, arg1, arg2);
		}
		
		
}
