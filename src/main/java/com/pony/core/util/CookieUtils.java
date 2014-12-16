package com.pony.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

//功能COOKIE相关的数据存取
public class CookieUtils {
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}
	
	public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, 
			String name, String value){
		return addCookie(request,response,name,value,null,null);
	}
	
	public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, 
			String name, String value,
			Integer expires, String domain){
		
		Cookie cookie = new Cookie(name, value);
		if(null != expires){
			cookie.setMaxAge(expires);
		}
		if(StringUtils.isNotBlank(domain)){
			cookie.setDomain(domain);
		}
		
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx)?"/":ctx);
		response.addCookie(cookie);
		return cookie;
	}
	
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, 
			String name){
		clearCookie(request, response, name, null);
	}
	
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response,
			String name,
			String domain){
		
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		if(StringUtils.isNotBlank(domain)){
			cookie.setDomain(domain);
		}
		
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx)?"/":ctx);
		response.addCookie(cookie);
	}
}
