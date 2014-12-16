package com.pony.oa.controller.dic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Dictionary;

@Controller
@RequestMapping("/dic")
public class DicController {

	@Autowired ICrudService crudService;
	
	@RequestMapping("/datagrid.do")
	public @ResponseBody Page<Dictionary> datagrid() {
		return crudService.findPage(Dictionary.class);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Dictionary.class, ids);
		return Result.success();
	}
	
}
