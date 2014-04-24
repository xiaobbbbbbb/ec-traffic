package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class DepartmentInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private String mark;
	private Boolean isValid = true;
	private Date ctime;

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

    public String getMark () {
        return mark;
    }

    public void setMark (String mark) {
        this.mark = mark;
    }

    public Boolean getIsValid () {
        return isValid;
    }

    public void setIsValid (Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}