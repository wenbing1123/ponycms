package com.pony.oa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.pony.core.entity.TreeEntity;

@Entity
@Table(name="oa_domain")
public class Domain extends TreeEntity {

	private	 String	 code;  		//域代码
	private	 Integer order;	 		//显示顺序
	private	 String	 description;   //描述
	
	@Column(length=50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="_order")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(length=200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
