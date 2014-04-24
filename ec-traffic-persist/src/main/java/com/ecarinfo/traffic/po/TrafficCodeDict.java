package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class TrafficCodeDict implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String code;
	private String discrption;
	private Integer point;
	private Integer mark;
	private Date ctime = new Date();

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

    public String getDiscrption () {
        return discrption;
    }

    public void setDiscrption (String discrption) {
        this.discrption = discrption;
    }

    public Integer getPoint () {
        return point;
    }

    public void setPoint (Integer point) {
        this.point = point;
    }

    public Integer getMark () {
        return mark;
    }

    public void setMark (Integer mark) {
        this.mark = mark;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}