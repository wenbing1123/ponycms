package com.pony.oa.entity;

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
	
	@Column(name="_table", length=36)
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	@Column(name="_column", length=36)
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
	@Column(name="_preffix", length=36)
	public String getPreffix() {
		return preffix;
	}
	public void setPreffix(String preffix) {
		this.preffix = preffix;
	}
	
	@Column(name="_start")
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getStep() {
		return step;
	}
	
	@Column(name="_step")
	public void setStep(Integer step) {
		this.step = step;
	}
	
	@Column(name="_current")
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	
}
