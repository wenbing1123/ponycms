package com.pony.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.treeable.Node;
import com.pony.oa.service.IModuleService;
import com.pony.oa.service.IUserService;

@Controller
public class IndexController {

	@Autowired IUserService userService;
	@Autowired IModuleService moduleService;
	
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/menu.do")
	public @ResponseBody List<Node> menu() {
		//Set<String> moduleIds = userService.findModulesByUserid(SecurityUtils.getUserid());
		return moduleService.loadAll();
	}
	
}
