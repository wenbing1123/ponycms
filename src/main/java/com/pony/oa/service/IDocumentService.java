package com.pony.oa.service;

import java.util.List;
import java.util.Map;

import com.pony.core.pageable.Page;
import com.pony.oa.entity.ApproveInfo;
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
	public void save(Document document, long workflowId, long userId,Map<String, Object> properties) ;
	
	/**
	 * 删除公文
	 * 
	 * @param id
	 */
	public void remove(long id);
	
	/**
	 * 查找公文
	 * 
	 * @param id
	 * @return
	 */
	public Document find(long id);
	
	/**
	 * 查找公文审批历史
	 * 
	 * @param documentId
	 * @return
	 */
	public List<ApproveInfo> searchApproveInfos(long documentId);
	
	/**
	 * 查找用户已审公文
	 * 
	 * @param userId
	 * @return
	 */
	public Page<Document> searchApprovedDocuments(long userId);
	
	/**
	 * 查找用户待审公文
	 * 
	 * @param userId
	 * @return
	 */
	public Page<Document> searchApprovingDocuments(long userId);
	
	/**
	 * 查找我的公文
	 * 
	 * @param userId
	 * @return
	 */
	public Page<Document> searchMyDocuments(long userId);
	
	//流程操作
	/**
	 * 把公文提交到流程
	 * 
	 * @param userId
	 * @param documentId
	 * @param transitionName
	 */
	public void submitToWorkflow(long userId, long documentId, String transitionName);
	
	/**
	 * 查询下一步列表
	 * 
	 * @param userId
	 * @param documentId
	 * @return
	 */
	public List<String> searchNextSteps(long userId, long documentId);
	
}
