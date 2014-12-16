package com.pony.oa.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal")
public class DocumentController {

	@RequestMapping("/document.do")
	public String document(){
		
		return "personal/document";
	}
	
}
