package com.ecarinfo.traffic.dto;

import com.ecarinfo.traffic.po.TaskCarinfo;

public class TaskCarinfoBase extends TaskCarinfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object o){
		TaskCarinfo info = (TaskCarinfo) o;
		if(this.getCarId()==info.getCarId()&&this.getTaskId()==info.getTaskId()){
			return true;
		}else{
			return false;
		}
	}
}
