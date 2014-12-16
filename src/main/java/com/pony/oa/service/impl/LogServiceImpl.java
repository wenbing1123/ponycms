package com.pony.oa.service.impl;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pony.core.RequestContext;
import com.pony.core.dao.IDao;
import com.pony.core.hibernate4.jpa.Specification;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.PageRequest;
import com.pony.core.pageable.Pageable;
import com.pony.core.pageable.Sort;
import com.pony.oa.entity.Log;
import com.pony.oa.service.ILogService;

@Service("logService")
@Transactional(propagation=Propagation.REQUIRED)
public class LogServiceImpl implements ILogService {

	@Resource private IDao dao;
	
	@Value("${log.enable}")
	private boolean logEnable;
	
	public void save(Log log) {
		if(enable()){
			dao.save(log);
		}
	}
	
	public void save(String user,String ip,String operate) {
		if(enable()){
			Log log = new Log();
			log.setUser(user);
			log.setIp(ip);
			log.setOperate(operate);
			dao.save(log);
		}
	}
	
	public void save(String user,String ip,String operate,boolean success) {
		if(enable()){
			Log log = new Log();
			log.setUser(user);
			log.setIp(ip);
			log.setOperate(operate);
			log.setSuccess(success);
			dao.save(log);
		}
	}
	
	public void save(String user,String ip,String operate,boolean success,String message) {
		if(enable()){
			Log log = new Log();
			log.setUser(user);
			log.setIp(ip);
			log.setOperate(operate);
			log.setSuccess(success);
			log.setMessage(message);
			dao.save(log);
		}
	}
	
	public void remove(Long[] ids) {
		for (Long id : ids) {
			dao.remove(Log.class, id);
		}
	}
	
	@Transactional(readOnly=true)
	public Page<Log> findPage(final String user, final String ip) {
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize(), Sort.Direction.DESC, "createtime");
		
		Specification<Log> spec = new Specification<Log>() {
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				Path<String> userExp = root.get("user");
				Path<String> ipExp = root.get("ip");
				
				Predicate userPre = builder.like(userExp, user);
				Predicate ipPre = builder.equal(ipExp, ip);
				
				return builder.and(userPre, ipPre);
			}
		};
		
		return dao.findPage(Log.class, spec, pageable);
	}

	public boolean enable() {
		if(logEnable){
			return true;
		}
		return false;
	}

}
