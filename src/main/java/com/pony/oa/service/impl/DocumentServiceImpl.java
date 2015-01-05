package com.pony.oa.service.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pony.oa.entity.Document;
import com.pony.oa.service.IDocumentService;

@Service("documentService")
@Transactional
public class DocumentServiceImpl implements IDocumentService {

	public void save(Document document, int workflowId, int userId,Map<String, Object> properties) {
		
	}

	public void submitToWorkflow(Long userId, Long documentId,String transitionName) {
		
	}

}
