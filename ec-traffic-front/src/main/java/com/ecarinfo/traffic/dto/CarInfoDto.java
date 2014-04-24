package com.ecarinfo.traffic.dto;
import java.util.Date;

import com.ecarinfo.traffic.po.CustomerCarinfo;

public class CarInfoDto  {

	private Integer customerId;
	private Date startTime;
	private Date endTime;
	private Long id;
	private String userName;
	private String carNo;
	private String carFrameNo;
	private String carEngineNo;
	private String carRegistNo;//机动车登记证书编号
	private Integer isValid;//是否审核
	private Integer isDisable;//是否keyong 
	private Integer isExpired;//有效
	private Integer trafficNums = 0;//最后一次违章次数,该值为-1时代表无违章
	private String trafficObjects;//存放traffic_code+traffic_time
	private Date lastResponseDate;
	private String trafficTimes;
	private String trafficIds;
	private Date ctime;
	private Integer isAllowchange;//是否可变更

    public Integer getIsAllowchange() {
		return isAllowchange;
	}

	public void setIsAllowchange(Integer isAllowchange) {
		this.isAllowchange = isAllowchange;
	}

	public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getCarNo () {
        return carNo;
    }

    public void setCarNo (String carNo) {
        this.carNo = carNo;
    }

    public String getCarFrameNo () {
        return carFrameNo;
    }

    public void setCarFrameNo (String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getCarEngineNo () {
        return carEngineNo;
    }

    public void setCarEngineNo (String carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public String getCarRegistNo () {
        return carRegistNo;
    }

    public void setCarRegistNo (String carRegistNo) {
        this.carRegistNo = carRegistNo;
    }

    public Integer getIsValid () {
        return isValid;
    }

    public void setIsValid (Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getTrafficNums () {
        return trafficNums;
    }

    public void setTrafficNums (Integer trafficNums) {
        this.trafficNums = trafficNums;
    }

	public String getTrafficObjects () {
        return trafficObjects;
    }

    public void setTrafficObjects (String trafficObjects) {
        this.trafficObjects = trafficObjects;
    }

    public Date getLastResponseDate () {
        return lastResponseDate;
    }

    public void setLastResponseDate (Date lastResponseDate) {
        this.lastResponseDate = lastResponseDate;
    }

    public String getTrafficTimes () {
        return trafficTimes;
    }

    public void setTrafficTimes (String trafficTimes) {
        this.trafficTimes = trafficTimes;
    }

    public String getTrafficIds () {
        return trafficIds;
    }

    public void setTrafficIds (String trafficIds) {
        this.trafficIds = trafficIds;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

}