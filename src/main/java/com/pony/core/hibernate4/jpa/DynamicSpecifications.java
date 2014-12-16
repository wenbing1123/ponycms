package com.pony.core.hibernate4.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;
import com.pony.core.util.CollectionUtils;

public class DynamicSpecifications {

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters,Class<T> entityClass){
		return new Specification<T>() {

			@SuppressWarnings("unchecked")
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder builder) {
				
				if(CollectionUtils.isNotEmpty(filters)){
					List<Predicate> predicates = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						
						String[] names = filter.getFieldName().split("\\.");
						Path expression = root.get(names[0]);
						for (int i=1; i<names.length; i++) {
							expression = expression.get(names[i]);
						}
						
						switch (filter.getOperator()) {
						case EQ:
							predicates.add(builder.equal(expression, filter.getFieldValue()));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable)filter.getFieldValue()));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable)filter.getFieldValue()));
							break;
						case GE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable)filter.getFieldValue()));
							break;
						case LE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable)filter.getFieldValue()));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%"+filter.getFieldValue()+"%"));
							break;
						}
					}
					
					if(predicates.size()>0){
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				
				return builder.conjunction();
			}
			
		};
	}
}
