package com.pony.core.hibernate4.jpa;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.pony.core.pageable.Page;
import com.pony.core.pageable.PageImpl;
import com.pony.core.pageable.Pageable;
import com.pony.core.pageable.Sort;
import com.pony.core.pageable.Sort.Order;

@Transactional
public abstract class HibernateJpaDaoSupport {

	protected Logger logger = Logger.getLogger(getClass());
	
	@PersistenceContext protected EntityManager em;
	
	//=====================DAO相关公共接口==========================
	public <T> void save(T entity){
		em.persist(entity);
	}
	
	public <T> void update(T entity){
		em.merge(entity);
	}
	
	public <T> void remove(T entity){
		em.remove(entity);
	}
	
	public <T,PK extends Serializable> void remove(Class<T> entityClass,PK id){
		em.remove(em.getReference(entityClass, id));
	}
	
	@Transactional(readOnly=true)
	public <T,PK extends Serializable> T find(Class<T> entityClass,PK id){
		return em.find(entityClass, id);
	}
	
	public int update(String hql, Object... values){
		Query query = createQuery(hql, values);
		return query.executeUpdate();
	}
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> T findOne(String hql, Object... values){
		Query query = createQuery(hql, values);
		try{
			return (T) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public <T> T findOne(Class<T> entityClass, String fieldName, Object fieldValue){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		
		Root<T> root = query.from(entityClass);
		String[] names = StringUtils.split(fieldName,".");
		Path<?> path = root.get(names[0]);
		for(int i=1;i<names.length;i++){
			path = path.get(names[i]);
		}
		Predicate predicate = builder.equal(path, fieldValue);
		
		try{
			return em.createQuery(query.where(builder.and(predicate))).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(String hql, Object... values){
		Query query = createQuery(hql, values);
		return query.getResultList();
	}
	
	@Transactional(readOnly=true)
	public <T> List<T> findList(Class<T> entityClass, final String fieldName, final Object fieldValue){
		
		Specification<T> specification = new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				String[] names = StringUtils.split(fieldName,".");
				Path<?> path = root.get(names[0]);
				for(int i=1;i<names.length;i++){
					path = path.get(names[i]);
				}
				Predicate predicate = builder.equal(path, fieldValue);
				return builder.and(predicate);
			}
		};
		
		return findList(entityClass, specification);
	}
	
	@Transactional(readOnly=true)
	public <T> List<T> findList(Class<T> entityClass, final String fieldName, final Object fieldValue, Sort sort){
		
		Specification<T> specification = new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				String[] names = StringUtils.split(fieldName,".");
				Path<?> path = root.get(names[0]);
				for(int i=1;i<names.length;i++){
					path = path.get(names[i]);
				}
				Predicate predicate = builder.equal(path, fieldValue);
				return builder.and(predicate);
			}
		};
		
		return findList(entityClass, specification, sort);
	}
	
	@Transactional(readOnly=true)
	public <T> List<T> findList(Class<T> entityClass, Specification<T> specification) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		Predicate predicate = specification.toPredicate(root, query, builder);
		
		return em.createQuery(query.where(predicate)).getResultList();
	}
	
	@Transactional(readOnly=true)
	public <T> List<T> findList(Class<T> entityClass, Specification<T> specification, Sort sort) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		Predicate predicate = specification.toPredicate(root, query, builder);
		
		if(null != sort){
			Iterator<Order> iter = sort.iterator();
			while(iter.hasNext()){
				Order order = iter.next();
				switch (order.getDirection()) {
				case DESC:
					query.orderBy(builder.desc(root.get(order.getProperty())));
					break;
				default:
					query.orderBy(builder.asc(root.get(order.getProperty())));
					break;
				}
			}
		}
		
		return em.createQuery(query.where(predicate)).getResultList();
	}
	
	@Transactional(readOnly=true)
	public <T> Page<T> findPage(Class<T> entityClass, Pageable pageable) {
		return findPage(entityClass, null , null, pageable);
	}
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPage(Class<T> entityClass, String hql, Object[] values, Pageable pageable) {
		if(null == hql || "".equals(hql)){
			hql = "from "+entityClass.getSimpleName();
		}
		
		long total = buildCountByHql(hql);
		
		Query query = createQuery(hql);
		setPageParameter(query, pageable.getOffset(), pageable.getPageSize());
		
		return new PageImpl<T>(query.getResultList(), pageable, total);
	}
	
	@Transactional(readOnly=true)
	public <T> Page<T> findPage(Class<T> entityClass, Specification<T> specification, Pageable pageable){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Long> countCq = builder.createQuery(Long.class);
		Root<T> root = countCq.from(entityClass);
		countCq.select(builder.count(root.get("id")));
		Predicate predicate = specification.toPredicate(root, countCq, builder);
		Long totalCount = em.createQuery(countCq.where(predicate)).getSingleResult();
		long total = totalCount.longValue();
		
		CriteriaQuery<T> entityCq = builder.createQuery(entityClass);
		root = entityCq.from(entityClass);
		predicate = specification.toPredicate(root, entityCq, builder);
		
		Sort sort = pageable.getSort();
		if(null != sort){
			Iterator<Order> iter = sort.iterator();
			while(iter.hasNext()){
				Order order = iter.next();
				switch (order.getDirection()) {
				case DESC:
					entityCq.orderBy(builder.desc(root.get(order.getProperty())));
					break;
				default:
					entityCq.orderBy(builder.asc(root.get(order.getProperty())));
					break;
				}
			}
		}
		
		TypedQuery<T> query = em.createQuery(entityCq.where(predicate));
		setPageParameter(query, pageable.getOffset(), pageable.getPageSize());
		
		return new PageImpl<T>(query.getResultList(), pageable, total);
	}
	
	@Transactional(readOnly=true)
	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters, Pageable pageable) {
		
		Specification<T> specification =DynamicSpecifications.bySearchFilter(filters, entityClass);
		
		return findPage(entityClass, specification, pageable);
	}
	
	//=====================HQL相关保护方法及私有方法==========================
	protected Query createQuery(String hql, Object... values){
		Query query = em.createQuery(hql);
		if(null != values){
			for (int i=0; i<values.length; i++) {
				query.setParameter(i+1, values[i]);
			}
		}
		return query;
	}
	
	private void setPageParameter(Query q, int start, int limit){
		if(-1 != start && -1 !=limit){
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}
	}
	
	private int buildCountByHql(String hql, Object... values){
		String countHql = buildCountHql(hql);
		Long count = findOne(countHql,values);
		return count.intValue();
	}
	
	private String buildCountHql(String orginalHql){
		String tmpHql = orginalHql;
		tmpHql = "from " + StringUtils.substringAfter(orginalHql, "from");
		tmpHql = StringUtils.substringBefore(tmpHql, "order by");
		
		String countHql = "select count(*) " + tmpHql;
		return countHql;
	}
	
	//=====================基础操作相关保护方法及私有方法==========================
	protected <T> void refresh(T entity){
		em.refresh(entity);
	}
	
	protected void flush() {
		em.flush();
	}
	
	protected void clear() {
		em.clear();
	}

}
