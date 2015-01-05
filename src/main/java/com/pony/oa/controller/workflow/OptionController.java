package com.pony.oa.controller.workflow;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Field;
import com.pony.oa.entity.Option;
import com.pony.oa.service.IWorkflowService;

@Controller
@RequestMapping("/optionController")
public class OptionController {

	@Autowired ICrudService crudService;
	@Autowired IWorkflowService workflowService;
	
	@RequestMapping(params="listData")
	public @ResponseBody List<Option> listData(Long fieldId){
		return crudService.findList(Option.class, "field.id", fieldId);
	}
	
	@RequestMapping(params="saveOrUpdate")
	public @ResponseBody Result saveOrUpdate(@ModelAttribute List<Option> options) throws Exception{
		workflowService.saveOrUpdateOptionList(options);
		return Result.success();
	}
	
	//把页面的请求参数变成DOMAIN的属性
	@InitBinder    
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Field.class,"field",new PropertiesEditor(){
				public void setAsText(String text) throws IllegalArgumentException {
					String input = (text != null ? text.trim() : null);
					if(StringUtils.isBlank(input)){
						setValue(null);
					}else{
						Field field = crudService.find(Field.class, Long.valueOf(input));
						setValue(field);
					}
				}
			});
	}
	
}
