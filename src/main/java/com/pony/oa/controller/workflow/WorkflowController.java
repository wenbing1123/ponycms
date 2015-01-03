package com.pony.oa.controller.workflow;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pony.core.Result;
import com.pony.core.pageable.Page;
import com.pony.oa.entity.Workflow;
import com.pony.oa.service.IWorkflowService;

@Controller
@RequestMapping("/workflowController")
public class WorkflowController {
	
	@Autowired IWorkflowService workflowService;

	@RequestMapping(params="gridData")
	public @ResponseBody Page<Workflow> gridData(String name) {
		return workflowService.findPage(name);
	}
	
	@RequestMapping(params="saveOrUpdate")
	public @ResponseBody Result saveOrUpdate(String name, String desc, MultipartFile xml, MultipartFile img) throws Exception{
		if(img != null){
			workflowService.saveOrUpdate(name, desc, xml.getName(), xml.getBytes(),img.getName(),img.getBytes());
		}else{
			workflowService.saveOrUpdate(name, desc, xml.getName(), xml.getBytes());
		}
		return Result.success();
	}
	
	@RequestMapping(params="getXml")
	public void getXml(Long id,HttpServletResponse response){
		Workflow workflow = workflowService.get(id);
		byte[] xml = workflow.getXml();
		response.setContentType("text/html; charset=utf-8");
		
        PrintWriter writer = null;
        try {
        	writer = response.getWriter();
        	writer.write(new String(xml));
			response.flushBuffer();
		} catch (IOException e) {
			
		} finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	@RequestMapping(params="getImg")
	public void getImg(Long id,HttpServletResponse response){
		Workflow workflow = workflowService.get(id);
		byte[] img = workflow.getImg();
		response.setContentType("image/png; charset=utf-8");   
        
        BufferedOutputStream bos = null;
        try {
			bos = new BufferedOutputStream(response.getOutputStream());
			bos.write(img);
			response.flushBuffer();
		} catch (IOException e) {
			
		} finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long id) throws Exception{
		workflowService.remove(id);
		return Result.success();
	}
}
