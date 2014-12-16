package com.pony.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContextFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
	
		try{
			
			String startString = request.getParameter("start");
			String limitString = request.getParameter("limit");
			
			int start = 0;
			int limit = Constants.DEF_PAGE_SIZE;
			if(startString != null){
				start = Integer.valueOf(startString);
			}
			if(limitString != null){
				limit = Integer.valueOf(limitString);
			}
			int pageNumber = start/limit + 1;
			int pageSize = limit;
			
			RequestContext.setPageNumber(pageNumber);
			RequestContext.setPageSize(pageSize);
			RequestContext.setRequest(request);
			RequestContext.setResponse(response);
			
			chain.doFilter(req, res);
		}finally{
			RequestContext.clearPageNumber();
			RequestContext.clearPageSize();
			
			RequestContext.clearRequest();
			RequestContext.clearResponse();
			
		}
		
	}
	
	public void destroy() {

	}

}
