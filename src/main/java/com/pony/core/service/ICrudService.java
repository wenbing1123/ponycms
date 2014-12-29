package com.pony.core.service;

import java.io.Serializable;
import java.util.List;

import com.pony.core.entity.BaseEntity;
import com.pony.core.entity.TreeEntity;
import com.pony.core.hibernate4.Updater;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.Sort;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.Treeable;

/**
 * 简单增删改查业务接口，复杂查询需要自定义接口及实现
 * 
 * @author scott
 *
 */
public interface ICrudService {

	/**
	 * 保存对象
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T> void save(T entity);
	
	/**
	 * 手动更新对象，即获取受管实体后手动设置字段的值来更新字段
	 * 
	 * @param <T>
	 * @param entity
	 * @throws Exception
	 */
	public <T extends BaseEntity> void update(T entity);
	
	/**
	 * 自动更新对象，即自动根据模式规则来更新字段
	 * 
	 * @param <T>
	 * @param updater
	 * @throws Exception
	 */
	public <T extends BaseEntity> void update(Updater<T> updater) throws Exception;
	
	/**
	 * 删除对象
	 * 
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param id
	 */
	public <T,PK extends Serializable> void remove(Class<T> entityClass, PK id);
	
	/**
	 * 删除对象集合
	 * 
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param ids
	 */
	public <T,PK extends Serializable> void remove(Class<T> entityClass, PK[] ids);
	
	/**
	 * 加载对象
	 * 
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T,PK extends Serializable> T find(Class<T> entityClass, PK id);
	
	/**
	 * 查询实体列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findList(Class<T> entityClass);
	
	/**
	 * 查询实体列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param sort
	 * @return
	 */
	public <T> List<T> findList(Class<T> entityClass, Sort sort);
	
	/**
	 * 根据属性查询实体列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object propertyValue);
	
	/**
	 * 根据属性查询实体列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param sort
	 * @return
	 */
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object propertyValue, Sort sort);
	
	/**
	 * 查询分页对象，对过本地线程从请求中读取条件及排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> Page<T> findPage(Class<T> entityClass);
	
	/**
	 * 查询分页对象，指定条件过滤器
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param filters
	 * @return
	 */
	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters);
	
	/**
	 * 查询分页对象，指定条件过滤器及排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param filters
	 * @param sort
	 * @return
	 */
	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters, Sort sort);
	
	/**
	 * 查询树节点列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param treeable
	 * @return
	 */
	public <T extends TreeEntity> List<Node> findTree(Class<T> entityClass, Treeable treeable);
	
	/**
	 * 查询树节点列表
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param filters
	 * @param treeable
	 * @return
	 */
	public <T extends TreeEntity> List<Node> findTree(Class<T> entityClass, List<SearchFilter> filters, Treeable treeable);
	
}
