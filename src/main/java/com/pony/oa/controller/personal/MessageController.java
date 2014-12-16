package com.pony.oa.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class MessageController {
	
	@RequestMapping("/message.do")
	public String message(){
		
		return "personal/message";
	}

}
