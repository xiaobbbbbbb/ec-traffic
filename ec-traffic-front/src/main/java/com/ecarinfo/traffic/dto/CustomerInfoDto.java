package com.ecarinfo.traffic.dto;

import com.ecarinfo.traffic.po.CustomerInfo;

public class CustomerInfoDto extends CustomerInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cityName;
	private String cityPname;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityPname() {
		return cityPname;
	}
	public void setCityPname(String cityPname) {
		this.cityPname = cityPname;
	}
}
