package com.ecarinfo.traffic.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecarinfo.base.BaseController;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.dao.CarInfoDao;
import com.ecarinfo.traffic.dao.CustomerCarinfoDao;
import com.ecarinfo.traffic.dao.TrafficItemsDao;
import com.ecarinfo.traffic.dto.Constant;
import com.ecarinfo.traffic.dto.FileDto;
import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.po.CustomerCarinfo;
import com.ecarinfo.traffic.po.CustomerInfo;
import com.ecarinfo.traffic.rm.CarInfoRM;
import com.ecarinfo.traffic.rm.CustomerCarinfoRM;
import com.ecarinfo.traffic.rm.CustomerInfoRM;
import com.ecarinfo.traffic.service.UploadFileService;
import com.ecarinfo.traffic.view.UploadView;
import com.ecarinfo.utils.ExcelReader;
import com.ecarinfo.utils.FileStorageUtil;
import com.ecarinfo.utils.FileUtil;

@Controller
@RequestMapping("/upload")
public class FileUploadController extends BaseController {

	@Resource
	TrafficItemsDao trafficItemsDao;

	@Resource
	UploadFileService uploadFileService;

	@Resource
	SqlSessionFactory sqlSessionFactory;

	private static final Logger logger = Logger
			.getLogger(FileUploadController.class);

	// 上传文件
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void upload(HttpServletRequest request,
			HttpServletResponse response, String desc) {
		String descName = (StringUtils.isNotEmpty(desc) && desc.length() > 0) ? desc
				: Constant.DEFAULT_FILE;
		uploadFile(request, response, descName);
	}

	// 上传文件界面
	@RequestMapping(value = "/uploadUI", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String uploadUI(Model model) {
		try {
			return "traffic/upload/upload";
		} catch (Exception e) {
			logger.error("进入首页失败", e);
		}
		return null;
	}

	// 上传文件
	private void uploadFile(HttpServletRequest request,
			HttpServletResponse response, String desc) {
		Json json = new Json();
		try {

			FileDto dto = FileUtil.uploadFileService(request, desc);
			String fileName = dto.getFileName();
			String code = fileName.substring(0,6);//文件名前六位是公司代码
			//根据公司代码查找id
			CustomerInfo cinfo=this.genericService.findOne(CustomerInfo.class, new Criteria().eq(CustomerInfoRM.code, code));
			if(cinfo==null){
				logger.error("未根据模板查询到公司信息");
				json.setMsg("100");
				super.printAjax(response, JSONUtil.toJson(json));
				return;
			}
			Integer customerId = cinfo.getId();
			Set<UploadView> set = new HashSet();// 解析数据封装
			
			/*
			 * 模板上传格式判断，暂时支持.txt和.xls两种格式
			 */
			if(dto.getFileSuffix().equals(".txt")){
				set = analysisTxt(dto);
			}
			/*
			 * 解析xls模板
			 */
			else if(dto.getFileSuffix().equals(".xls")){
				set = analysisXls(dto);
			}
			else{
				json.setMsg("请使用txt,excel模板上传！");
				super.printAjax(response, JSONUtil.toJson(json));
				return;
			}
			logger.info("有效数据有：" + set.size() + "条");

			// 插入车辆信息
			saveWithBatch(validateCarinfo(set));

			// 插入对应关系
			Set<CustomerCarinfo> cs = validateCustomerCarinfo(set, customerId);
			saveCusCarWithBatch(cs);

			// 更新的数据

			// uploadFileService.readerUploadFile(dto);
			json.setMsg("有效数据"+set.size()+"条,导入"+cs.size()+"条");
			System.out.println(json);
		} catch 
		(IOException e){
			json.setSuccess(false);
			logger.error("文件解析异常!", e);
			super.printAjax(response, JSONUtil.toJson(json));
			return;
		}
		catch(Exception e) {
			json.setSuccess(false);
			logger.error("文件上传异常!", e);
			super.printAjax(response, JSONUtil.toJson(json));
			return;
		}
		super.printAjax(response, JSONUtil.toJson(json));
	}

	// 文件下载
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void fileDawnload(String fileName, String filePath,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			fileName = DtoUtil.incode(fileName);
			String path = FileStorageUtil.getBaseFileStorage(request) + "/"
					+ filePath;
			// EcUtil.fileDownload(fileName, path, response);
		} catch (Exception e) {
			logger.error("文件下载异常!", e);
		}
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
			CarInfo carInfo = this.genericService.findOne(CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(CarInfoRM.carFrameNo, view.getCarFrameNo(),CondtionSeparator.AND ));
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
	 * 筛选出customer-carinfo需添加的车辆 暂时根据需求全部添加进去（无重复对应关系）
	 */
	public Set<CustomerCarinfo> validateCustomerCarinfo(Set<UploadView> set,
			Integer customerId) {
		Iterator<UploadView> it = set.iterator();
		Set<CustomerCarinfo> carSet = new HashSet();
		while (it.hasNext()) {
			UploadView view = it.next();

			// 找出carid
			CarInfo carinfo = this.genericService.findOne(CarInfo.class,
					new Criteria().eq(CarInfoRM.carNo, view.getCarNo()).eq(CarInfoRM.carFrameNo, view.getCarFrameNo(),CondtionSeparator.AND ));

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
	
	//解析txt
	public Set<UploadView> analysisTxt(FileDto dto) throws Exception{
		Set<UploadView> set = new HashSet();
		InputStreamReader read =null;
		
			InputStream is = new FileInputStream(dto.getFilePath());
	
			 
			read = new InputStreamReader( is , "GBK");   
			BufferedReader bufferedReader = new BufferedReader(read);   
			String lineTXT = null;   
			while ((lineTXT = bufferedReader.readLine()) != null) {   //换行
				String [] stuAttr=lineTXT.toString().split(",");
				UploadView view = new UploadView();
				view.setCarNo(stuAttr[0]!=null?stuAttr[0]:"");
				view.setUserName(stuAttr[1]!=null?stuAttr[1]:"");
				view.setCarFrameNo(stuAttr[2]!=null?stuAttr[2]:"");
				view.setCarEngineNo(stuAttr[3]!=null?stuAttr[3]:"");
				view.setCarRegistNo(stuAttr[4]!=null?stuAttr[4]:"");
				//解析stuAttr数组封装Student对象
				 set.add(view); 
			}
			read.close();
	
		return set;
	}
	
	public Set<UploadView> analysisXls(FileDto dto) throws IOException{
		Set<UploadView> set = new HashSet();
		
		InputStream is = new FileInputStream(dto.getFilePath());
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);

		HSSFSheet sheet = wb.getSheetAt(0);
		
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		HSSFRow row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		ExcelReader excelReader = new ExcelReader();

		logger.info("总行数:" + rowNum);
		logger.info("总列数:" + colNum);
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {

			UploadView view = new UploadView();
			CustomerCarinfo ccinfo = new CustomerCarinfo();
			row = sheet.getRow(i);

			// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
			view.setCarNo(excelReader.getStringCellValue(row.getCell(0))
					.trim());
			view.setUserName(excelReader.getStringCellValue(row.getCell(1))
					.trim());
			view.setCarFrameNo(excelReader.getStringCellValue(
					row.getCell(2)).trim());
			view.setCarEngineNo(excelReader.getStringCellValue(
					row.getCell(3)).trim());
			view.setCarRegistNo(excelReader.getStringCellValue(
					row.getCell(4)).trim());
			view.setIsAllowchange(excelReader.getStringCellValue(
					row.getCell(5)).trim() == "" ? 0 : 1);

			set.add(view);
		}
		return set;
	}
}
