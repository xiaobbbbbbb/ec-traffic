package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class ServiceCityInterface implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer cityId = 0;
	private Integer interfaceId;
	private Date ctime;
	private Integer cityPid = 0;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getCityId () {
        return cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId = cityId;
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

    public Integer getCityPid () {
        return cityPid;
    }

    public void setCityPid (Integer cityPid) {
        this.cityPid = cityPid;
    }
}