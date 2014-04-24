package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class ServiceCity implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String code;
	private String name;
	private String letter;
	private Integer parentId;
	private Integer isHot = 0;
	private Integer carFrameNo;//是否需要车架号（不为空则是需要的后几位数）
	private Integer carEngineNo;//引擎的后几位数
	private Integer carRegisterNo;
	private Date ctime;
	private Date utime;

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

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getLetter () {
        return letter;
    }

    public void setLetter (String letter) {
        this.letter = letter;
    }

    public Integer getParentId () {
        return parentId;
    }

    public void setParentId (Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsHot () {
        return isHot;
    }

    public void setIsHot (Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getCarFrameNo () {
        return carFrameNo;
    }

    public void setCarFrameNo (Integer carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public Integer getCarEngineNo () {
        return carEngineNo;
    }

    public void setCarEngineNo (Integer carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public Integer getCarRegisterNo () {
        return carRegisterNo;
    }

    public void setCarRegisterNo (Integer carRegisterNo) {
        this.carRegisterNo = carRegisterNo;
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