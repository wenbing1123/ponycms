package com.pony.oa;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class AuthenticationFilter extends FormAuthenticationFilter  {

	private String captchaIdParam = "captchaId";
	private String captchaParam	= "captcha";
	
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password	= getPassword(request);
		String captchaId = getCaptchaId(request);
		String captcha = getCaptcha(request);
		boolean isRememberMe = isRememberMe(request);
		String host = getHost(request);
		return new AuthenticationToken(username, password, captchaId, captcha, isRememberMe, host);
	}
	
	//登录失败处理
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		return super.onLoginFailure(token, e, request, response);
	}

	//登录成功处理
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	//认证失败处理
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
		return super.onAccessDenied(request, response);
	}
	
	protected String getCaptchaId(ServletRequest request){
		return WebUtils.getCleanParam(request, getCaptchaIdParam());
	}
	
	protected String getCaptcha(ServletRequest request){
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	public String getCaptchaIdParam() {
		return captchaIdParam;
	}

	public void setCaptchaIdParam(String captchaIdParam) {
		this.captchaIdParam = captchaIdParam;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

}
