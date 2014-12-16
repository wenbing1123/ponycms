package com.pony.oa.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Log;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired ICrudService crudService;

	@RequestMapping("/datagrid.do")
	public @ResponseBody Page<Log> datagrid() {
		return crudService.findPage(Log.class);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Log.class, ids);
		return Result.success();
	}
}
