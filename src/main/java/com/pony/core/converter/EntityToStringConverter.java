package com.pony.core.converter;

import org.springframework.core.convert.converter.Converter;

import com.pony.core.entity.BaseEntity;

public class EntityToStringConverter<T extends BaseEntity> implements Converter<T, String> {

	public String convert(T entity) {
		return entity.getId().toString();
	}

}
