package com.pony.oa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_field")
public class Field extends BaseEntity {

	private Form 	form;
	private	String	name;
	private	String	label;
	private	String	type;	 //字段类型: java_lang_String,java_lang_Long,java_lang_Integer,java_lang_Float,java_lang_Boolean,java_util_Date,java_io_File
	private	String	input;	 //字段样式: text,textarea,select
	private	List<Option> options;//字段选项

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="form_id")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	@Column(length=36)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=36)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(length=36)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(length=36)
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@OneToMany(mappedBy="field",orphanRemoval=true,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
}
