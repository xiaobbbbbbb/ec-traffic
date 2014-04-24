package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class TrafficItems implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String carNo;
	private Long carId;
	private Date trafficTime;
	private String cityName;
	private String address;
	private String item;
	private String code;
	private Integer money;
	private Integer points;
	private String unit;
	private Integer interfaceId;//接口ID
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getCarNo () {
        return carNo;
    }

    public void setCarNo (String carNo) {
        this.carNo = carNo;
    }

    public Long getCarId () {
        return carId;
    }

    public void setCarId (Long carId) {
        this.carId = carId;
    }

    public Date getTrafficTime () {
        return trafficTime;
    }

    public void setTrafficTime (Date trafficTime) {
        this.trafficTime = trafficTime;
    }

    public String getCityName () {
        return cityName;
    }

    public void setCityName (String cityName) {
        this.cityName = cityName;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getItem () {
        return item;
    }

    public void setItem (String item) {
        this.item = item;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public Integer getMoney () {
        return money;
    }

    public void setMoney (Integer money) {
        this.money = money;
    }

    public Integer getPoints () {
        return points;
    }

    public void setPoints (Integer points) {
        this.points = points;
    }

    public String getUnit () {
        return unit;
    }

    public void setUnit (String unit) {
        this.unit = unit;
    }

    public Integer getInterfaceId () {
        return interfaceId;
    }

    public void setInterfaceId (Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}