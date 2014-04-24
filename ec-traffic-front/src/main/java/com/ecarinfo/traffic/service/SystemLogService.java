package com.ecarinfo.traffic.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.traffic.po.SystemLog;

public interface SystemLogService {

	// 保存日志
	void saveLog(SystemLog dto);

	ECPage<SystemLog> querySystemLogPages( String userName, String action, String type, String startDate, String endDate);
}
