package com.pony.core;

public class ValueLabel {

	private Object value;
	private String label;
	
	public ValueLabel(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
