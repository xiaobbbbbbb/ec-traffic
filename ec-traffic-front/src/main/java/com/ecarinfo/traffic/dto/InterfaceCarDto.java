package com.ecarinfo.traffic.dto;


public class InterfaceCarDto  {

	private Integer interfaceId;//接口id
	private String carNo;//车牌号
	private String customerName;//公司名称
	private String startTime;
	private String endTime;
	
	public InterfaceCarDto(
			Integer interfaceId ,String carNo,String customerName,String startTime,String endTime){
		super();
		this.interfaceId=interfaceId;
		this.carNo=carNo;
		this.customerName=customerName;
		this.startTime=startTime;
		this.endTime=endTime;
	}
	public Integer getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
