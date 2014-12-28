package com.pony.oa.controller.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.TreeRequest;
import com.pony.oa.entity.Domain;

@Controller
@RequestMapping("/domainController")
public class DomainController {

	@Autowired ICrudService crudService;
	
	@RequestMapping(params="treeData")
	public @ResponseBody List<Node> treeData(Long id) throws Exception{
		TreeRequest treeRequest = new TreeRequest(id,2);
		treeRequest.addProperties("code","description");
		List<Node> nodes = crudService.findTree(Domain.class, treeRequest);
		return nodes;
	}
	
	@RequestMapping(params="save")
	public @ResponseBody Result save(Domain domain){
		crudService.save(domain);
		return Result.success();
	}
	
	@RequestMapping(params="update")
	public @ResponseBody Result update(Domain domain) {
		Domain entity = crudService.find(Domain.class, domain.getId());
		entity.setName(domain.getName());
		entity.setCode(domain.getCode());
		entity.setDescription(domain.getDescription());
		crudService.update(entity);
		return Result.success();
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Domain.class, ids);
		return Result.success();
	}
	
}
