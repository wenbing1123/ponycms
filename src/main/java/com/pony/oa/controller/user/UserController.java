package com.pony.oa.controller.user;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("/save.do")
	public @ResponseBody Result save(User user){
		userService.save(user);
		return Result.success();
	}
	
	@RequestMapping("/update.do")
	public @ResponseBody Result update(User user){
		userService.update(user);
		return Result.success();
	}
	
	@RequestMapping("/remove.do")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(User.class, ids);
		return Result.success();
	}
	
	@RequestMapping("/resetPassword.do")
	public @ResponseBody Result resetPassword(Long id, Boolean random){
		String password = userService.resetPassword(id, random);
		Result result = Result.success();
		result.setObject(password);
		return result;
	}
	
	@RequestMapping("/getRoles.do")
	public @ResponseBody List<Role> getRoles(){
		return crudService.findList(Role.class);
	}
	
	@RequestMapping("/getUserRoles.do")
	public @ResponseBody String getUserRoles(Long id){
		User user = userService.find(id);
		StringBuilder builder = new StringBuilder("{");
		if(user.getDomain() != null){
			builder.append("\"domainId\"").append(":").append("\""+user.getDomain().getId()+"\"").append(",");
		}
		if(user.getGroup() != null){
			builder.append("\"groupId\"").append(":").append("\""+user.getGroup().getId()+"\"").append(",");
		}
		Set<Role> roles = user.getRoles();
		if(roles != null && roles.size() > 0){
			builder.append("\"roleIds\":[");
			for (Role role : roles) {
				builder.append("\""+role.getId()+"\"").append(",");
			}
			builder.deleteCharAt(builder.lastIndexOf(","));
			builder.append("],");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("}");
		return builder.toString();
	}
	
	//把页面的请求参数变成DOMAIN的属性
	@InitBinder    
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Domain.class,"domain",new PropertiesEditor(){

			public void setAsText(String text) throws IllegalArgumentException {
				String input = (text != null ? text.trim() : null);
				if(StringUtils.isBlank(input)){
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
				if(StringUtils.isBlank(input)){
					setValue(null);
				}else{
					Group group = crudService.find(Group.class, Long.valueOf(input));
					setValue(group);
				}
			}
			
		});
		binder.registerCustomEditor(Set.class, "roles",new CustomCollectionEditor(Set.class){

			//过滤掉空字符串及null,并根据规则转换成list
			public void setAsText(String text) throws IllegalArgumentException {
				if(StringUtils.isNotBlank(text)){
					setValue(Arrays.asList(text.split(",")));
				}
			}
			
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
