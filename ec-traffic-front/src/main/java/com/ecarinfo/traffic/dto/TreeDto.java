package com.ecarinfo.traffic.dto;

import java.io.Serializable;

public class TreeDto implements Serializable {

	private static final long serialVersionUID = 1080394894122956546L;

	private Integer id; // 文件名及路径
	private String name; // 
	private Integer parentId;// 父ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
