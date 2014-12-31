package com.pony.oa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_message")
@JsonIgnoreProperties({"receiver"})
public class Message extends BaseEntity {

	private	String	title; 		//标题
	private	String	content; 	//内容
	private	String	from;		//消息来源，并非一定由用户发送
	private	User	receiver;	//消息接收者
	private	Boolean	ifRead;		//是否已读
	
	@Column(length=50)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(length=255)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="_from")
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receiver_id")
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIfRead() {
		return ifRead;
	}
	public void setIfRead(Boolean ifRead) {
		this.ifRead = ifRead;
	}
	
}
