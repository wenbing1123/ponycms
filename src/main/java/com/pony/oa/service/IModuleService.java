package com.pony.oa.service;

import java.util.List;
import java.util.Set;

import com.pony.core.treeable.Node;

public interface IModuleService {

	public List<Node> loadAll();
	public List<Node> loadAll(Set<String>ids);
	public List<Node> loadAll(Set<String>ids, boolean hasCheck);
	
}
