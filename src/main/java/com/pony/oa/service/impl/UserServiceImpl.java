package com.pony.oa.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.util.Assert;

import com.pony.core.RequestContext;
import com.pony.core.dao.IDao;
import com.pony.core.hibernate4.jpa.Specification;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.PageRequest;
import com.pony.core.pageable.Pageable;
import com.pony.core.pageable.Sort;
import com.pony.core.util.RandomUtils;
import com.pony.oa.entity.Role;
import com.pony.oa.entity.User;
import com.pony.oa.service.IUserService;
import com.pony.oa.util.SecurityUtils;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class UserServiceImpl implements IUserService {

	@Resource IDao dao;
	
	@Value("${default.password}")
	private String defaultPassword = "000000";

	@Transactional(readOnly=false)
	public void save(User user) {
		Assert.notNull(user);
		String enctypePwd = SecurityUtils.encryptPassword(user.getUsername(), defaultPassword);
		user.setPassword(enctypePwd);
		user.setLocked(false);
		user.setUnlockTime(null);
		user.setFailureCount(0);
		user.setLastLoginDate(null);
		user.setLastLoginIp(null);
		dao.save(user);
	}

	@Transactional(readOnly=false)
	public void update(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getId());
		User entity = dao.find(User.class, user.getId());
		entity.setEnabled(user.getEnabled());
		entity.setIpAddress(user.getIpAddress());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getPhone());
		entity.setDomain(user.getDomain()); //通过@InitBinder进行id到entity的转换
		entity.setGroup(user.getGroup());
		if(entity.getRoles()!=null && entity.getRoles().size()>0){
			entity.getRoles().clear(); //配置角色
		}
		entity.setRoles(user.getRoles());
		dao.update(user);
	}

	@Transactional(readOnly=false)
	public void remove(Long id) {
		Assert.notNull(id);
		
		dao.remove(User.class, id);
	}

	@Transactional(readOnly=false)
	public void remove(Long[] ids) {
		Assert.notNull(ids);
		
		for (Long id : ids) {
			dao.remove(User.class, id);
		}
	}

	public User find(Long id) {
		return dao.find(User.class, id);
	}

	public Page<User> findPage(final String username) {
		
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize(), Sort.Direction.DESC, "createtime");
		
		Specification<User> spec = new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				Path<String> usernameExp = root.get("username");
				
				return builder.equal(usernameExp, username);
			}
		};
		
		return dao.findPage(User.class, spec, pageable);
	}

	public User findByUsername(String username) {
		return dao.findOne(User.class, "username", username);
	}

	public Set<Role> findRolesByUserid(Long userid) {
		User user = find(userid);
		Set<Role> roles = user.getRoles();
		return roles;
	}
	
	public Set<String> findModulesByUserid(Long userid) {
		Set<Role> roles = findRolesByUserid(userid);
		
		Set<String> modules = new HashSet<String>();
		if(null != roles && roles.size()>0){
			for (Role role : roles) {
				modules.addAll(role.getModules());
			}
		}
		
		return modules;
	}
	
	public Set<String> findPermsByUserid(Long userid) {
		Set<Role> roles = findRolesByUserid(userid);
		
		Set<String> perms = new HashSet<String>();
		if(null != roles && roles.size()>0){
			for (Role role : roles) {
				perms.addAll(role.getPerms());
			}
		}
		
		return perms;
	}
	
	@Transactional(readOnly=false)
	public void setRoles(Long id, Long[] roleIds) {
		Assert.notNull(id);
		User user = dao.find(User.class, id);
		if(user.getRoles()!=null && user.getRoles().size()>0){
			user.getRoles().clear();
		}
		if(roleIds != null){
			List<Role> roles = dao.findList("from Role where id in (?)", roleIds);
			user.setRoles(new HashSet<Role>(roles));
		}
		dao.update(user);
	}

	@Transactional(readOnly=false)
	public String resetPassword(Long id, Boolean random) {
		Assert.notNull(id);
		User entity = dao.find(User.class, id);
		if(random){ //随机密码
			String originPassword = String.valueOf(RandomUtils.produceNumber(6));
			String enctype = SecurityUtils.encryptPassword(entity.getUsername(), originPassword);
			entity.setPassword(enctype);
			dao.update(entity);
			return originPassword;
		}else{ //默认密码
			String enctype = SecurityUtils.encryptPassword(entity.getUsername(), defaultPassword);
			entity.setPassword(enctype);
			return defaultPassword;
		}
		
		
	}

}
