package com.pony.core.treeable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//树形请求
public class TreeRequest implements Treeable {

	private Serializable 	id; //请求子节点的当前父节点id
	private Integer level = 3; //请求子节点的层次数，默认为3
	private List<String> declaredProperties;
	
	public TreeRequest(){}
	
	public TreeRequest(Serializable id){
		this.id = id;
	}
	
	public TreeRequest(Serializable id, int level){
		this.id = id;
		this.level = level;
	}
	
	public Serializable getId() {
		return this.id;
	}

	public Integer getLevel() {
		return this.level;
	}
	
	public void addProperties(String...properties){
		if(declaredProperties == null){
			declaredProperties = new ArrayList<String>();
		}
		for (String property : properties) {
			declaredProperties.add(property);
		}
	}

	public String[] getDeclaredProperties() {
		if(declaredProperties != null && declaredProperties.size()>0){
			return declaredProperties.toArray(new String[declaredProperties.size()]);
		}
		return null;
	}
}
