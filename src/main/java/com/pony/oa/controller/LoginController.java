package com.pony.oa.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pony.core.Result;
import com.pony.core.json.JSON;
import com.pony.core.util.RequestUtils;
import com.pony.core.util.SpringUtils;
import com.pony.oa.AuthenticationToken;
import com.pony.oa.Principal;
import com.pony.oa.service.ILogService;

@Controller
public class LoginController {
	
	@Autowired ILogService logService;

	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String login_get() {
		
		return "login";
	}
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String login_post(String j_username,
								String j_password,
								HttpServletRequest request,
								HttpServletResponse response,
								RedirectAttributes redirectAttributes) throws Exception {
		AuthenticationToken token = new AuthenticationToken(j_username, j_password, null, null, request.getRemoteAddr());
		
		Subject subject = SecurityUtils.getSubject();
		
		Result result = Result.success();
		try{
			if(!subject.isAuthenticated()){ 
				subject.login(token);
			}else{
				Principal principal = (Principal) subject.getPrincipal();
				if(!principal.getUsername().equalsIgnoreCase(j_username)){ //当前用户为非登录用户
					subject.logout();
					subject.login(token);
				}
				
			}
		}catch(UnknownAccountException e) {
			result = Result.error(SpringUtils.getMessage("UnknownAccount"));
		}catch(IncorrectCredentialsException e) {
			result = Result.error(SpringUtils.getMessage("IncorrectCredentials"));
		}catch(LockedAccountException e) {
			result = Result.error(SpringUtils.getMessage("LockedAccount"));
		}catch(AuthenticationException e){
			result = Result.error(e.getMessage());
		}
		
		if(result.getSuccess()){
			logService.save(j_username,token.getHost(),SpringUtils.getMessage("Admin.Login"),true);
		}else{
			logService.save(j_username,token.getHost(),SpringUtils.getMessage("Admin.Login"),false,result.getMessage());
		}
		
		if(RequestUtils.isAjaxRequest(request)){
			response.setCharacterEncoding("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(JSON.get().readAsString(result));
			response.flushBuffer();
			writer.close();
			return null;
		}else{
			if(result.getSuccess()){
				return "redirect:index.do";
			}else{
				redirectAttributes.addFlashAttribute("message", result.getMessage());
				return "redirect:login.do";
			}
		}
		
	}
	
	@RequestMapping(value="/logout.do",method=RequestMethod.GET)
	public String logout() {
		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return "redirect:login.do";
	}
	
	@RequestMapping(value="/unauthorized.do",method=RequestMethod.GET)
	public String unauthorized() {
		
		return "common/unauthorized";
	}
	
}
