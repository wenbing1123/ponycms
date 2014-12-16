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
@RequestMapping("/category")
public class CatgoryController {

	@Autowired ICategoryService categoryService;
	
	@RequestMapping("/treedata.do")
	public @ResponseBody List<Node> treedata(String type){
		return categoryService.getTreeByType(type);
	}
	
	@RequestMapping("/listdata.do")
	public @ResponseBody List<ValueLabel> listdata(String type){
		return categoryService.getListByType(type);
	}
	
}
