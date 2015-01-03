package com.pony.oa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_field_option")
public class Option extends BaseEntity {

	private Field 	field;	//字段
	private	String	value;	//字段值
	private String	label;	//字段标签
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn()
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
	@Column(length=36)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(length=36)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
