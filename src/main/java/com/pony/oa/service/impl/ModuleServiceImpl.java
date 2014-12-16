package com.pony.oa.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.pony.core.treeable.Node;
import com.pony.oa.service.IModuleService;

/**
 * dom4j xpath条件查询
 * 
 * 
 * 
 * @author scott
 *
 */
@Service("moduleService")
public class ModuleServiceImpl implements IModuleService {

	public final static String MODULES_XML = "data/modules.xml";
	
	protected Logger logger = Logger.getLogger(getClass());
	
	public List<Node> loadAll() {
		return loadAll(null,false);
	}
	
	public List<Node> loadAll(Set<String>ids) {
		return loadAll(ids,false);
	}
	
	public List<Node> loadAll(Set<String> ids, boolean hasCheck) {
		List<Node> nodes = new ArrayList<Node>();
		try {
			File file = new ClassPathResource(MODULES_XML).getFile();
			Document document = new SAXReader().read(file);
			String xpath = "//modules/module";
			List<?> elements = document.selectNodes(xpath);
			nodes.addAll(element2node(elements,ids,hasCheck));
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		return nodes;
	}
	
	private List<Node> element2node(List<?> elements, Set<String> ids, boolean hasCheck) {
		List<Node> nodes = new ArrayList<Node>();
		if(elements != null && elements.size() > 0) {
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				String id = element.attributeValue("id");
				Node node = new Node(id,element.attributeValue("text"));
				String iconCls = element.attributeValue("iconCls");
				if(iconCls != null && !"".equals(iconCls)){
					node.setIconCls(iconCls);
				}
				node.setExpanded(true);
				node.setLeaf(true);
				String port = element.attributeValue("port");
				if(port != null && !"".equals(port)){
					node.addAttribute("port", port);
				}
				String perm = element.attributeValue("perm");
				if(perm != null && !"".equals(perm)){
					node.addAttribute("perm", perm);
				}
				
				if(element.hasContent()){
					node.setLeaf(false);
					node.addChildrens(element2node(element.elements(), ids, hasCheck));
				}
				
				if(hasCheck){
					if(ids != null && ids.contains(id)){
						node.setChecked(true);
					}else{
						node.setChecked(false);
					}
					nodes.add(node);
				}else{
					if(ids == null || ids.contains(id)){
						nodes.add(node);
					}
				}
				
			}
		}
		return nodes;
	}

}
