package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class InterfaceInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private Integer isSpider = 0;//蜘蛛
	private String desction;//接口说明
	private Integer maxNum;//最大查询量
	private Integer isValid = 1;
	private Integer workStatus = 0;//工作状态
	private Date ctime;
	private Date utime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getIsSpider () {
        return isSpider;
    }

    public void setIsSpider (Integer isSpider) {
        this.isSpider = isSpider;
    }

    public String getDesction () {
        return desction;
    }

    public void setDesction (String desction) {
        this.desction = desction;
    }

    public Integer getMaxNum () {
        return maxNum;
    }

    public void setMaxNum (Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getIsValid () {
        return isValid;
    }

    public void setIsValid (Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getWorkStatus () {
        return workStatus;
    }

    public void setWorkStatus (Integer workStatus) {
        this.workStatus = workStatus;
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