package com.pony.core.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

@Component("springUtils")
@Lazy(false)
public class SpringUtils implements InitializingBean, DisposableBean, ApplicationContextAware{

	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		context = applicationContext;
	}
	
	public static ApplicationContext getContext(){
		return context;
	}

	public void afterPropertiesSet() throws Exception {
		
	}
	
	public void destroy() throws Exception {
		context = null;
	}
	
	public static Object getBean(String beanName){
		Assert.hasText(beanName);
		return context.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> beanClass, String beanName){
		return (T)context.getBean(beanName);
	}
	
	public static String getMessage(String code, Object... args){
		LocaleResolver localLocaleResolver = getBean(LocaleResolver.class, "localeResolver");
		Locale locale = localLocaleResolver.resolveLocale(null);
		return context.getMessage(code, args,locale);
	}

}
