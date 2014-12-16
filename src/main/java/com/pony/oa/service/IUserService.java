package com.pony.oa.service;

import java.util.Set;

import com.pony.core.pageable.Page;
import com.pony.oa.entity.Role;
import com.pony.oa.entity.User;

public interface IUserService {

	//基础业务
	public void save(User user);
	public void update(User user); //user为受管对象，非受管对象单独形成修改方法
	public void remove(Long id);
	public void remove(Long[] ids);
	public User find(Long id);
	public Page<User> findPage(String username);
		
	//特述业务
	public User findByUsername(String username);
	public Set<Role> findRolesByUserid(Long userid);
	public Set<String> findModulesByUserid(Long userid);
	public Set<String> findPermsByUserid(Long userid);
	
	/**
	 * 分配角色
	 * 
	 * @param id
	 */
	public void setRoles(Long id, Long[] roleIds);
	
	/**
	 * 重置密码
	 * 
	 * @param id 用户ID
	 * @param random 是否产生随机密码
	 * @return 返回随机密码
	 */
	public String resetPassword(Long id, Boolean random); //重置密码
	
}
