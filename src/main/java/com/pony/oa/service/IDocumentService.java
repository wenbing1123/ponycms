package com.pony.oa.service;

import java.util.Map;

import com.pony.oa.entity.Document;

public interface IDocumentService {

	//基本操作
	/**
	 * 创建公文
	 * 
	 * @param document  公文基本属性
	 * @param workflowId 工作流Id
	 * @param userId //创建用户
	 * @param props //公文动态属性
	 */
	public void save(Document document, int workflowId, int userId,Map<String, Object> properties) ;
	
	//流程操作
	/**
	 * 把公文提交到流程
	 * 
	 * @param userId
	 * @param documentId
	 * @param transitionName
	 */
	public void submitToWorkflow(Long userId, Long documentId, String transitionName);
	
}
