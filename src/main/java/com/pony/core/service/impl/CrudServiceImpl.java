package com.pony.core.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pony.core.RequestContext;
import com.pony.core.dao.IDao;
import com.pony.core.entity.BaseEntity;
import com.pony.core.entity.TreeEntity;
import com.pony.core.hibernate4.Updater;
import com.pony.core.hibernate4.jpa.DynamicSpecifications;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.Specification;
import com.pony.core.pageable.Page;
import com.pony.core.pageable.PageRequest;
import com.pony.core.pageable.Pageable;
import com.pony.core.pageable.Sort;
import com.pony.core.service.ICrudService;
import com.pony.core.treeable.Node;
import com.pony.core.treeable.Treeable;
import com.pony.core.util.BeanUtilsEx;
import com.pony.core.util.ReflectionUtils;

@Service("crudService")
@Transactional
public class CrudServiceImpl implements ICrudService {

	@Resource private IDao dao;

	public <T, PK extends Serializable> T find(Class<T> entityClass, PK id) {
		Assert.notNull(id);
		
		return dao.find(entityClass, id);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return dao.findList("from " + entityClass.getSimpleName());
	}

	public <T> Page<T> findPage(Class<T> entityClass) {
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize(), Sort.parse(RequestContext.getRequest()));
		return dao.findPage(entityClass, SearchFilter.parse(RequestContext.getRequest()), pageable);
	}

	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters) {
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize());
		return dao.findPage(entityClass, filters, pageable);
	}
	
	public <T> Page<T> findPage(Class<T> entityClass, List<SearchFilter> filters, Sort sort){
		Pageable pageable = new PageRequest(RequestContext.getPageNumber(), RequestContext.getPageSize(), sort);
		return dao.findPage(entityClass, filters, pageable);
	}

	public <T, PK extends Serializable> void remove(Class<T> entityClass, PK id) {
		Assert.notNull(id);
		
		dao.remove(entityClass, id);
	}

	public <T, PK extends Serializable> void remove(Class<T> entityClass, PK[] ids) {
		Assert.notNull(ids);
		
		for (PK id : ids) {
			dao.remove(entityClass, id);
		}
	}

	public <T> void save(T entity) {
		Assert.notNull(entity);
		dao.save(entity);
	}

	public <T extends BaseEntity> void update(T entity) {
		Assert.notNull(entity);
		Assert.notNull(entity.getId());
		
		dao.update(entity);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> void update(Updater<T> updater) throws Exception{
		T entity = updater.getEntity();
		Assert.notNull(entity);
		Assert.notNull(entity.getId());
		
		T t = (T) dao.find(entity.getClass(), entity.getId());
		
		List<String> properties = null;
		switch (updater.getMode()) {
		case SPECIFY:
			properties = updater.getIncludeProperties();
			if(properties != null && properties.size()>0){
				for (String property : properties) {
					Object value = ReflectionUtils.getFieldValue(entity, property);
					ReflectionUtils.setFieldValue(t, property, value);
				}
			}
			break;
		case MAX:
			properties = updater.getExcludeProperties();
			if(properties != null && properties.size()>0){
				BeanUtilsEx.copyProperties(t, entity, properties.toArray(new String[properties.size()]));
			}else{
				BeanUtilsEx.copyProperties(t, entity);
			}
			break;
		default:
			BeanInfo info = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			properties = updater.getIncludeProperties();
			for (PropertyDescriptor pd : pds) {
				Object value = ReflectionUtils.getFieldValue(entity, pd.getName());
				if(properties != null && properties.contains(pd.getName()) || value != null){
					ReflectionUtils.setFieldValue(t, pd.getName(), value);
				}
			}

			break;
		}
		
		dao.update(t);
	}
	
	public <T extends TreeEntity> List<Node> findTree(Class<T> entityClass, Treeable treeable) {
		return findTree(entityClass, null, treeable);
	}
	
	public <T extends TreeEntity> List<Node> findTree(Class<T> entityClass, List<SearchFilter> filters, Treeable treeable){
		Assert.notNull(treeable);
		Serializable id = treeable.getId();
		List<T> entities = null;
		
		if(filters == null) filters = new ArrayList<SearchFilter>();
		
		if(id != null  && !"".equals(id)){
			T t = dao.find(entityClass, treeable.getId());
			if(t != null){ //存在父节点
				filters.add(new SearchFilter("lft", SearchFilter.Operator.GT, t.getLft())); 
				filters.add(new SearchFilter("rgt", SearchFilter.Operator.LT, t.getRgt())); 
				filters.add(new SearchFilter("layer", SearchFilter.Operator.LE, t.getLayer()+treeable.getLevel()));
				Specification<T> spec = DynamicSpecifications.bySearchFilter(filters, entityClass);
				entities = dao.findList(entityClass, spec, new Sort(Sort.Direction.ASC,"lft"));
			}
		}else{
			filters.add(new SearchFilter("layer", SearchFilter.Operator.LE, treeable.getLevel()));
			Specification<T> spec = DynamicSpecifications.bySearchFilter(filters, entityClass);
			entities = dao.findList(entityClass, spec, new Sort(Sort.Direction.ASC,"lft"));
		}
		
		//节点构建
		List<Node> nodes = new ArrayList<Node>();
		
		if(entities != null && entities.size()>0){
			Map<String, Node> tmpMap = new HashMap<String, Node>();
			int i = 0, min = 0;
			for (T t : entities) {
				String idStr = String.valueOf(t.getId());
				Node node = new Node(idStr, t.getName());
				i++;
				if(i==1){
					min = t.getLayer();
				}
				
				node.setIconCls(t.getIconCls()); //节点图标
				
				if(t.getLft()==t.getRgt()-1){ //叶子节点
					node.setLeaf(true);
					node.setExpanded(false); //不展开
				}else{ //容器节点
					node.setLeaf(false);
					node.setExpanded(false); //不展开
					if(t.getLayer()<min-1+treeable.getLevel()){ //容器节点末级节点不展开，点击节点再加载
						node.setExpanded(true);
					}else{ //容器非末级节点展开
						node.setExpanded(false);
					}
				}
				
				String[] properties = treeable.getDeclaredProperties();
				if(properties != null){
					for (String property : properties) {
						Object value = ReflectionUtils.getFieldValue(t, property);
						if(value != null){
							node.addAttribute(property, value);
						}
					}
				}
				
				String parentIdStr = null;
				if(t.getParentId() != null) {
					parentIdStr = String.valueOf(t.getParentId());
				}
				if(parentIdStr != null && tmpMap.containsKey(parentIdStr)){
					Node pNode = tmpMap.get(parentIdStr);
					pNode.addChildren(node);
				}
				if(treeable.getId() == null){
					if(parentIdStr == null){
						nodes.add(node);
					}
				}else{
					if(parentIdStr != null && treeable.getId().toString().equals(parentIdStr)){
						nodes.add(node);
					}
				}
				tmpMap.put(idStr, node);
			}
		}
		
		return nodes;
	}
}
