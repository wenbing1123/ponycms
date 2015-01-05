package com.pony.oa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_form")
@JsonIgnoreProperties({"fields"})
public class Form extends BaseEntity{

	private	Workflow	workflow; 	//对应流程
	private	String		template;	//表单采用模板
	private	List<Field>	fields = new ArrayList<Field>(); 	//表单字段列表

	public void addField(Field field){
		fields.add(field);
	}
	
	public void removeField(Field field){
		fields.remove(field);
	}
	
	@OneToOne(mappedBy="form",fetch=FetchType.LAZY)
	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	
	@Column(length=100)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	@OneToMany(mappedBy="form")
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}