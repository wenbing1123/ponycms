package com.pony.core.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	/**
	 * 判断HTTP请求是否为AJAX请求
	 * 
	 * @param request
	 * @return
	 */
	public static Boolean isAjaxRequest(HttpServletRequest request){
		
		String requestType = request.getHeader("X-Requested-With");
		if(null != requestType && "XMLHttpRequest".equalsIgnoreCase(requestType)){
			return true;
		}
		
		return false;
	}
	
}
