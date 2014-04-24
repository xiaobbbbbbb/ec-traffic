package com.ecarinfo.traffic.view;

import com.ecarinfo.traffic.po.CarInfo;

public class CarInfoView extends CarInfo {

	private static final long serialVersionUID = 6125396805296491118L;

	private String name;// 公司名称
	private Integer isExpired;//是否有效
	private Integer isAllowchange;
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public Integer getIsAllowchange() {
		return isAllowchange;
	}

	public void setIsAllowchange(Integer isAllowchange) {
		this.isAllowchange = isAllowchange;
	}
}
