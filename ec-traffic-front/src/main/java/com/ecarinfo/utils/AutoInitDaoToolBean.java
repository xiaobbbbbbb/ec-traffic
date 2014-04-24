package com.ecarinfo.utils;

import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.simple.DaoTool;

@Component
public class AutoInitDaoToolBean implements BeanFactoryAware{

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DataSource dataSource = beanFactory.getBean(com.alibaba.druid.pool.DruidDataSource.class);
		DaoTool.init(dataSource);
		ProxyIPCollection.init();
		
		HainanTrafficUtils.init(PropUtil.getProperties("server.properties").getProperty("hainanjj"));
	}

}
