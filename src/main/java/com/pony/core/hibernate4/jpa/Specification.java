package com.pony.core.hibernate4.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

//查询条件生成
public interface Specification<T> {

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

}
