package com.pony.oa.entity;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_document")
public class Document extends BaseEntity {

	private	String	name;
	private	String	description;
	private	String	status; //状态,新建，提交流程后每一步更新状态，最后归档
	private User	creator; //创建者
	private	Workflow workflow; //流程定义
	private	String	processInstanceId; //流程实例Id,启动流程后更新值
	private Map<Long,DocumentProperty> properties; //公文动态属性
	
	@Column(length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(length=36)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="flow_id")
	public Workflow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
	@Column(length=36)
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@OneToMany(mappedBy="document",cascade=CascadeType.ALL)
	@MapKey(name="id")
	public Map<Long, DocumentProperty> getProperties() {
		return properties;
	}
	public void setProperties(Map<Long, DocumentProperty> properties) {
		this.properties = properties;
	}
	
}
