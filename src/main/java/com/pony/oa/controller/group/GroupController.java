package com.pony.oa.controller.group;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.SearchFilter.Operator;
import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.TreeRequest;
import com.pony.oa.entity.Domain;
import com.pony.oa.entity.Group;

@Controller
@RequestMapping("/groupController")
public class GroupController {

	@Autowired ICrudService crudService;
	
	@RequestMapping(params="treeData")
	public @ResponseBody List<Node> treeData(Long domainId, Long id) {
		TreeRequest treeRequest = new TreeRequest(id);
		treeRequest.addProperties("code","description");
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("domain.id", Operator.EQ, domainId));
		return crudService.findTree(Group.class, filters, treeRequest);
	}
	
	@RequestMapping(params="save")
	public @ResponseBody Result save(Group group){
		crudService.save(group);
		return Result.success();
	}
	
	@RequestMapping(params="update")
	public @ResponseBody Result update(Group group) {
		Group entity = crudService.find(Group.class, group.getId());
		entity.setName(group.getName());
		entity.setCode(group.getCode());
		entity.setDescription(group.getDescription());
		crudService.update(entity);
		return Result.success();
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Group.class, ids);
		return Result.success();
	}
	
	//把页面的请求参数变成DOMAIN的属性,http400即参数语法错误，未设置数据转换
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
	}
	
}
