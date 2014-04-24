package com.ecarinfo.traffic.dto;

import com.ecarinfo.traffic.po.InterfaceInfo;

public class InterfaceInfoDto extends InterfaceInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer cityId;
	private String cityName;
	private Integer cityPid;
	private String cityPname;
	public String getCityPname() {
		return cityPname;
	}
	public void setCityPname(String cityPname) {
		this.cityPname = cityPname;
	}
	public Integer getCityPid() {
		return cityPid;
	}
	public void setCityPid(Integer cityPid) {
		this.cityPid = cityPid;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
