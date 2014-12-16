package com.pony.core.treeable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
	
	public Node(){}
	
	public Node(String id){
		this.id = id;
	}
	
	public Node(String id, String text){
		this.id = id;
		this.text = text;
	}

	private String 				id;
	private String 				text;
	private String 				iconCls; 	//节点样式类
	private Boolean 			expanded; 	//是否打开
	private Boolean 			checked; 	//是否选中
	private Boolean 			leaf; 		//是否叶子节点 
	private List<Node> 			children; 	//子节点列表
	private Map<String,Object> 	attributes; //自定义属性
	
	public void addChildren(Node node){
		if(null == children){
			children = new ArrayList<Node>();
		}
		children.add(node);
	}
	
	public void addChildrens(List<Node> nodes){
		if(null == children){
			children = new ArrayList<Node>();
		}
		children.addAll(nodes);
	}
	
	public void removeChildren(Node node){
		if(null != children){
			children.remove(node);
		}
	}
	
	public void addAttribute(String name, Object value){
		if(null == attributes){
			attributes = new HashMap<String, Object>();
		}
		attributes.put(name, value);
	}
	
	public void removeAttribute(String name){
		if(null != attributes){
			attributes.remove(name);
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
