package com.pony.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.pony.cms.enums.CommentControl;

@Entity
@Table(name="cms_channel_ext")
public class ChannelExt {

	private	Channel	channel;		//所属栏目
	private	String	id;				//栏目Id
	private	String	name;			//栏目名称
	private	String	path;			//访问路径
	private	String	title;			//页面标题
	private	String	keywords;		//搜索关键字
	private	String 	description;	//描述
	private	Boolean	channelStatic;	//是否栏目静态化
	private	Boolean	contentStatic;	//是否内容静态化
	private	Integer order;	 		//显示顺序
	private	String	tplChannel;		//栏目模板
	private	String	tplContent;		//内容模板
	private	String	titleImg;		//标题图
	private	String	contentImg;		//内容图
	private	Integer titleImgWidth;		//栏目标题图宽度
	private	Integer	titleImgHeight;		//栏目标题图高度
	private	Integer contentImgWidth;	//栏目内容图宽度
	private	Integer	contentImgHeight;	//栏目内容图高度
	private	CommentControl commentControl;	//评论控制
	private	Boolean	allowScore;	//是否允许评分
	private	Boolean	allowShare;	//是否允许分享
	private	Boolean	allowUpdown;	//是否允许顶踩
	private	Boolean	isBlank;	//是否新窗口打开
	private	Boolean	isDisplay;	//是否显示
	private	String	link;		//外部链接
	private	String	viewGroupIds;	//访问组Id列表
	private	String	contriGroupIds;	//投稿组Id列表
	
	@OneToOne
	@PrimaryKeyJoinColumn
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	@Id @Column
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
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
	
	@Column(length=255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(length=255)
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@Column(length=255)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getChannelStatic() {
		return channelStatic;
	}
	public void setChannelStatic(Boolean channelStatic) {
		this.channelStatic = channelStatic;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getContentStatic() {
		return contentStatic;
	}
	public void setContentStatic(Boolean contentStatic) {
		this.contentStatic = contentStatic;
	}
	
	@Column(name="_order")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(length=100)
	public String getTplChannel() {
		return tplChannel;
	}
	public void setTplChannel(String tplChannel) {
		this.tplChannel = tplChannel;
	}
	
	@Column(length=100)
	public String getTplContent() {
		return tplContent;
	}
	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}
	
	@Column(length=100)
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	
	@Column(length=100)
	public String getContentImg() {
		return contentImg;
	}
	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
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
	
	@Enumerated(EnumType.STRING)
	public CommentControl getCommentControl() {
		return commentControl;
	}
	public void setCommentControl(CommentControl commentControl) {
		this.commentControl = commentControl;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getAllowScore() {
		return allowScore;
	}
	public void setAllowScore(Boolean allowScore) {
		this.allowScore = allowScore;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getAllowShare() {
		return allowShare;
	}
	public void setAllowShare(Boolean allowShare) {
		this.allowShare = allowShare;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getAllowUpdown() {
		return allowUpdown;
	}
	public void setAllowUpdown(Boolean allowUpdown) {
		this.allowUpdown = allowUpdown;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsBlank() {
		return isBlank;
	}
	public void setIsBlank(Boolean isBlank) {
		this.isBlank = isBlank;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	@Column(length=100)
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Column(length=255)
	public String getViewGroupIds() {
		return viewGroupIds;
	}
	public void setViewGroupIds(String viewGroupIds) {
		this.viewGroupIds = viewGroupIds;
	}
	
	@Column(length=255)
	public String getContriGroupIds() {
		return contriGroupIds;
	}
	public void setContriGroupIds(String contriGroupIds) {
		this.contriGroupIds = contriGroupIds;
	}
	
}
