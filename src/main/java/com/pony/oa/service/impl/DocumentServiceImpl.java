package com.pony.oa.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pony.core.dao.IDao;
import com.pony.core.facade.ActivitiFacade;
import com.pony.core.pageable.Page;
import com.pony.oa.entity.ApproveInfo;
import com.pony.oa.entity.Document;
import com.pony.oa.service.IDocumentService;

@Service("documentService")
@Transactional
public class DocumentServiceImpl implements IDocumentService {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired IDao dao;
	@Autowired ActivitiFacade activitiFacade;
	
	public void save(Document document, long workflowId, long userId,Map<String, Object> properties) {
		
	}

	public void remove(long id) {
		
	}

	public Document find(long id) {
		return null;
	}

	public List<ApproveInfo> searchApproveInfos(long documentId) {
		return null;
	}

	public Page<Document> searchApprovedDocuments(long userId) {
		return null;
	}

	public Page<Document> searchApprovingDocuments(long userId) {
		return null;
	}

	public Page<Document> searchMyDocuments(long userId) {
		return null;
	}

	public void submitToWorkflow(long userId, long documentId, String transitionName) {
		
	}

	public List<String> searchNextSteps(long userId, long documentId) {
		return null;
	}

}
