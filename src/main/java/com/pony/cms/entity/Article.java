package com.pony.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;
import com.pony.oa.entity.User;

@Entity
@Table(name="cms_article")
public class Article extends BaseEntity {
	
	public enum Status {
		editting,checking,checked,recycle
	}

	private		Channel		channel;		//所属栏目
	private		ArticleType	articleType;	//文章类型
	private		String		title;			//文章标题
	private		Integer		topLevel;		//固定级别，值越大越靠前，值为0则不固顶
	private		Boolean		hasTitleImg;	//是否有标题图
	private		Boolean		isRecommend;	//是否推荐
	private		Status		status;			//文章状态
	private		Integer		dayViews;		//日访问量
	private		Integer		dayComments;	//日评论量
	private		Integer		dayDownloads;	//日评论量
	private		Integer		dayUps;			//日顶数
	private		User		user;			//创建用户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channel_id")
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public ArticleType getArticleType() {
		return articleType;
	}
	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	
	@Column(length=255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	public Integer getTopLevel() {
		return topLevel;
	}
	public void setTopLevel(Integer topLevel) {
		this.topLevel = topLevel;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getHasTitleImg() {
		return hasTitleImg;
	}
	public void setHasTitleImg(Boolean hasTitleImg) {
		this.hasTitleImg = hasTitleImg;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Basic
	public Integer getDayViews() {
		return dayViews;
	}
	public void setDayViews(Integer dayViews) {
		this.dayViews = dayViews;
	}
	
	@Basic
	public Integer getDayComments() {
		return dayComments;
	}
	public void setDayComments(Integer dayComments) {
		this.dayComments = dayComments;
	}
	
	@Basic
	public Integer getDayDownloads() {
		return dayDownloads;
	}
	public void setDayDownloads(Integer dayDownloads) {
		this.dayDownloads = dayDownloads;
	}
	
	@Basic
	public Integer getDayUps() {
		return dayUps;
	}
	public void setDayUps(Integer dayUps) {
		this.dayUps = dayUps;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
