package com.pony.oa.service.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pony.core.dao.IDao;
import com.pony.oa.entity.Serialnum;
import com.pony.oa.service.ISerialnumService;

@Service("serialnumService")
@Transactional
public class SerialnumServiceImpl implements ISerialnumService {
	
	@Resource private IDao dao;

	private Lock lock = new ReentrantLock();   //支持中断和非中断，synchronized不支持中断 
	
	public Integer getNextVal(String table, String column) {
		return getNextVal(table, column, "");
	}

	public Integer getNextVal(String table, String column, String preffix) {
		
		try{
			
			lock.lock();
			
			Serialnum serialnum = 
				dao.findOne("from Serialnum where table=? and column=? and preffix=?", table, column, preffix);
			
			if(null == serialnum){
				serialnum = new Serialnum();
				serialnum.setTable(table);
				serialnum.setColumn(column);
				serialnum.setPreffix(preffix);
				serialnum.setStart(1);
				serialnum.setStep(1);
				serialnum.setCurrent(1);
				dao.save(serialnum);
				return 1;
			}else{
				serialnum.setCurrent(serialnum.getCurrent()+1);
				dao.update(serialnum);
				return serialnum.getCurrent();
			}
			
		}finally{
			lock.unlock();
		}
	}	

}
