package com.pony.oa.controller.cfg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.Setting;
import com.pony.core.SettingUtils;

@Controller
@RequestMapping("/cfgController")
public class CfgController {

	@RequestMapping(params="get")
	public @ResponseBody Setting get() {
		return SettingUtils.get();
	}
	
	@RequestMapping(params="update")
	public @ResponseBody Result update(Setting setting) {
		SettingUtils.set(setting);
		return Result.success();
	}
	
}
