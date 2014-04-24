package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class CustomerCarinfoLog implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long carId;
	private Integer customerId;
	private Boolean type;//0:insert,1:disable,2:enable
	private Date ctime;

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

    public Integer getCustomerId () {
        return customerId;
    }

    public void setCustomerId (Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getType () {
        return type;
    }

    public void setType (Boolean type) {
        this.type = type;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}