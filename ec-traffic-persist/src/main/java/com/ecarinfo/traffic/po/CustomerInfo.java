package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class CustomerInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String code;
	private String authCode;
	private String name;
	private Integer cityId;
	private Integer cityPid;
	private Date startDate;//合同起时时间
	private Date endDate;
	private Integer carNums;
	private Integer carAllowchangeNums;
	private Integer carFixedNums;
	private Integer type;//客户类型1保险，2 4S,3 油品 ，4 其它
	private Integer isValid = 1;
	private Integer isExpired = 0;
	private String opName;//操作员
	private Date ctime;
	private Date updateTime;
	private String url;//回调url用于推送违章数据

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getAuthCode () {
        return authCode;
    }

    public void setAuthCode (String authCode) {
        this.authCode = authCode;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getCityId () {
        return cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityPid () {
        return cityPid;
    }

    public void setCityPid (Integer cityPid) {
        this.cityPid = cityPid;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCarNums () {
        return carNums;
    }

    public void setCarNums (Integer carNums) {
        this.carNums = carNums;
    }

    public Integer getCarAllowchangeNums () {
        return carAllowchangeNums;
    }

    public void setCarAllowchangeNums (Integer carAllowchangeNums) {
        this.carAllowchangeNums = carAllowchangeNums;
    }

    public Integer getCarFixedNums () {
        return carFixedNums;
    }

    public void setCarFixedNums (Integer carFixedNums) {
        this.carFixedNums = carFixedNums;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Integer getIsValid () {
        return isValid;
    }

    public void setIsValid (Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsExpired () {
        return isExpired;
    }

    public void setIsExpired (Integer isExpired) {
        this.isExpired = isExpired;
    }

    public String getOpName () {
        return opName;
    }

    public void setOpName (String opName) {
        this.opName = opName;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}