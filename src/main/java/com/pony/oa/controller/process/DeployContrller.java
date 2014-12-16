package com.pony.oa.controller.process;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/process")
public class DeployContrller {

	@RequestMapping("/deploy.do")
	public String deploy() {
		
		return "process/deploy";
	}
	
}
