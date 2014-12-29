package com.pony.core.json;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pony.core.Constants;

/**
 * JSON序列化及反序列化包装类
 * 
 * @author scott
 *
 */
public class JSON {

	/**
	 * 获取JSON实例
	 * 
	 * @return
	 */
	public static JSON get() {
		return new JSON();
	}
	
	/**
	 * 增加混合注解，动态过滤属性
	 * 
	 * @param maxinClass
	 * @param beanClass
	 * @return
	 */
	public JSON mixin(Class<?> maxinClass, Class<?> beanClass) {
		mapper.addMixInAnnotations(beanClass, maxinClass);
		return this;
	}
	
	/**
	 * 设置日期格式
	 * 
	 * @param format
	 * @return
	 */
	public JSON setDateFormat(DateFormat format){
		mapper.setDateFormat(format);
		return this;
	}
	
	/**
	 * 把java bean读取为json格式的字符串
	 * 
	 * @param bean
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String readAsString(Object bean) throws JsonGenerationException, JsonMappingException, IOException{
		return mapper.writeValueAsString(bean);
	}
	
	/**
	 * 把json格式的字符串转换为指定类型的java bean对象
	 * 
	 * @param <T>
	 * @param json
	 * @param beanClass
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> T readAsObject(String json, Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(json, beanClass);
	}
	
	/**
	 * 把json格式的字符串转换为java list对象
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> List<T> readAsList(String json, Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, beanClass);
		return mapper.readValue(json, javaType);
	}
	
	/**
	 * 把json格式的字符串转换为java map对象
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> Map<String,T> readAsMap(String json, Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException{
		JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, beanClass);
		//Map<String,?> map = mapper.readValue(json, new TypeReference<Map<String,?>>(){});
		return mapper.readValue(json, javaType);
	}
	
	private ObjectMapper mapper;
	
	public JSON(){
		init();
	}
	
	private void init(){
		mapper = new ObjectMapper();
		//为null的属性不生成
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//序列化时遇到空对象是否出错，设置对象转JSON时Java对象实际没有的属性不处理
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//反序列化时遇到未知属性时是否出错，设置输JSON转对象时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//设置日期格式
		mapper.setDateFormat(Constants.DF_DATETIME);
	}
	
	public static ObjectMapper getObjectMapper() {
		return new JSON().getMapper();
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
	
}
