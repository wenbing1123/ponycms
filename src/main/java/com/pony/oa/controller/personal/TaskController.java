package com.pony.oa.controller.personal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.ApproveInfo;
import com.pony.oa.entity.Document;
import com.pony.oa.service.IDocumentService;

@Controller
@RequestMapping("/taskController")
public class TaskController {
	
	@Autowired ICrudService crudService;
	@Autowired IDocumentService documentService;
	
	/**
	 * 待办任务列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/gridData",params="handling")
	public @ResponseBody Page<Document> handlingGridData(HttpServletRequest request) {
		List<SearchFilter> filters = SearchFilter.parse(request);
		return crudService.findPage(Document.class,filters);
	}
	
	/**
	 * 已办任务列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/gridData",params="handled")
	public @ResponseBody Page<Document> handledGridData(HttpServletRequest request) {
		List<SearchFilter> filters = SearchFilter.parse(request);
		return crudService.findPage(Document.class,filters);
	}
	
	/**
	 * 获取公文当前执行完成后下一步可能的操作列表
	 * 
	 * @param documentId
	 * @return
	 */
	@RequestMapping(params="nextSteps")
	public @ResponseBody List<?> nextSteps(Long documentId){
		
		return null;
	}
	/**
	 * 任务办理操作
	 * @return
	 */
	@RequestMapping(params="handle")
	public @ResponseBody Result handle(){
		return Result.success();
	}
	
	/**
	 * 办理历史列表
	 * 
	 * @param request
	 * @param documentId
	 * @return
	 */
	@RequestMapping(params="handleHistory")
	public @ResponseBody Page<ApproveInfo> handleHistory(HttpServletRequest request, Long documentId){
		List<SearchFilter> filters = SearchFilter.parse(request);
		return crudService.findPage(ApproveInfo.class,filters);
	}
	
}
