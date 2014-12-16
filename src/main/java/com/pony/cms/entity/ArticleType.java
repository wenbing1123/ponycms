package com.pony.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="cms_article_type")
public class ArticleType extends BaseEntity{

	private		String		name;		//类型名称
	private		Integer		imgWidth;	//图片宽度
	private		Integer		imgHeight;	//图片高度
	private		Boolean		hasImg;		//是否有图片
	private		Boolean		disabled;	//是否禁用
	
	@Column(length=36)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	public Integer getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}
	
	@Basic
	public Integer getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getHasImg() {
		return hasImg;
	}
	public void setHasImg(Boolean hasImg) {
		this.hasImg = hasImg;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
}
