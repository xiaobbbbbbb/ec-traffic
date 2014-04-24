package com.ecarinfo.traffic.dto;

import com.ecarinfo.traffic.po.CarInfo;

public class CarinfoBase extends CarInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//重新定义hashCode 
		 public int hashCode(){
		        return this.getCarNo().hashCode()+this.getCarFrameNo().hashCode();
		 }
		 
		 @Override
		 public boolean equals(Object st){
			 CarInfo view= (CarInfo) st;
		        if (this.getCarNo()==view.getCarNo()&&this.getCarFrameNo()==view.getCarFrameNo()) 
		        	return true;
		        else 
		        	return false;
		  } 
}
