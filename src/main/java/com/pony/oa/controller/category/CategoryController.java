package com.pony.oa.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.ValueLabel;
import com.pony.core.treeable.Node;
import com.pony.oa.service.ICategoryService;

@Controller
@RequestMapping("/categoryController")
public class CategoryController {

	@Autowired ICategoryService categoryService;
	
	@RequestMapping(params="treeData")
	public @ResponseBody List<Node> treeData(String type){
		return categoryService.getTreeByType(type);
	}
	
	@RequestMapping(params="listData")
	public @ResponseBody List<ValueLabel> listData(String type){
		return categoryService.getListByType(type);
	}
	
}
