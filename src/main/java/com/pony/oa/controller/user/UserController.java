package com.pony.oa.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Domain;
import com.pony.oa.entity.Group;
import com.pony.oa.entity.User;
import com.pony.oa.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired ICrudService crudService;
	@Autowired IUserService userService;
	
	@RequestMapping("/datagrid.do")
	public @ResponseBody Page<User> datagrid() {
		return crudService.findPage(User.class);
	}
	
	@RequestMapping("/save")
	public @ResponseBody Result save(User user){
		crudService.save(user);
		return Result.success();
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(User user){
		User entity = crudService.find(User.class, user.getId());
		entity.setEnabled(user.getEnabled());
		entity.setIpAddress(user.getIpAddress());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getPhone());
		entity.setDomain(user.getDomain()); //通过@InitBinder进行id到entity的转换
		entity.setGroup(user.getGroup());
		crudService.update(entity);
		return Result.success();
	}
	
	@RequestMapping("/remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(User.class, ids);
		return Result.success();
	}
	
	@RequestMapping("/setRoles")
	public @ResponseBody Result setRoles(Long id, Long[] roleIds){
		userService.setRoles(id, roleIds);
		return Result.success();
	}
	
	@RequestMapping("/resetPassword")
	public @ResponseBody Result resetPassword(Long id, Boolean random){
		String password = userService.resetPassword(id, random);
		Result result = Result.success();
		result.setObject(password);
		return result;
	}
	
	@InitBinder    
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Domain.class,"domain",new PropertiesEditor(){

			public void setAsText(String text) throws IllegalArgumentException {
				String input = (text != null ? text.trim() : null);
				if(!StringUtils.hasLength(input)){
					setValue(null);
				}else{
					Domain domain = crudService.find(Domain.class, Integer.valueOf(input));
					setValue(domain);
				}
			}
			
		});
		binder.registerCustomEditor(Group.class,"group",new PropertiesEditor(){

			public void setAsText(String text) throws IllegalArgumentException {
				String input = (text != null ? text.trim() : null);
				if(!StringUtils.hasLength(input)){
					setValue(null);
				}else{
					Group group = crudService.find(Group.class, Integer.valueOf(input));
					setValue(group);
				}
			}
			
		});
	}
	
}
