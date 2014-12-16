package com.pony.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.pony.core.util.ReflectionUtils;

//系统配置操作工具类
public class SettingUtils {

	/**
	 * 配置文件定义
	 * 
	 */
	public final static String SETTING_XML = "setting.xml";
	public final static String CACHE_NAME = "com.pony.core.cache.SettingCache";
	public final static String CACHE_KEY = "_setting";
	
	/**
	 * 缓存管理实例
	 * 
	 */
	private static CacheManager cacheManager;
	static{
		Configuration config = 
				ConfigurationFactory.parseConfiguration(
						Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream("ehcache.xml"));
		
		cacheManager = CacheManager.create(config);
	}
	
	/**
	 * 获取系统配置
	 * 
	 * @return
	 */
	public static Setting get(){
		Ehcache cache = cacheManager.getCache(CACHE_NAME);
		if(cache != null && cache.isKeyInCache(CACHE_KEY)){
			Element e = cache.get(CACHE_KEY);
			return (Setting)e.getValue();
		}else{
			try{
				File settingFile = new ClassPathResource(SETTING_XML).getFile();
				Document document = new SAXReader().read(settingFile);
				List<?>settings = document.selectNodes("//Settings/Setting");
				Iterator<?>iterator = settings.iterator();
				Setting setting = new Setting();
				while(iterator.hasNext()){
					org.dom4j.Element el = (org.dom4j.Element)iterator.next();
					String fieldName = el.attributeValue("name");
					String value = el.attributeValue("value");
					try{
						ReflectionUtils.setFieldValue(setting, fieldName, value);
					}catch(Exception e) {
						//没有属性不处理
					}
				}
				cache.put(new Element(CACHE_KEY,setting));
				return setting;
			}catch(Exception e){
				throw new SystemException(e);
			}
		}
	}
	
	/**
	 * 设置系统配置
	 * 
	 * @param setting
	 */
	public static void set(Setting setting){
		OutputStream outputStream = null;
		XMLWriter writer = null;
		try{
			File settingFile = new ClassPathResource(SETTING_XML).getFile();
			Document document = new SAXReader().read(settingFile);
			List<?>settings = document.selectNodes("//Settings/Setting");
			Iterator<?>iterator = settings.iterator();
			while(iterator.hasNext()){
				org.dom4j.Element el = (org.dom4j.Element)iterator.next();
				String fieldName = el.attributeValue("name");
				Attribute valueAttr = el.attribute("value");
				Field field = ReflectionUtils.getAccessibleField(setting, fieldName);
				if (field != null) {
					Object value = field.get(setting);
					valueAttr.setValue(String.valueOf(value));
				}
			}
			
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			outputFormat.setIndent(true);
			outputFormat.setIndent("\t");
			outputFormat.setNewlines(true);
			
			outputStream = new FileOutputStream(settingFile);
	        
	        writer = new XMLWriter(outputStream, outputFormat);
	        writer.write(document);
	        writer.close();
	        
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					throw new SystemException(e);
				}
			}
			IOUtils.closeQuietly(outputStream);
		}
		Ehcache cache = cacheManager.getCache(CACHE_NAME);
		cache.put(new Element(CACHE_KEY,setting));
	}
	
}
