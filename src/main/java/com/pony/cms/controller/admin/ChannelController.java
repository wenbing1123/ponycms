package com.pony.cms.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChannelController {

	@RequestMapping("/channel/index.do")
	public String index() {
		return "channel/index";
	}
	
}
