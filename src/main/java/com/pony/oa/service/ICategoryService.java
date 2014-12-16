package com.pony.oa.service;

import java.util.List;

import com.pony.core.ValueLabel;
import com.pony.core.treeable.Node;

public interface ICategoryService {
	
	public List<ValueLabel> getListByType(String type);
	public List<Node> getTreeByType(String type);
}
