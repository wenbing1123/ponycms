package com.pony.core.treeable;

import java.io.Serializable;


public interface Treeable {

	public Serializable getId();
	public Integer getLevel();
	public String[] getDeclaredProperties(); //指定声明的属性集
	
}
