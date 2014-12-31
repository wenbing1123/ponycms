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

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_workflow")
public class Workflow extends BaseEntity {

	private String	name;	//流程名称，如请假流程
	private	byte[]	xml;	//流程定义XML
	private	byte[]	img;	//流程定义图片
	private	Form 	form;	//动态表单,一个流程对应一个表单，由流程作为主体进行维护关联关系
	
	@Column(length=36)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="form_id")
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	
}
