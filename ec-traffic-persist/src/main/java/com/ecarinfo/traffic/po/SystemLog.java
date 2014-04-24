package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class SystemLog implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String code;
	private String info;
	private Integer opId;
	private String opName;
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getInfo () {
        return info;
    }

    public void setInfo (String info) {
        this.info = info;
    }

    public Integer getOpId () {
        return opId;
    }

    public void setOpId (Integer opId) {
        this.opId = opId;
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
}