package com.pony.oa.controller.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.SearchFilter.Operator;
import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.TreeRequest;
import com.pony.oa.entity.Group;

@Controller
@RequestMapping("/group")
public class GroupController {

	@Autowired ICrudService crudService;
	
	@RequestMapping("/treegrid.do")
	public @ResponseBody List<Node> treegrid(Long domainId, String id) {
		TreeRequest treeRequest = new TreeRequest(id);
		treeRequest.addProperties("code", "order", "description");
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("domain.id", Operator.EQ, domainId));
		return crudService.findTree(Group.class, filters, treeRequest);
	}
	
}
