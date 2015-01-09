package com.pony.oa.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pony.core.RequestContext;
import com.pony.core.ValueLabel;
import com.pony.core.dao.IDao;
import com.pony.core.facade.ActivitiFacade;
import com.pony.core.hibernate4.jpa.Specification;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.PageRequest;
import com.pony.core.pageable.Pageable;
import com.pony.oa.entity.Field;
import com.pony.oa.entity.Form;
import com.pony.oa.entity.Option;
import com.pony.oa.entity.Workflow;
import com.pony.oa.service.IWorkflowService;

@Service("workflowService")
@Transactional
public class WorkflowServiceImpl implements IWorkflowService {

	public final static String FORM_DEFINITION_XML = "data/form_definition.xml";
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired IDao dao;
	@Autowired ActivitiFacade activitiFacade;
	
	public void saveOrUpdate(String name, String desc, String xmlName, byte[] xmlData){
		//部署流程
		activitiFacade.deploy(name,xmlName,xmlData);
		//保存记录
		Workflow workflow = dao.findOne("from Workflow w where w.name=?", "");
		if(workflow == null){
			workflow = new Workflow();
			workflow.setName(name);
			workflow.setDesc(desc);
			workflow.setXml(xmlData);
			workflow.setForm(new Form()); //创建时定义一个空的表单对象
			dao.save(workflow);
		}else{
			workflow.setDesc(desc);
			workflow.setXml(xmlData);
			dao.update(workflow);
		}
	}
	
	public void saveOrUpdate(String name, String desc, String xmlName, byte[] xmlData, String imgName, byte[] imgData) {
		//部署流程
		activitiFacade.deploy(name,xmlName,xmlData,imgName,imgData);
		//保存记录
		Workflow workflow = dao.findOne("from Workflow w where w.name=?", "");
		if(workflow == null){
			workflow = new Workflow();
			workflow.setName(name);
			workflow.setDesc(desc);
			workflow.setXml(xmlData);
			workflow.setImg(imgData);
			workflow.setForm(new Form()); //创建时定义一个空的表单对象
			dao.save(workflow);
		}else{
			workflow.setDesc(desc);
			workflow.setXml(xmlData);
			workflow.setImg(imgData);
			dao.update(workflow);
		}
	}

	public void remove(Long id) {
		Workflow workflow = dao.find(Workflow.class, id);
		activitiFacade.undeploy(workflow.getName());
		dao.remove(workflow);
	}

	public Workflow get(Long id) {
		return dao.find(Workflow.class, id);
	}
	
	public List<Workflow> findAll(){
		return dao.findList("from Workflow");
	}

	public Page<Workflow> findPage(final String name) {
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize());
		Specification<Workflow> spec = new Specification<Workflow>() {

			public Predicate toPredicate(Root<Workflow> root,CriteriaQuery<?> query, CriteriaBuilder builder) {
				if(StringUtils.isNotBlank(name)){
				Path<String> nameExp = root.get("name");
					return builder.like(nameExp, name);
				}
				return builder.conjunction();
			}
			
		};
		return dao.findPage(Workflow.class, spec, pageable);
	}

	public byte[] getXml(Long id) {
		Workflow workflow = dao.find(Workflow.class, id);
		return workflow.getXml();
	}

	public byte[] getImage(Long id) {
		Workflow workflow = dao.find(Workflow.class, id);
		return workflow.getImg();
	}

	public Form getForm(Long workflowId) {
		Workflow workflow = dao.find(Workflow.class, workflowId);
		return workflow.getForm();
	}
	
	public void saveOrUpdateOptionList(List<Option> options){
		if(options != null && options.size()>0){
			for (Option option : options) {
				if(option.getId() == null){
					dao.save(option);
				}else{
					Option entity = dao.find(Option.class, option.getId());
					entity.setValue(option.getValue());
					entity.setLabel(option.getLabel());
					dao.update(entity);
				}
			}
		}
	}
	
	public void updateForm(Form form, String fieldIds){
		Form entity  =  dao.find(Form.class, form.getId());
		entity.setTemplate(form.getTemplate());
		if(StringUtils.isNoneBlank(fieldIds)){
			String[] ids = fieldIds.split(",");
			//dao.findList("from Field f where f.id in (?1)", new Object[]{ids});
			for (String idStr : ids) {
				Long id = Long.valueOf(idStr);
				Field field = dao.find(Field.class, id);
				form.addField(field);
			}
		}
		dao.update(entity);
	}
	
	public List<ValueLabel> getAllFormTpl(){
		List<ValueLabel> list = new ArrayList<ValueLabel>();
		
		try {
			File file = new ClassPathResource(FORM_DEFINITION_XML).getFile();
			Document document = new SAXReader().read(file);
			
			List<?> elements = document.selectNodes("//Form/Templates/Template");
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				list.add(new ValueLabel(element.attributeValue("id"),element.attributeValue("name")));
			}
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		
		return list;
	}

	public List<ValueLabel> getAllFieldType() {
		List<ValueLabel> list = new ArrayList<ValueLabel>();
		
		try {
			File file = new ClassPathResource(FORM_DEFINITION_XML).getFile();
			Document document = new SAXReader().read(file);
			
			List<?> elements = document.selectNodes("//Field/Types/type");
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				list.add(new ValueLabel(element.attributeValue("id"),element.attributeValue("name")));
			}
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		
		return list;
	}

	public List<ValueLabel> getAllFieldInput() {
		List<ValueLabel> list = new ArrayList<ValueLabel>();
		
		try {
			File file = new ClassPathResource(FORM_DEFINITION_XML).getFile();
			Document document = new SAXReader().read(file);
			
			List<?> elements = document.selectNodes("//Field/Inputs/Input");
			Iterator<?> iterator = elements.iterator();
			while(iterator.hasNext()){
				Element element = (Element)iterator.next();
				list.add(new ValueLabel(element.attributeValue("id"),element.attributeValue("name")));
			}
		} catch (Exception e) {
			logger.error("load xml error!", e);
		}
		
		return list;
	}

	public List<Option> getOptions(Long fieldId) {
		Assert.notNull(fieldId);
		Field field = dao.find(Field.class, fieldId);
		return field.getOptions();
	}

}
