package com.pony.core.json;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.pony.core.RequestContext;

@Component @Aspect
public class JsonFilterAdvice {
	
	@Pointcut("execution(* com.pony.**.controller.*(..))")
	public void anyMethod(){}

	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
		
		MethodSignature msig = (MethodSignature) pjp.getSignature();
		
		JsonFilter annotation = msig.getMethod().getAnnotation(JsonFilter.class);
		JsonFilters annotations = msig.getMethod().getAnnotation(JsonFilters.class);
		
		if (annotation == null && annotations == null) {  
            return pjp.proceed();  
        }
		
		JSON json = JSON.get();
		if (annotation != null) {  
			Class<?> mixin = annotation.mixin();
			Class<?> bean = annotation.bean();
			
			if(bean != null){
				json.mixin(mixin, bean);
			}else{
				json.mixin(mixin, msig.getMethod().getReturnType());
			}
		}
		if (annotations != null) {  
			JsonFilter[] filters = annotations.value();
			if(filters != null && filters.length>0){
				for (JsonFilter filter : filters) {
					Class<?> mixin = filter.mixin();
					Class<?> bean = filter.bean();
					
					if(bean != null){
						json.mixin(mixin, bean);
					}else{
						json.mixin(mixin, msig.getMethod().getReturnType());
					}
				}
			}
		}
		String str = json.readAsString(pjp.proceed());
		
		HttpServletResponse response = RequestContext.getResponse();
		response.setCharacterEncoding("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
		response.flushBuffer();
		writer.close();
		
		return null;
	}
	
}
