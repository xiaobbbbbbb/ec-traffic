package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class TaskCarinfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long taskId;
	private Long carId;
	private String trafficIds;//traffic_items.id
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getTaskId () {
        return taskId;
    }

    public void setTaskId (Long taskId) {
        this.taskId = taskId;
    }

    public Long getCarId () {
        return carId;
    }

    public void setCarId (Long carId) {
        this.carId = carId;
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
}