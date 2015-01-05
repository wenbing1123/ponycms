package com.pony.oa.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_workflow")
@JsonIgnoreProperties({"xml","img"})
public class Workflow extends BaseEntity {

	private String	name;	//名称
	private String 	desc;	//描述
	private	byte[]	xml;	//xml
	private	byte[]	img;	//img
	private	Form 	form;	//动态表单,一个流程对应一个表单，由流程作为主体进行维护关联关系
	
	@Column(length=36)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="_desc", length=255)
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Lob @Basic(fetch=FetchType.LAZY)
	public byte[] getXml() {
		return xml;
	}
	public void setXml(byte[] xml) {
		this.xml = xml;
	}
	
	@Lob @Basic(fetch=FetchType.LAZY)
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="form_id")
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	
}
