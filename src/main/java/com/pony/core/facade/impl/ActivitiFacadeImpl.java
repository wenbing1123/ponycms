package com.pony.core.facade.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import com.pony.core.facade.ActivitiFacade;

@Service("activitiFacade")
@Transactional
public class ActivitiFacadeImpl implements ActivitiFacade{

	@Resource private RepositoryService repositoryService;
	@Resource private RuntimeService runtimeService;
	@Resource private TaskService taskService;
	@Resource private IdentityService identityService;
	@Resource private HistoryService historyService;
	@Resource private ManagementService managementService;
	
	public void deployProcessDefinition(String name, String xmlName, byte[] xmlData) {
		
		//部署流程定义
		repositoryService
			.createDeployment()
			.name(name)
			.addInputStream(xmlName, new ByteArrayInputStream(xmlData))
			.deploy();
		
	}
	
	public void deployProcessDefinition(String name, String xmlName, byte[] xmlData, String imgName, byte[] imgData){
		
		//部署流程定义
		repositoryService
			.createDeployment()
			.name(name)
			.addInputStream(xmlName, new ByteArrayInputStream(xmlData))
			.addInputStream(imgName, new ByteArrayInputStream(imgData))
			.deploy();
				
	}
	
	public void deployProcessDefinition(String classpathXml){
		
		//部署流程定义
		repositoryService
			.createDeployment()
			.addClasspathResource(classpathXml)
			.deploy();
		
	}
	
	public void deployProcessDefinition(String classpathXml, String classpathImg){

		//部署流程定义
		repositoryService
			.createDeployment()
			.addClasspathResource(classpathXml)
			.addClasspathResource(classpathImg)
			.deploy();
		
	}

	public void deployProcessDefinition(String name, ZipInputStream zipDefinition) {

		//部署流程定义
		repositoryService
			.createDeployment()
			.name(name)
			.addZipInputStream(zipDefinition)
			.deploy();
		
	}
	
	public void undeployProcessDefinition(String name){
		
		//获取流程部署
		List<Deployment> deployments = 
			repositoryService
				.createDeploymentQuery()
				.deploymentName(name)
				.list();
		
		//删除流程部署
		if(deployments != null && deployments.size()>0){
			for (Deployment deployment : deployments) {
				repositoryService.deleteDeployment(deployment.getId());
			}
		}
		
	}

	public void undeployProcessDefinition(String name, boolean cascade) {
		
		//获取流程部署
		List<Deployment> deployments = 
			repositoryService
				.createDeploymentQuery()
				.deploymentName(name)
				.list();
		
		//删除流程部署
		if(deployments != null && deployments.size()>0){
			for (Deployment deployment : deployments) {
				repositoryService.deleteDeployment(deployment.getId(), cascade);
			}
		}
		
	}

	//=================>
	
	public List<ProcessDefinition> queryProcessDefinitionList() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().desc();
		return query.list();
	}
	
	public long queryProcessDefinitionCount() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().desc();
		return query.count();
	}
	
	public List<ProcessDefinition> queryProcessDefinitionListPage(int firstIndex, int maxResult) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().desc();
		return query.listPage(firstIndex, maxResult);
	}

	public InputStream queryProcessDefinitionImage(String deploymentId) {
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
		   if(name.indexOf(".png")>=0){
			   imageName = name;
		   }
		}
		if(imageName!=null) {
			return repositoryService.getResourceAsStream(deploymentId, imageName);
		}
		return null;
	}

	public String startProcessInstance(String processDefinitionKey, String userId, Map<String, Object> variables) {
		identityService.setAuthenticatedUserId(userId); //指定启动用户
		
		ProcessInstance processInstance = 
			runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
		
		return processInstance.getProcessInstanceId();
	}
	
	public String startProcessInstance(String processDefinitionKey, String userId, String bussinessKey, Map<String, Object> variables) {
		identityService.setAuthenticatedUserId(userId); //指定启动用户
		
		ProcessInstance processInstance = 
			runtimeService.startProcessInstanceByKey(processDefinitionKey, bussinessKey, variables);
		
		return processInstance.getProcessInstanceId();
	}
	
	public InputStream getProcessInstanceDiagram(String processInstanceId){
		//查询流程实例
		ProcessInstance pi = 
			runtimeService
			.createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		BpmnModel bpmnModel = 
			repositoryService
			.getBpmnModel(pi.getProcessDefinitionId());
		//得到正在执行的环节
		List<String> activeIds = 
			runtimeService.getActiveActivityIds(pi.getId());
		
		InputStream is = 
			new DefaultProcessDiagramGenerator()
			.generateDiagram(
					bpmnModel,
					"png",
					activeIds,
					Collections.<String>emptyList(),
					null,null,null,1.0);
		
		return is;
	}

	public List<ProcessInstance> queryProcessInstanceList(String processDefinitionKey) {
		return runtimeService
				.createProcessInstanceQuery()
				.list();
	}
	
	public long queryProcessInstanceCount(String processDefinitionKey) {
		return runtimeService
			.createProcessInstanceQuery()
			.count();
	}

	public List<ProcessInstance> queryProcessInstanceListPage(String processDefinitionKey, int firstIndex, int maxResult) {
		return runtimeService
			.createProcessInstanceQuery()
			.listPage(firstIndex, maxResult);
	}

	public void claimTask(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	public void completeTask(String taskId) {
		taskService.complete(taskId);
	}

	public void delegateTask(String taskId, String userId) {
		taskService.delegateTask(taskId, userId);
	}
	
	public void resolveTask(String taskId) {
		taskService.resolveTask(taskId);
	}

	public List<Task> queryAssigneeTaskList(String userId) {
		return taskService
			.createTaskQuery()
			.taskAssignee(userId)
			.list();
	}

	public List<Task> queryCandidateTaskList(String userId) {
		return taskService
			.createTaskQuery()
			.taskCandidateUser(userId)
			.list();
	}

	public List<Task> queryDelegateTaskList(String userId) {
		return taskService
			.createTaskQuery()
			.taskAssignee(userId)
			.taskDelegationState(DelegationState.PENDING)
			.list();
	}
	
	public List<Task> queryResolveTaskList(String userId) {
		return taskService
			.createTaskQuery()
			.taskAssignee(userId)
			.taskDelegationState(DelegationState.RESOLVED)
			.list();
	}

	public List<String> searchNextTransitions(String instanceId, String userId) {
		return null;
	}

	public String nextStep(String instanceId, String userId,String transitionName) {
		return null;
	}

	public Object[] backStep(String instanceId, String userId) {
		return null;
	}

}
