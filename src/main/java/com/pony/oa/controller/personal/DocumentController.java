package com.pony.oa.controller.personal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.SearchFilter.Operator;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Document;
import com.pony.oa.entity.Workflow;
import com.pony.oa.service.IDocumentService;
import com.pony.oa.service.IWorkflowService;
import com.pony.oa.util.SecurityUtils;

@Controller
@RequestMapping("/documentController")
public class DocumentController {
	
	@Autowired ICrudService crudService;
	@Autowired IWorkflowService workflowService;
	@Autowired IDocumentService documentService;
	
	/**
	 * 我的公文列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params="gridData")
	public @ResponseBody Page<Document> gridData(HttpServletRequest request) {
		List<SearchFilter> filters = SearchFilter.parse(request);
		filters.add(new SearchFilter("creator.id",Operator.EQ,SecurityUtils.getUserid()));
		return crudService.findPage(Document.class,filters);
	}
	
	/**
	 * 工作流列表
	 * 
	 * @return
	 */
	@RequestMapping(params="getWorkflowList")
	public @ResponseBody List<Workflow> getWorkflowList(){
		return workflowService.findAll();
	}
	
	/**
	 * 保存公文输入页面
	 * 
	 * @param workflowId
	 * @return
	 */
	@RequestMapping(params="saveInput")
	public String saveInput(Long workflowId){
		
		return null;
	}
	
	/**
	 * 保存公文操作
	 * 
	 * @param document
	 * @return
	 */
	@RequestMapping(params="save")
	public @ResponseBody Result save(Document document,Long workflowId,Map<String,Object>prop){
		documentService.save(document, workflowId, SecurityUtils.getUserid(), prop);
		return Result.success();
	}
	
	/**
	 * 保存并提交公文操作
	 * 
	 * @param document
	 * @return
	 */
	@RequestMapping(params="saveAndSubmit")
	public @ResponseBody Result saveAndSubmit(Document document){
		
		return Result.success();
	}
	
	/**
	 * 提交公文操作
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params="submit")
	public @ResponseBody Result submit(Long id){
		
		return Result.success();
	}
	
	/**
	 * 下载附件操作
	 * 
	 * @param id
	 */
	@RequestMapping(params="download")
	public void download(Long id){
		
	}
	
	/**
	 * 更新公文操作
	 * 
	 * @param document
	 * @return
	 */
	@RequestMapping(params="update")
	public @ResponseBody Result update(Document document){
		return Result.success();
	}
	
	/**
	 * 删除公文操作
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long id) throws Exception{
		workflowService.remove(id);
		return Result.success();
	}
	
}
