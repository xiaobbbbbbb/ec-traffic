package com.ecarinfo.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.traffic.po.SystemLog;
import com.ecarinfo.utils.IPUtil;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = Logger.getLogger(LogAspect.class);

	@Resource
	private GenericService genericService;

	public Object doSystemLog(ProceedingJoinPoint point) {
		Object obj = null;
		try {
			obj = point.proceed();
			String methodName = point.getSignature().getName();
			// 目标方法不为空
			if (StringUtils.isNotEmpty(methodName)) {
				// set与get方法除外
				if (!(methodName.startsWith("set") || methodName.startsWith("get"))) {
					MethodSignature signature = (MethodSignature) point.getSignature();
					Method method = signature.getMethod();
					if (method != null) {
						boolean hasAnnotation = method.isAnnotationPresent(Action.class);

						if (hasAnnotation) {
							Action annotation = method.getAnnotation(Action.class);
							String code = annotation.code();
							String description = annotation.description();

							if (obj instanceof Json) {
								Json returnVal = (Json) obj;
								if (returnVal != null && returnVal.getObj() != null) {
									description += "“" + returnVal.getObj() + "”";
								}
							}
							RalUser user = EcUtil.getCurrentUser();
							SystemLog log = new SystemLog();
							log.setCtime(new Date());
							log.setInfo(description);
							log.setOpName(user.getName());
							log.setOpId(user.getUserId());
							log.setCode(code);
							this.genericService.save(log);
							
						}
					}
				}
			}
		} catch (Throwable e) {
			logger.error("日志切面异常", e);
		}
		return obj;
	}
}
