package com.pony.core;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.core.json.JSON;

@Component("objectMapper")
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

	public ObjectMapper getObject() throws Exception {
		return JSON.getObjectMapper();
	}

	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
