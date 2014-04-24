package com.ecarinfo.traffic.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.traffic.dto.FileDto;
import com.ecarinfo.traffic.service.UploadFileService;
import com.ecarinfo.utils.ExcelReader;

@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService {

	@Resource
	private GenericDao genericDao;

	@Override
	public void readerUploadFile(FileDto dto) {
		ExcelReader reader=new ExcelReader();
		InputStream is = null;
		try {
			is = new FileInputStream(dto.getFilePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}if(is != null){
			try {
				POIFSFileSystem fs=new POIFSFileSystem(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HSSFWorkbook wb;
			
			Map<Integer, String> map=reader.readExcelContent(is);
	
		}
		// TODO Auto-generated method stub	
	}

}
