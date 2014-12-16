package com.pony.oa.service;

import com.pony.core.pageable.Page;
import com.pony.oa.entity.Log;

public interface ILogService {

	public void save(Log log);
	public void save(String user,String ip,String operate);
	public void save(String user,String ip,String operate,boolean success);
	public void save(String user,String ip,String operate,boolean success,String message);
	public void remove(Long[] ids);
	public Page<Log> findPage(String user, String ip);
	
	public boolean enable();
	
}
