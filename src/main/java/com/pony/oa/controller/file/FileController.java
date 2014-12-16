package com.pony.oa.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Dictionary;
import com.pony.oa.entity.Fileinfo;
import com.pony.oa.service.IFileinfoService;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired ICrudService crudService;
	@Autowired IFileinfoService fileinfoService;
	
	@RequestMapping("/datagrid.do")
	public @ResponseBody Page<Fileinfo> datagrid() {
		return crudService.findPage(Fileinfo.class);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Dictionary.class, ids);
		return Result.success();
	}
}
