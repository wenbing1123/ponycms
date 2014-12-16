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

@Entity
@Table(name="cms_model_item")
public class ModelItem extends BaseEntity {
	
	public enum HelpPos {
		right,bottom,left,top
	}
	
	public enum DataType {
		t_string,t_integer,t_float,t_date,t_boolean
	}

	private		Model	model;		//所属模型
	private		String	name;		//字段名称
	private		String	label;		//字段标签
	private		String	defValue;	//默认值 
	private		String	optValue;	//下拉可选值
	private		Integer	order;		//排列顺序
	private		Integer	textSize;	//文本长度
	private		Integer	areaRows;	//文本域行数
	private		Integer	areaCols;	//文本域列数
	private		String	help;		//帮助信息
	private		HelpPos	helpPos;	//帮助信息位置
	private		DataType dataType;	//数据类型
	private		Boolean	isSingle;	//是否整行显示
	private		Boolean	isChannel;	//是否栏目字段
	private		Boolean	isCustom;	//是否自定义
	private		Boolean	isDisplay;	//是否显示
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="model_id")
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
	@Column(length=36)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=36)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(length=255)
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
	@Column(length=255)
	public String getOptValue() {
		return optValue;
	}
	public void setOptValue(String optValue) {
		this.optValue = optValue;
	}
	
	@Column(name="_order")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Basic
	public Integer getTextSize() {
		return textSize;
	}
	public void setTextSize(Integer textSize) {
		this.textSize = textSize;
	}
	
	@Basic
	public Integer getAreaRows() {
		return areaRows;
	}
	public void setAreaRows(Integer areaRows) {
		this.areaRows = areaRows;
	}
	
	@Basic
	public Integer getAreaCols() {
		return areaCols;
	}
	public void setAreaCols(Integer areaCols) {
		this.areaCols = areaCols;
	}
	
	@Column(length=100)
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public HelpPos getHelpPos() {
		return helpPos;
	}
	public void setHelpPos(HelpPos helpPos) {
		this.helpPos = helpPos;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsSingle() {
		return isSingle;
	}
	public void setIsSingle(Boolean isSingle) {
		this.isSingle = isSingle;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsChannel() {
		return isChannel;
	}
	public void setIsChannel(Boolean isChannel) {
		this.isChannel = isChannel;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsCustom() {
		return isCustom;
	}
	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
}
