package com.pony.oa.controller.workflow;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Field;
import com.pony.oa.entity.Form;

@Controller
@RequestMapping("/fieldController")
public class FieldController {

	@Autowired ICrudService crudService;
	
	@RequestMapping(params="listData")
	public @ResponseBody List<Field> listData(Long formId){
		return crudService.findList(Field.class, "form.id", formId);
	}
	
	@RequestMapping(params="saveOrUpdate")
	public @ResponseBody Result saveOrUpdate(Field field){
		Long id = field.getId();
		if(id == null){
			Field entity = crudService.find(Field.class, id);
			entity.setName(field.getName());
			entity.setLabel(field.getLabel());
			entity.setType(field.getType());
			entity.setInput(field.getInput());
			entity.setForm(field.getForm());  //所属表单,@initBinder
			crudService.update(entity);
		}else{
			crudService.save(field);
		}
		return Result.success();
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Field.class, ids);
		return Result.success();
	}
	
	//把页面的请求参数变成DOMAIN的属性
	@InitBinder    
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Form.class,"form",new PropertiesEditor(){
			public void setAsText(String text) throws IllegalArgumentException {
				String input = (text != null ? text.trim() : null);
				if(StringUtils.isBlank(input)){
					setValue(null);
				}else{
					Form form = crudService.find(Form.class, Long.valueOf(input));
					setValue(form);
				}
			}
		});
	}
	
}
