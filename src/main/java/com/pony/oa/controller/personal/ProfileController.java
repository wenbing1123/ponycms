package com.pony.oa.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class ProfileController {

	@RequestMapping("/profile.do")
	public String profile(){
		
		return "personal/profile";
	}
	
	
}
