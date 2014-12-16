package com.pony.core.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanUtilsEx {

	private static Logger logger = LoggerFactory.getLogger(BeanUtilsEx.class);
	
	static{
		ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);
		ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
	}
	
	public static void copyProperty(Object target, String name, Object value) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperty(target, name, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
	}
	
	public static void copyProperties(Object source, Object target, String...ignoreProperties) {
		org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
	}
}
