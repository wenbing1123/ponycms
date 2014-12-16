package com.pony.cms;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.pony.core.Result;
import com.pony.core.json.JSON;
import com.pony.core.util.RequestUtils;

public class FrontExceptionResolver extends SimpleMappingExceptionResolver {

	private static Logger logger = Logger.getLogger(FrontExceptionResolver.class);
	
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception) {
		
		logger.error("Internal Error", exception);
		if(RequestUtils.isAjaxRequest(request)){
			try{
				response.setStatus(500);
				response.setCharacterEncoding("text/html;charset=utf-8");
				Result result = Result.error("服务器内部错误，错误信息："+exception.getMessage());
				PrintWriter writer = response.getWriter();
				writer.write(JSON.get().readAsString(result));
				response.flushBuffer();
				writer.close();
			}catch (Exception e) {
				logger.error("error", e);
			}
		}else{
			String viewName = determineViewName(exception, request);
			ModelAndView mv = getModelAndView(viewName, exception, request);
			mv.getModelMap().put("errorMessage", "服务器内部错误，错误信息：" + exception.getMessage());
			return mv;
		}
		
		return null;
	}
}
