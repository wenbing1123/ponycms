package com.pony.oa.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_serialnum")
public class Serialnum extends BaseEntity {

	private String  table; //表
	private String  column; //列
	private String  preffix = ""; //前缀
	private Integer start = 1;
	private Integer step = 1;
	private Integer current; //当前值
	
	@Column(length=36)
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	@Column(length=36)
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
	@Column(length=36)
	public String getPreffix() {
		return preffix;
	}
	public void setPreffix(String preffix) {
		this.preffix = preffix;
	}
	public Integer getStart() {
		return start;
	}
	
	@Basic
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getStep() {
		return step;
	}
	
	@Basic
	public void setStep(Integer step) {
		this.step = step;
	}
	
	@Basic
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	
}
