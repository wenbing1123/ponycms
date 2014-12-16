package com.pony.core.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {

	Class<?> mixin();
	
	Class<?> bean();
	
}
