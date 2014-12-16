package com.pony.oa.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class DesktopController {
	
	@RequestMapping("/desktop.do")
	public String desktop() {
		return "personal/desktop";
	}
	
}
