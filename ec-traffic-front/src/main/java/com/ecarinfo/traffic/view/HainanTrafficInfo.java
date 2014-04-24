package com.ecarinfo.traffic.view;

import java.util.Date;

public class HainanTrafficInfo {
	private String carNo;
	private String address;
	private String trafficType;
	private Date time;
	private String carType;
	private String executedOrg;
	
	public HainanTrafficInfo() {}
	
	public HainanTrafficInfo(String carNo, String address, String trafficType,
			Date time, String carType, String executedOrg) {
		super();
		this.carNo = carNo;
		this.address = address;
		this.trafficType = trafficType;
		this.time = time;
		this.carType = carType;
		this.executedOrg = executedOrg;
	}

	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTrafficType() {
		return trafficType;
	}
	public void setTrafficType(String trafficType) {
		this.trafficType = trafficType;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getExecutedOrg() {
		return executedOrg;
	}
	public void setExecutedOrg(String executedOrg) {
		this.executedOrg = executedOrg;
	}

	@Override
	public String toString() {
		return "HainanTrafficInfo [carNo=" + carNo + ", address=" + address
				+ ", trafficType=" + trafficType + ", time=" + time
				+ ", carType=" + carType + ", executedOrg=" + executedOrg
				+ "]";
	}	
}
