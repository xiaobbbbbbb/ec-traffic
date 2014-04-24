package com.ecarinfo.survey.test;

import java.io.IOException;


import com.ecarinfo.persist.simple.DaoTool;
import com.ecarinfo.utils.HainanTrafficUtils;
import com.ecarinfo.utils.ProxyIPCollection;

public class StringTest {
	public static void main(String[] args) {
		try {
			DaoTool.initSimple("jdbc:mysql://192.168.1.241:3306/ec_traffic?characterEncoding=UTF-8&rewriteBatchedStatements=true", "developer", "123456");
			ProxyIPCollection.init();
			for (int i=0; i<10; i++) {
				System.err.println(HainanTrafficUtils.getTrafficInfos("琼B17710", "9712"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}