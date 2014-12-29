package com.pony.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.core.dao.IDao;
import com.pony.core.pageable.Sort;
import com.pony.oa.entity.Dictionary;
import com.pony.oa.service.IDictionaryService;

@Service("dictionaryService")
@Transactional
public class DictionaryServiceImpl implements IDictionaryService {

	@Resource private IDao dao;
	
	//org.hibernate.PersistentObjectException: detached entity passed to persist 异常表示id已存在值
	public void save(List<Dictionary> dictionarys) {
		for (Dictionary dictionary : dictionarys) {
			dao.save(dictionary);
		}
	}

	public void update(List<Dictionary> dictionarys) {
		for (Dictionary dictionary : dictionarys) {
			Dictionary entity = dao.find(Dictionary.class, dictionary.getId());
			entity.setName(dictionary.getName());
			entity.setOrder(dictionary.getOrder());
			entity.setDescription(dictionary.getDescription());
			dao.update(dictionary);
		}
	}
	
	public void remove(List<Dictionary> dictionarys){
		for (Dictionary dictionary : dictionarys) {
			dao.remove(Dictionary.class, dictionary.getId());
		}
	}

	public List<Dictionary> findList(String category) {
		return dao.findList(Dictionary.class, "category", category, new Sort("order"));
	}

}
