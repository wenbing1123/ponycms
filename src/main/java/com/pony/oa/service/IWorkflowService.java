package com.pony.oa.service;

import java.util.List;

import com.pony.core.ValueLabel;
import com.pony.core.pageable.Page;
import com.pony.oa.entity.Form;
import com.pony.oa.entity.Option;
import com.pony.oa.entity.Workflow;

public interface IWorkflowService {

	public void saveOrUpdate(String name, String desc, String xmlName, byte[] xmlData);
	public void saveOrUpdate(String name, String desc, String xmlName, byte[] xmlData, String imgName, byte[] imgData);
	public void remove(Long id);
	public Workflow get(Long id);
	public List<Workflow> findAll();
	public Page<Workflow> findPage(String name);
	public byte[] getXml(Long id);
	public byte[] getImage(Long id);
	
	//========>
	public Form getForm(Long workflowId); 					//获取流程表单
	public void setForm(Long workflowId, Form form); 		//设置流程表单
	public List<ValueLabel> getAllFormTpl();		//获取存在的表单模板
	public List<ValueLabel> getAllFieldType();		//获取字段类型
	public List<ValueLabel> getAllFieldInput(); 	//获取字段样式
	public List<Option>	getOptions(Long fieldId);	//获取字段的可选项列表
	
}
