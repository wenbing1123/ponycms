package com.pony.oa;

import org.apache.shiro.authc.UsernamePasswordToken;

public class AuthenticationToken extends UsernamePasswordToken{

	private static final long serialVersionUID = 1L;
	
	private 	String		captchaId;	//验证码ID
	private 	String		captcha;	//验证码
	private 	UserType	userType = UserType.USER;	//用户类型，默认为用户
	
	public AuthenticationToken(String username, String password, String captchaId, String captcha, boolean rememberMe, String host){
		super(username, password, rememberMe, host);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}
	
	public AuthenticationToken(String username, String password, String captchaId, String captcha, boolean rememberMe){
		super(username, password, rememberMe);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}
	
	public AuthenticationToken(String username, String password, String captchaId, String captcha, String host){
		super(username, password, host);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}
	
	public AuthenticationToken(String username, String password, String captchaId, String captcha){
		super(username, password);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}
