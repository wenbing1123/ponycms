package com.pony.oa.controller.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.TreeRequest;
import com.pony.oa.entity.Domain;

@Controller
@RequestMapping("/domain")
public class DomainController {

	@Autowired ICrudService crudService;
	
	@RequestMapping("/treegrid.do")
	public @ResponseBody List<Node> treegrid(String id) throws Exception{
		TreeRequest treeRequest = new TreeRequest(id);
		treeRequest.addProperties("code", "order", "description");
		List<Node> nodes = crudService.findTree(Domain.class, treeRequest);
		return nodes;
	}
	
}
