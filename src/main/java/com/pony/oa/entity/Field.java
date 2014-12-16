package com.pony.oa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_field")
public class Field extends BaseEntity {

	private Form form;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="form_id")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}
	
}
