package com.ecarinfo.traffic.view;

import com.ecarinfo.traffic.po.TrafficItems;

public class TrafficItemsView extends TrafficItems {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String carNo;
	private String userName;
	private String carFrameNo;
	public String getCarFrameNo() {
		return carFrameNo;
	}
	public void setCarFrameNo(String carFrameNo) {
		this.carFrameNo = carFrameNo;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
