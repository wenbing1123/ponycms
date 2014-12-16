package com.pony.cms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.pony.core.entity.TreeEntity;

@Entity
@Table(name="cms_channel")
public class Channel extends TreeEntity {

	private	Model	model;			//所属模型
	private	String	path;			//访问路径
	private	Integer order;	 		//显示顺序
	private	Boolean	hasContent;		//是否有内容
	private	Boolean	isDisplay;		//是否显示
	
	private	ChannelExt	channelExt;	//栏目更多属性
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="model_id")
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
	@Column(length=100)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
	public ChannelExt getChannelExt() {
		return channelExt;
	}
	public void setChannelExt(ChannelExt channelExt) {
		this.channelExt = channelExt;
	}
	
}
