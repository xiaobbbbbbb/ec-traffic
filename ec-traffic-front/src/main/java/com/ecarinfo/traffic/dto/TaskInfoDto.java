package com.ecarinfo.traffic.dto;
import com.ecarinfo.traffic.po.InterfaceCarinfo;

public class TaskInfoDto extends InterfaceCarinfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer taskId;//任务Id
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	
}