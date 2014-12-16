package com.pony.oa.controller.cfg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.Setting;
import com.pony.core.SettingUtils;

@Controller
@RequestMapping("/cfg")
public class CfgController {

	@RequestMapping("/get")
	public @ResponseBody Setting get() {
		return SettingUtils.get();
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(Setting setting) {
		SettingUtils.set(setting);
		return Result.success();
	}
	
}
