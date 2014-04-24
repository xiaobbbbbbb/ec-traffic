package com.ecarinfo.traffic.view;

import com.ecarinfo.traffic.po.CarInfo;

public class UploadView {
	
	private String carNo;
	private String userName;
	private String carEngineNo;
	private String carRegistNo;
	private String carFrameNo;
	private Integer isAllowchange;
	
	//重新定义hashCode 
	 public int hashCode(){
	        return carNo.hashCode();
	 }
	 
	 @Override
	 public boolean equals(Object st){
		 UploadView view= (UploadView) st;
	        if (carNo==view.carNo&&carFrameNo==view.carFrameNo) 
	        	return true;
	        else 
	        	return false;
	  } 
	public Integer getIsAllowchange() {
		return isAllowchange;
	}
	public void setIsAllowchange(Integer isAllowchange) {
		this.isAllowchange = isAllowchange;
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
	public String getCarEngineNo() {
		return carEngineNo;
	}
	public void setCarEngineNo(String carEngineNo) {
		this.carEngineNo = carEngineNo;
	}
	public String getCarRegistNo() {
		return carRegistNo;
	}
	public void setCarRegistNo(String carRegistNo) {
		this.carRegistNo = carRegistNo;
	}
	public String getCarFrameNo() {
		return carFrameNo;
	}
	public void setCarFrameNo(String carFrameNo) {
		this.carFrameNo = carFrameNo;
	}

}
