package com.pony.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class BaseEntity {

	private	Long 	id;
	private Date 	createtime;
	private	Date 	updatetime;
	private Boolean	ifuse;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIfuse() {
		return ifuse;
	}
	public void setIfuse(Boolean ifuse) {
		this.ifuse = ifuse;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createtime = new Date();
		this.ifuse = true;
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatetime = new Date();
	}
	
}
