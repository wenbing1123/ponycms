package com.pony.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContext {

	private static ThreadLocal<Integer> pageNumber = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	
	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	
	public static int getPageNumber() {
		Integer s = pageNumber.get();
		return s!=null ? s.intValue() : 1;
	}
	
	public static void setPageNumber(int value) {
		pageNumber.set(value);
	}
	
	public static void clearPageNumber(){
		pageNumber.remove();
	}
	
	public static Integer getPageSize() {
		Integer s = pageSize.get();
		return s!=null ? s.intValue() : Constants.DEF_PAGE_SIZE;
	}
	
	public static void setPageSize(int value) {
		pageSize.set(value);
	}
	
	public static void clearPageSize(){
		pageSize.remove();
	}
	
	public static HttpServletRequest getRequest(){
		return request.get();
	}
	
	public static void setRequest(HttpServletRequest value) {
		request.set(value);
	}
	
	public static void clearRequest() {
		request.remove();
	}
	
	public static HttpServletResponse getResponse(){
		return response.get();
	}
	
	public static void setResponse(HttpServletResponse value) {
		response.set(value);
	}
	
	public static void clearResponse() {
		response.remove();
	}
	
}
