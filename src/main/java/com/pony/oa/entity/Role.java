package com.pony.oa.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pony.core.ValueLabel;
import com.pony.core.entity.BaseEntity;
import com.pony.core.util.SpringUtils;

@Entity
@Table(name="oa_role")
@JsonIgnoreProperties({"perms","modules"})
public class Role extends BaseEntity {

	public enum Type {
		sys,post;
		public static List<ValueLabel> toList() {
			Type[] types = Type.values();
			List<ValueLabel> list = new ArrayList<ValueLabel>(types.length);
			for (Type type : types) {
				list.add(new ValueLabel(type.name(), SpringUtils.getMessage("Role.Type."+type.name())));
			}
			return list;
		}
	}
	
	private	String	name;
	private String	description;
	private	Integer priority; //优先级
	private	Boolean	status;
	private Boolean isSuper; //是否超级管理员
	private String 	type; //角色类型
	
	private	Set<String> perms; //权限列表
	private Set<String> modules; //模块列表
	
	@Column(length=36)
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
	
	@Basic(optional=true)
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsSuper() {
		return isSuper;
	}
	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}
	
	@Column(length=36)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name="oa_role_perm" ,joinColumns = @JoinColumn(name = "role_id"))
	@Column(name="perm_id")
	public Set<String> getPerms() {
		return perms;
	}
	public void setPerms(Set<String> perms) {
		this.perms = perms;
	}
	
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name="oa_role_module" ,joinColumns = @JoinColumn(name = "role_id"))
	@Column(name="module_id")
	public Set<String> getModules() {
		return modules;
	}
	public void setModules(Set<String> modules) {
		this.modules = modules;
	}
	
}
