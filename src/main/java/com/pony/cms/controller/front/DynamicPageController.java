package com.pony.cms.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DynamicPageController {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/channel/${id}")
	public String channel(@PathVariable long id) {
		
		return "channel/default";
	}
	
	@RequestMapping("/article/${id}")
	public String article(@PathVariable long id) {
		
		return "article/default";
	}
	
}
