package com.pony.oa.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_user")
@JsonIgnoreProperties({"password","domain","group","roles"})
public class User extends BaseEntity{

	private	String	username;
	private	transient String password;
	private	Boolean	enabled;		//是否启用，由管理员控制
	private	Boolean	locked;			//是否锁定，由程序根据登录情况控制
	private Date 	unlockTime; 	//解锁时间，由锁定时间+锁定时长得到
	private Integer failureCount; 	//登录失败次数
	private	String	ipAddress;
	private	String	email;
	private	String	phone;
	private	String	lastLoginIp;
	private	Date lastLoginDate;
	
	private	Domain		domain; //域
	private	Group		group; //组
	private	Set<Role>	roles; //角色
	
	@Column(length=36)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(length=255)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUnlockTime() {
		return unlockTime;
	}
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	
	@Basic
	public Integer getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}
	
	@Column(length=20)
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(length=36)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length=20)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(length=20)
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="domain_id")
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_id")
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@ManyToMany(fetch=FetchType.LAZY) //删除用户不级联删除角色
	@JoinTable(name="oa_user_role"
		,joinColumns=@JoinColumn(name="user_id")
		,inverseJoinColumns=@JoinColumn(name="role_id"))
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
