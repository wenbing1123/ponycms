package com.pony.oa.module;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pony.core.json.JSON;
import com.pony.core.treeable.Node;

public class ModuleTest {

	public final static String MODULES_XML = "data/modules.xml";
	
	@Test
	public void testLoad() throws JsonProcessingException{
		List<Node> nodes = new ArrayList<Node>();
		try {
			File file = new ClassPathResource(MODULES_XML).getFile();
			Document document = new SAXReader().read(file);
			//String xpath = "//modules/module";
			//List<?> elements = document.selectNodes(xpath);
			Set<String> moduleIds = new HashSet<String>();
			moduleIds.add("personal");
			moduleIds.add("task");
			StringBuilder xpath = new StringBuilder("//module");
			if(moduleIds != null && moduleIds.size()>0) {
				xpath.append("[");
				for (String moduleId : moduleIds) {
					xpath.append("@id=\"").append(moduleId).append("\" or ");
				}
				xpath.delete(xpath.lastIndexOf("or")-1, xpath.length());
				xpath.append("]");
			}
			System.out.println(xpath.toString());
			List<?> elements = document.selectNodes(xpath.toString());
			nodes.addAll(element2node(elements));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(JSON.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(nodes));
	}
	
	private List<Node> element2node(List<?> elements) {
		List<Node> nodes = new ArrayList<Node>();
		if(elements != null && elements.size() > 0) {
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				Node node = new Node(element.attributeValue("id"),element.attributeValue("text"));
				String port = element.attributeValue("port");
				if(port != null && !"".equals(port)){
					node.addAttribute("port", port);
				}
				String perm = element.attributeValue("perm");
				if(perm != null && !"".equals(perm)){
					node.addAttribute("perm", perm);
				}
				if(element.hasContent()){
					node.addChildrens(element2node(element.elements()));
				}
				nodes.add(node);
			}
		}
		return nodes;
	}
}
