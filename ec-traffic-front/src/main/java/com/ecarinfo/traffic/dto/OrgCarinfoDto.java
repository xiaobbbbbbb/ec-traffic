package com.ecarinfo.traffic.dto;
import java.util.List;

import com.ecarinfo.traffic.po.CarInfo;
import com.ecarinfo.traffic.view.UploadView;

public class OrgCarinfoDto  {

	private String orgCode;
	private String action;
	private List<UploadView> carinfolist;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<UploadView> getCarinfolist() {
		return carinfolist;
	}
	public void setCarinfolist(List<UploadView> carinfolist) {
		this.carinfolist = carinfolist;
	}
	
}