package com.pony.oa.controller.user;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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
import com.pony.oa.entity.Role;
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
		userService.save(user);
		return Result.success();
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(User user){
		userService.update(user);
		return Result.success();
	}
	
	@RequestMapping("/remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(User.class, ids);
		return Result.success();
	}
	
	@RequestMapping("/resetPassword")
	public @ResponseBody Result resetPassword(Long id, Boolean random){
		String password = userService.resetPassword(id, random);
		Result result = Result.success();
		result.setObject(password);
		return result;
	}
	
	@RequestMapping("/getRoles")
	public @ResponseBody List<Role> getRoles(){
		return crudService.findAll(Role.class);
	}
	
	//把页面的请求参数变成DOMAIN的属性
	@InitBinder    
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Domain.class,"domain",new PropertiesEditor(){

			public void setAsText(String text) throws IllegalArgumentException {
				String input = (text != null ? text.trim() : null);
				if(!StringUtils.hasLength(input)){
					setValue(null);
				}else{
					Domain domain = crudService.find(Domain.class, Long.valueOf(input));
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
					Group group = crudService.find(Group.class, Long.valueOf(input));
					setValue(group);
				}
			}
			
		});
		binder.registerCustomEditor(Set.class, "roles",new CustomCollectionEditor(Set.class){

			protected Object convertElement(Object element) {
				Long id = null;
				
				if(element instanceof String && !"".equals(element)){
					id = Long.valueOf((String)element);
				}else if(element instanceof Long){
					id = (Long)element;
				}
				return null==id?null:crudService.find(Role.class, id);
			}
			
		});
	}
	
}
