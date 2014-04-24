package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class CustomerCarinfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Integer customerId;
	private Long carId;
	private Integer isExpired = 1;
	private Integer isDisable = 1;
	private Integer carNums;
	private Integer carFixedNums;
	private Integer carAllowchangeNums;
	private Integer isAllowchange;
	private Date ctime;
	private Date utime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getCustomerId () {
        return customerId;
    }

    public void setCustomerId (Integer customerId) {
        this.customerId = customerId;
    }

    public Long getCarId () {
        return carId;
    }

    public void setCarId (Long carId) {
        this.carId = carId;
    }

    public Integer getIsExpired () {
        return isExpired;
    }

    public void setIsExpired (Integer isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getIsDisable () {
        return isDisable;
    }

    public void setIsDisable (Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Integer getCarNums () {
        return carNums;
    }

    public void setCarNums (Integer carNums) {
        this.carNums = carNums;
    }

    public Integer getCarFixedNums () {
        return carFixedNums;
    }

    public void setCarFixedNums (Integer carFixedNums) {
        this.carFixedNums = carFixedNums;
    }

    public Integer getCarAllowchangeNums () {
        return carAllowchangeNums;
    }

    public void setCarAllowchangeNums (Integer carAllowchangeNums) {
        this.carAllowchangeNums = carAllowchangeNums;
    }

    public Integer getIsAllowchange () {
        return isAllowchange;
    }

    public void setIsAllowchange (Integer isAllowchange) {
        this.isAllowchange = isAllowchange;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime () {
        return utime;
    }

    public void setUtime (Date utime) {
        this.utime = utime;
    }
}