package com.pony.oa.controller.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.ValueLabel;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.oa.entity.Role;
import com.pony.oa.service.IModuleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired ICrudService crudService;
	@Autowired IModuleService moduleService;

	@RequestMapping("/datagrid.do")
	public @ResponseBody Page<Role> datagrid() {
		return crudService.findPage(Role.class);
	}
	
	@RequestMapping("/type.do")
	public @ResponseBody List<ValueLabel> type(){
		return Role.Type.toList();
	}
	
	@RequestMapping("/module.do")
	public @ResponseBody List<Node> module(Long roleId) {
		Set<String> modules = null;
		if(roleId != null){
			Role role = crudService.find(Role.class, roleId);
			if(role != null){
				modules = role.getModules();
			}
		}
		return moduleService.loadAll(modules,true);
	}
	
	@RequestMapping("/save.do")
	public @ResponseBody Result save(Role role,String[] modules,String[] perms) throws Exception{
		if(role.getIsSuper() == null || !role.getIsSuper()){ //非超级管理员
			if(modules != null && modules.length>0){
				Set<String> moduleSet = new HashSet<String>();
				Set<String> permSet = new HashSet<String>();
				for(int i=0; i<modules.length;i++){
					moduleSet.add(modules[i]);
				}
				for(int i=0; i<perms.length;i++){
					permSet.add(perms[i]);
				}
				role.setModules(moduleSet);
				role.setPerms(permSet);
			}
		}
		crudService.save(role);
		return Result.success();
	}
	
	@RequestMapping("/update.do")
	public @ResponseBody Result update(Role role,String[] modules,String[] perms) {
		Role entity = crudService.find(Role.class, role.getId());
		entity.setName(role.getName());
		entity.setType(role.getType());
		entity.setIsSuper(role.getIsSuper());
		entity.setStatus(role.getStatus());
		entity.setPriority(role.getPriority());
		entity.setDescription(role.getDescription());
		if(role.getIsSuper() == null || !role.getIsSuper()){ //非超级管理员
			if(modules != null && modules.length>0){
				entity.getModules().clear(); //删除所有分配的模块
				entity.getPerms().clear(); //删除所有分配的权限
				Set<String> moduleSet = new HashSet<String>();
				Set<String> permSet = new HashSet<String>();
				for(int i=0; i<modules.length;i++){
					moduleSet.add(modules[i]);
				}
				for(int i=0; i<perms.length;i++){
					permSet.add(perms[i]);
				}
				entity.setModules(moduleSet);
				entity.setPerms(permSet);
			}
		}else{ //超级管理员
			if(entity.getModules() != null){
				entity.getModules().clear(); //删除所有分配的模块
				entity.getPerms().clear(); //删除所有分配的权限
			}
		}
		crudService.update(entity);
		return Result.success();
	}
	
	@RequestMapping("/remove.do")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Role.class, ids);
		return Result.success();
	}
	
}
