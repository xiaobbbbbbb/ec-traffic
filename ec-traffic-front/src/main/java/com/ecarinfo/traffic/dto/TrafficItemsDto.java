package com.ecarinfo.traffic.dto;

public class TrafficItemsDto {
	private Integer customerId;
	private String carNo;
	private String carFrameNo;
	private String carEngineNo;
	private String carRegistNo;
	private String startDate;
	private String endDate;

	public TrafficItemsDto(Integer customerId,
			String carNo,String carFrameNo,String carEngineNo,String carRegistNo,String startDate,String endDate){
		this.customerId = customerId;
		this.carNo = carNo;
		this.carEngineNo = carEngineNo;
		this.carFrameNo = carFrameNo;
		this.carRegistNo = carRegistNo;
		this.startDate  = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarFrameNo() {
		return carFrameNo;
	}

	public void setCarFrameNo(String carFrameNo) {
		this.carFrameNo = carFrameNo;
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

}
