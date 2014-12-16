package com.pony.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

	//上下文路径
	public static final String 	CONTEXT_PATH 		= "base";
	
	//默认分页大小
	public static final String 	DEF_PAGE_SIZE_KEY 	= "pageSize";
	public static final int 	DEF_PAGE_SIZE 		= 20;
	
	//日期格式
	public final static DateFormat DF_DATETIME 	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat DF_DATE 		= new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat DF_TIME 		= new SimpleDateFormat("HH:mm:ss");
    
    
    //邮件模板目录
    public final static String TPLDIR_SYS_DEFINED_MAIL = "/WEB-INF/ftl/mail";
	
}
