package com.pony.oa.controller.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.ValueLabel;
import com.pony.oa.entity.Form;
import com.pony.oa.service.IWorkflowService;

@Controller
@RequestMapping("/formController")
public class FormController {

	@Autowired IWorkflowService workflowService;
	
	@RequestMapping(params="getForm")
	public @ResponseBody Form getForm(Long workflowId){
		return workflowService.getForm(workflowId);
	}
	
	@RequestMapping(params="setForm")
	public @ResponseBody Result setForm(Long workflowId, Form form){
		workflowService.setForm(workflowId, form);
		return Result.success();
	}
	
	@RequestMapping(params="getAllFormTpl")
	public @ResponseBody List<ValueLabel> getAllFormTpl(){
		return workflowService.getAllFormTpl();
	}
	
	@RequestMapping(params="getAllFieldType")
	public @ResponseBody List<ValueLabel> getAllFieldType(){
		return workflowService.getAllFieldType();
	}
	
	@RequestMapping(params="getAllFieldInput")
	public @ResponseBody List<ValueLabel> getAllFieldInput(){
		return workflowService.getAllFieldInput();
	}
	
}
