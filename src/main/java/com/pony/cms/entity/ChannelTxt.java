package com.pony.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="cms_channel_txt")
public class ChannelTxt extends BaseEntity {

	private	Channel	channel;//所属栏目
	private	String	txt;	//文本内容
	private	String	txt1;	//文本内容1
	private	String	txt2;	//文本内容2
	private	String	txt3;	//文本内容3
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channel_id")
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	@Lob @Basic(fetch = FetchType.LAZY)
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	@Lob @Basic(fetch = FetchType.LAZY)
	public String getTxt1() {
		return txt1;
	}
	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}
	
	@Lob @Basic(fetch = FetchType.LAZY)
	public String getTxt2() {
		return txt2;
	}
	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}
	
	@Lob @Basic(fetch = FetchType.LAZY)
	public String getTxt3() {
		return txt3;
	}
	public void setTxt3(String txt3) {
		this.txt3 = txt3;
	}
	
}
