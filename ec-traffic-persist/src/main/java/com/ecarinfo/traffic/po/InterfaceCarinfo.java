package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class InterfaceCarinfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long carId;
	private Integer interfaceId;
	private Boolean referenceCount;//引用计数值
	private Date lastRequestDate;//最近一次查询时间
	private Date ctime;
	private String customerId;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getCarId () {
        return carId;
    }

    public void setCarId (Long carId) {
        this.carId = carId;
    }

    public Integer getInterfaceId () {
        return interfaceId;
    }

    public void setInterfaceId (Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Boolean getReferenceCount () {
        return referenceCount;
    }

    public void setReferenceCount (Boolean referenceCount) {
        this.referenceCount = referenceCount;
    }

    public Date getLastRequestDate () {
        return lastRequestDate;
    }

    public void setLastRequestDate (Date lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (String customerId) {
        this.customerId = customerId;
    }
}