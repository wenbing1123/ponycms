package com.pony.oa.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.util.SpringUtils;
import com.pony.oa.entity.User;
import com.pony.oa.service.IUserService;
import com.pony.oa.util.SecurityUtils;

@Controller
@RequestMapping("/profileController")
public class ProfileController {
	
	@Autowired IUserService userService;
	
	@RequestMapping(params="get")
	public @ResponseBody User get() {
		return userService.find(SecurityUtils.getUserid());
	}
	
	@RequestMapping(params="updatePwd")
	public @ResponseBody Result updatePwd(String oldPassword, String newPassword) {
		User user = userService.find(SecurityUtils.getUserid());
		String encOldPwd = SecurityUtils.encryptPassword(user.getUsername(), oldPassword);
		if(!encOldPwd.equals(user.getPassword())){
			return Result.error(SpringUtils.getMessage("IncorrectCredentials"));
		}
		String encNewPwd = SecurityUtils.encryptPassword(user.getUsername(), newPassword);
		user.setPassword(encNewPwd);
		userService.update(user);
		return Result.success();
	}
	
}
