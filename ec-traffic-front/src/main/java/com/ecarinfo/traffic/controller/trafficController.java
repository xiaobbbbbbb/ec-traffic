package com.ecarinfo.traffic.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.TrafficItemsDao;
import com.ecarinfo.traffic.dto.TrafficItemsDto;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.po.TrafficItems;
import com.ecarinfo.traffic.service.TrafficService;
import com.ecarinfo.traffic.view.TrafficItemsView;
import com.ecarinfo.utils.EcExcelUtil;

@Controller
@RequestMapping("/traffic")
public class trafficController extends BaseController {

	@Resource
	TrafficItemsDao trafficItemsDao;

	@Resource
	private GenericService genericService;

	@Resource
	private TrafficService trafficService;

	private static final Logger logger = Logger
			.getLogger(trafficController.class);

	// 首页
	@RequestMapping(value = "/index", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String index(Model model) {
		try {
			return "traffic/traffic/index";
		} catch (Exception e) {
			logger.error("进入首页失败", e);
		}
		return null;
	}

	// 列表
	@RequestMapping(value = "/listIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String listIndex(Model model) {
		List<CustomerInfo> listCustomer = this.genericService.findList(
				CustomerInfo.class, new Criteria());
		model.addAttribute("listCustomer", listCustomer);
		return "traffic/traffic/list";
	}

	// 列表
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Integer customerId, String carNo, String carFrameNo,
			String carEngineNo, String carRegistNo, String startDate,
			String endDate, Model model) {
		try {
			if (carNo != null) {
				carNo = DtoUtil.incode(carNo);
			}
			List<CustomerInfo> listCustomer = this.genericService.findList(
					CustomerInfo.class, new Criteria());
			model.addAttribute("listCustomer", listCustomer);
			TrafficItemsDto dto = new TrafficItemsDto(customerId, carNo,
					carFrameNo, carEngineNo, carRegistNo, startDate, endDate);
			model.addAttribute("dto", dto);
			ECPage<TrafficItemsView> ecpage = this.trafficService
					.queryTrafficPages(customerId, carNo, carFrameNo,
							carEngineNo, carRegistNo, startDate, endDate);
			model.addAttribute("ECPage", ecpage);
			return "traffic/traffic/list";
		} catch (Exception e) {
			logger.error("客户信息列表加载失败", e);
		}
		return null;
	}

	// 添加UI
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "traffic/traffic/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Action(description = "添加公司", code = "公司管理")
	public Json add(@RequestBody RalGroup dto) {
		Json json = new Json();
		try {
			if (dto.getParentId() == null) {
				dto.setParentId(0);
			}
			json.setMsg("公司添加成功!");
			json.setObj(dto.getName());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("公司公司添加失败!");
			logger.error("添加失败!", e);
		}
		return json;
	}

	// 下载页面
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Model model) {
		List<CustomerInfo> listCustomer = this.genericService.findList(
				CustomerInfo.class, new Criteria());
		model.addAttribute("listCustomer", listCustomer);
		return "traffic/traffic/download";
	}

	// 导出 详细
	@RequestMapping(value = "/downloadTrafficItems", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void downloadTrafficItems(Integer customerId,String startDate,String endDate,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			CustomerInfo cinfo = this.genericService.findByPK(
					CustomerInfo.class, customerId);
			if(cinfo==null){
				return ;
			}
			String date=DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER11);
			String fileName = cinfo.getName()+ "违章信息"+date+".txt";
			List<TrafficItemsView> dtos = this.trafficService
					.findTrafficItemsByCustomerId(customerId,startDate,endDate);
			Map<String, String> datas = new LinkedHashMap<String, String>();
			datas.put("title", "车辆运行报告详细信息" + "(" + dtos.size() + "条记录)");
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			datas.put("date", f.format(new Date()));
			datas.put("name", "深圳奥创科技有限公司");
			FileOutputStream fos = null;
			String path=request.getContextPath();
			File file = new File(path +"/download/"+ fileName);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file, true);// 追加
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			for (TrafficItemsView view : dtos) {
				bw.write(view.getCarNo() + "," + view.getCarFrameNo() + "|"
						+ view.getTrafficTime() + view.getAddress());
			}
			bw.close();

			// 下载
			EcExcelUtil.downloadLocal(response, file);
		} catch (Exception e) {
			logger.error("车辆运行报告汇总下载失败", e);
		}
	}
}
