package com.pony.core.hibernate4;

import java.util.List;


/**
 * 更新设置
 * @author scott
 *
 * @param <T>
 */
public class Updater<E> {
	
	/**
	 * 更新模式，MAX表示更新所有字段，但排除指定字段
	 *        MIN表示只更新非空字段，但包括指定字段
	 *        SPECIFY表示只更新指定字段
	 * 
	 * @author scott
	 *
	 */
	public enum Mode {
		MAX, MIN, SPECIFY
	}
	
	/**
	 * 最小化更新
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public static <T> Updater<T> get(T entity){
		return Updater.get(entity, Mode.MIN);
	}
	
	/**
	 * 按指定模式更新
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param mode
	 * @return
	 */
	public static <T> Updater<T> get(T entity,Mode mode){
		Updater<T> updater = new Updater<T>();
		updater.mode = mode;
		return updater;
	}
	
	/**
	 * 不更新指定字段
	 * 
	 * @param properties
	 * @return
	 */
	public Updater<E> exclude(String...properties){
		
		return this;
	}
	
	/**
	 * 更新指定字段
	 * 
	 * @param properties
	 * @return
	 */
	public Updater<E> include(String...properties){
		
		return this;
	}
	
	private E entity;
	private List<String> excludeProperties;
	private List<String> includeProperties;
	private Mode mode;
	
	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public List<String> getExcludeProperties() {
		return excludeProperties;
	}

	public void setExcludeProperties(List<String> excludeProperties) {
		this.excludeProperties = excludeProperties;
	}

	public List<String> getIncludeProperties() {
		return includeProperties;
	}

	public void setIncludeProperties(List<String> includeProperties) {
		this.includeProperties = includeProperties;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
