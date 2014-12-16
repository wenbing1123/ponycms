package com.pony.oa.service;

public interface ISerialnumService {

	public Integer 	getNextVal(String table, String column);
	public Integer 	getNextVal(String table, String column, String preffix);
	
}
