package com.pony.oa.controller.dic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.json.JSON;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Dictionary;
import com.pony.oa.service.IDictionaryService;

@Controller
@RequestMapping("/dicController")
public class DicController {

	@Autowired ICrudService crudService;
	@Autowired IDictionaryService dictionaryService;
	
	@RequestMapping(params="gridData")
	public @ResponseBody List<Dictionary> gridData(String category) {
		return dictionaryService.findList(category);
	}
	
	@RequestMapping(params="save")
	public @ResponseBody Result save(HttpServletRequest request) throws Exception{
		String line = request.getReader().readLine();
		if(!line.startsWith("[")){
			line = "[" + line + "]";
		}
		System.out.println(line);
		List<Dictionary> dictionarys = JSON.get().readAsList(line, Dictionary.class);
		dictionaryService.save(dictionarys);
		return Result.success();
	}
	
	@RequestMapping(params="update")
	public @ResponseBody Result update(HttpServletRequest request) throws Exception{
		String line = request.getReader().readLine();
		if(!line.startsWith("[")){
			line = "[" + line + "]";
		}
		System.out.println(line);
		List<Dictionary> dictionarys = JSON.get().readAsList(line, Dictionary.class);
		dictionaryService.update(dictionarys);
		return Result.success();
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(HttpServletRequest request) throws Exception{
		String line = request.getReader().readLine();
		if(!line.startsWith("[")){
			line = "[" + line + "]";
		}
		System.out.println(line);
		List<Dictionary> dictionarys = JSON.get().readAsList(line, Dictionary.class);
		dictionaryService.remove(dictionarys);
		return Result.success();
	}
	
}
