package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class TaskInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String taskNo;//100001201309200001
	private Integer interfaceId;//接口ID
	private String status = "ready";
	private Integer requestNums = 0;
	private Date searchStime;//查询开始时间
	private Integer responseNums = 0;
	private Date searchEtime;//查询结束时间
	private Date ctime;
	private Date utime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getTaskNo () {
        return taskNo;
    }

    public void setTaskNo (String taskNo) {
        this.taskNo = taskNo;
    }

    public Integer getInterfaceId () {
        return interfaceId;
    }

    public void setInterfaceId (Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public Integer getRequestNums () {
        return requestNums;
    }

    public void setRequestNums (Integer requestNums) {
        this.requestNums = requestNums;
    }

    public Date getSearchStime () {
        return searchStime;
    }

    public void setSearchStime (Date searchStime) {
        this.searchStime = searchStime;
    }

    public Integer getResponseNums () {
        return responseNums;
    }

    public void setResponseNums (Integer responseNums) {
        this.responseNums = responseNums;
    }

    public Date getSearchEtime () {
        return searchEtime;
    }

    public void setSearchEtime (Date searchEtime) {
        this.searchEtime = searchEtime;
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