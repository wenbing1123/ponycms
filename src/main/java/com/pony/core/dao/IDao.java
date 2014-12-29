package com.pony.core.dao;

import java.io.Serializable;
import java.util.List;

import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.Specification;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.Pageable;
import com.pony.core.pageable.Sort;

public interface IDao {

	public <T> void save(T entity);
	public <T> void update(T entity);
	public <T> void remove(T entity);
	public <T,PK extends Serializable> void remove(Class<T> entityClass, PK id);
	public <T,PK extends Serializable> T find(Class<T> entityClass, PK id);
	
	public int update(String hql, Object... values);
	
	public <T> T findOne(String hql, Object... values);
	public <T> T findOne(Class<T> entityClass, String fieldName, Object fieldValue);
	
	public <T> List<T> findList(String hql, Object... values);
	public <T> List<T> findList(Class<T> entityClass, String fieldName, Object fieldValue);
	public <T> List<T> findList(Class<T> entityClass, String fieldName, Object fieldValue, Sort sort);
	public <T> List<T> findList(Class<T> entityClass, Specification<T> specification);
	public <T> List<T> findList(Class<T> entityClass, Specification<T> specification, Sort sort);
	
	public <T> Page<T> findPage(Class<T> entityClass, Pageable pageable);
	public <T> Page<T> findPage(Class<T> entityClass, String hql, Object[] values, Pageable pageable);
	public <T> Page<T> findPage(Class<T> entityClass, Specification<T> specification, Pageable pageable);
	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters, Pageable pageable);
	
}
