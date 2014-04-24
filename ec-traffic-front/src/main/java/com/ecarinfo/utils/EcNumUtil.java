package com.ecarinfo.utils;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * EC项目随机流水号
 */
public class EcNumUtil {
	private static final Logger logger = Logger.getLogger(EcNumUtil.class);

	/*
	 * 18位可能有重复
	 */
	public static String getBsnum() {
		Date date =new Date();
		String str=DateUtils.dateToString(date, TimeFormatter.FORMATTER11);
		long s=Math.round(Math.random()*8999+1000);
		return str+s;
	}
	
	public static void main(String s[]){
		System.out.println(getBsnum());
	}
}
