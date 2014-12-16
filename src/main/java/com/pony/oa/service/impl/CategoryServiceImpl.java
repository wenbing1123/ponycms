package com.pony.oa.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.pony.core.ValueLabel;
import com.pony.core.treeable.Node;
import com.pony.oa.service.ICategoryService;

/**
 * dom4j xpath
 * 
 * @author scott
 *
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

	public final static String CATEGORY_XML = "data/category.xml";
	
	protected Logger logger = Logger.getLogger(getClass());
	
	public List<ValueLabel> getListByType(String type){
		List<ValueLabel> list = new ArrayList<ValueLabel>();
		
		try {
			File file = new ClassPathResource(CATEGORY_XML).getFile();
			Document document = new SAXReader().read(file);
			
			List<?> elements = document.selectNodes("//category[@type='"+type+"']");
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				list.add(new ValueLabel(element.attributeValue("id"),element.attributeValue("text")));
			}
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		
		return list;
	}
	
	public List<Node> getTreeByType(String type) {
		List<Node> nodes = new ArrayList<Node>();
		
		try {
			File file = new ClassPathResource(CATEGORY_XML).getFile();
			Document document = new SAXReader().read(file);
			
			List<?> elements = document.selectNodes("//category[@type='"+type+"']");
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				Node node = new Node(element.attributeValue("id"),element.attributeValue("text"));
				node.setLeaf(true);
				nodes.add(node);
			}
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		
		return nodes;
	}

}
