package com.ecarinfo.traffic.po;
import java.io.Serializable;

public class ProvinceInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//名称
	private Integer isLeaf = 0;
	private Integer carFrameNo;//是否需要提供车架号（后位数）
	private Integer carEngineNo;
	private Integer carRegisterNo;//证书后几位

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

    public Integer getIsLeaf () {
        return isLeaf;
    }

    public void setIsLeaf (Integer isLeaf) {
        this.isLeaf = isLeaf;
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
}