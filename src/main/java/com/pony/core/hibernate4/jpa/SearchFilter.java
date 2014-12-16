package com.pony.core.hibernate4.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.pony.core.util.ConvertUtils;

public class SearchFilter {

	//GT:>;LT:<
	public enum Operator {
		EQ, LIKE, GT, LT, GE, LE
	}
	
	public enum FieldType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), H(Short.class);
		private Class<?> clazz;
		private FieldType(Class<?> clazz) {
			this.clazz = clazz;
		}
		public Class<?> getValue() {
			return clazz;
		}
	}
	
	private String fieldName;
	private Object fieldValue;
	private Operator operator;
	
	public SearchFilter(String fieldName, Operator operator, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.operator = operator;
	}
	
	/**
	 * 通过key字符串创建过滤器
	 * 
	 * @param key EQS_name,EQI_name
	 * @param value 
	 */
	public static SearchFilter with(String key, String value){
		String[] names = StringUtils.split(key, "_");
		if (names.length != 2) {
			throw new IllegalArgumentException(key + " is not a valid search filter key");
		}
		String filedName = names[1];
		String firstPart = names[0];
		Operator operator = Operator.valueOf(firstPart.substring(0,firstPart.length()-1));
		FieldType fieldType = FieldType.valueOf(firstPart.substring(firstPart.length()-1));
		Object objectValue = ConvertUtils.convertStringToObject(value, fieldType.getValue());
		return new SearchFilter(filedName, operator, objectValue);
	}
	
	/**
	 * 从请求中解析查询参数
	 * 
	 * @param request
	 * @return
	 */
	public static List<SearchFilter> parse(HttpServletRequest request){
		return parse(request, "search_");
	}
	
	/**
	 * 从请求中解析查询参数
	 * 
	 * @param request
	 * @param prefix
	 * @return
	 */
	public static List<SearchFilter> parse(HttpServletRequest request, String prefix){
		Validate.notNull(request, "Request must not be null");
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		Enumeration<?> paramNames = request.getParameterNames();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String value = request.getParameter(paramName);
				if(StringUtils.isNotBlank(value)){
					filters.add(SearchFilter.with(unprefixed, value));
				}
			}
		}
		return filters;
	}
	
	
	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public Operator getOperator() {
		return operator;
	}
	
}
