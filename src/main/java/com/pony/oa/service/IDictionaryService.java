package com.pony.oa.service;

import java.util.List;

import com.pony.oa.entity.Dictionary;

public interface IDictionaryService {

	public void save(List<Dictionary> dictionarys);
	public void update(List<Dictionary> dictionarys);
	public void remove(List<Dictionary> dictionarys);
	
	public List<Dictionary> findList(String category);
	
}
