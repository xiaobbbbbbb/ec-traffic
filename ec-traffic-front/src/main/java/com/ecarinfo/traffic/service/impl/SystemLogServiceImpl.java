package com.ecarinfo.traffic.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.traffic.dao.SystemLogDao;
import com.ecarinfo.traffic.po.SystemLog;
import com.ecarinfo.traffic.rm.SystemLogRM;
import com.ecarinfo.traffic.service.SystemLogService;
import com.ecarinfo.utils.IPUtil;

@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {

	@Resource
	private SystemLogDao systemLogDao;

	// 保存日志
	public void saveLog(SystemLog dto) {
		RalUser user = EcUtil.getCurrentUser();
		SystemLog log = new SystemLog();
		log.setCtime(new Date());
		log.setCode(dto.getCode());
		log.setOpId(dto.getOpId());
		log.setOpName(dto.getOpName());
		log.setInfo(dto.getInfo());
		systemLogDao.insert(log);
	}

	// 分页
	public ECPage<SystemLog> querySystemLogPages(String opName, String info, String code, String startDate, String endDate) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1",1);
		if (StringUtils.isNotEmpty(opName)) {
			whereBy.like(SystemLogRM.opName, "%" + opName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(info)) {
			whereBy.like(SystemLogRM.info, "%" + info + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(code)) {
			whereBy.like(SystemLogRM.code, "%" + code + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startDate)) {
			whereBy.greateThenOrEquals(SystemLogRM.ctime, startDate + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			whereBy.lessThenOrEquals(SystemLogRM.ctime, endDate + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(SystemLogRM.pk, OrderType.ASC);
		ECPage<SystemLog> page = PagingManager.list(systemLogDao, whereBy);
		return page;
	}
}
