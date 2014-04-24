package com.ecarinfo.traffic.view;

import com.ecarinfo.traffic.po.ServiceCity;

public class ServiceCityView extends ServiceCity {

	private static final long serialVersionUID = 6125396805296491118L;

	private String parentName;// 公司名称

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
