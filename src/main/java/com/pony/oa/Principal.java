package com.pony.oa;

import java.io.Serializable;

public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long userid;		
	private String 	username;
	
	public Principal(Long userid, String username) {
		this.userid = userid;
		this.username = username;
	}
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
