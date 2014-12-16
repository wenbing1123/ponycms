package com.pony.cms.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

/**
 * 模型实体，一个模型下可建立多个栏目，一个模型下有多个模型项
 * 
 * @author scott
 *
 */
@Entity
@Table(name="cms_model")
public class Model extends BaseEntity {

	private String	 	name; 	//模型名称
	private String	 	path;	//模型路径
	private String	 	tplChannelPreffix;	//栏目模板前缀
	private String 	 	tplArticlePreffix;	//内容模板前缀
	private Integer  	titleImgWidth;		//栏目标题图宽度
	private Integer		titleImgHeight;		//栏目标题图高度
	private Integer  	contentImgWidth;	//栏目内容图宽度
	private Integer		contentImgHeight;	//栏目内容图高度
	private Integer		order;			//显示顺序
	private Boolean		hasContent;		//是否有内容
	private Boolean		disabled;		//是否禁用
	private Boolean		isDef;			//是否默认模型
	
	private Set<ModelItem>	items;	//模型项列表
	
	@Column(length=36)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=100)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(length=100)
	public String getTplChannelPreffix() {
		return tplChannelPreffix;
	}
	public void setTplChannelPreffix(String tplChannelPreffix) {
		this.tplChannelPreffix = tplChannelPreffix;
	}
	
	@Column(length=100)
	public String getTplArticlePreffix() {
		return tplArticlePreffix;
	}
	public void setTplArticlePreffix(String tplArticlePreffix) {
		this.tplArticlePreffix = tplArticlePreffix;
	}
	
	@Basic
	public Integer getTitleImgWidth() {
		return titleImgWidth;
	}
	public void setTitleImgWidth(Integer titleImgWidth) {
		this.titleImgWidth = titleImgWidth;
	}
	
	@Basic
	public Integer getTitleImgHeight() {
		return titleImgHeight;
	}
	public void setTitleImgHeight(Integer titleImgHeight) {
		this.titleImgHeight = titleImgHeight;
	}
	
	@Basic
	public Integer getContentImgWidth() {
		return contentImgWidth;
	}
	public void setContentImgWidth(Integer contentImgWidth) {
		this.contentImgWidth = contentImgWidth;
	}
	
	@Basic
	public Integer getContentImgHeight() {
		return contentImgHeight;
	}
	public void setContentImgHeight(Integer contentImgHeight) {
		this.contentImgHeight = contentImgHeight;
	}
	
	@Column(name="_order")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getHasContent() {
		return hasContent;
	}
	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsDef() {
		return isDef;
	}
	public void setIsDef(Boolean isDef) {
		this.isDef = isDef;
	}
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="model")
	public Set<ModelItem> getItems() {
		return items;
	}
	public void setItems(Set<ModelItem> items) {
		this.items = items;
	}
	
}
