package com.pony.core.facade.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.pony.core.SystemException;
import com.pony.core.facade.ActivitiFacade;


/**
 * 
 * Activiti之流程通过、驳回、会签、转办、中止、挂起等核心操作:http://www.tuicool.com/articles/juaUVvm
 * 
 * 串签：一个环节一个环节往下签。
 * 并签：几个环节同步或几个人同时签。
 * 会签：几个人随机签，签好可继续会签，循环往复。
 * @author scott
 *
 */
@Service("activitiFacade")
@Transactional
public class ActivitiFacadeImpl implements ActivitiFacade{
	
	public final static String ACTIVITY_ID_START = "START";
	public final static String ACTIVITY_ID_END = "END";

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
		List<String> transitionNames = new ArrayList<String>();
		//流程定义标识
		String definitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult().getProcessDefinitionId();
		//流程定义实体
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionId);
		//执行实例
		ExecutionEntity execution = (ExecutionEntity)runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		//当前实例的执行到哪个节点 
		String activitiId = execution.getActivityId();
		//获得流程定义的所有节点 
		List<ActivityImpl> activitiList = definition.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			if(activitiId.equals(activityImpl.getId())){
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
				for (PvmTransition pvmTransition : outTransitions) {
					PvmActivity ac = pvmTransition.getDestination(); //获取线路的终点节点  
					transitionNames.add(String.valueOf(ac.getProperty("name")));
				}
				break;
			}
		}
		return transitionNames;
	}

	public String nextStep(String instanceId, String userId,String transitionName) {
		return null;
	}

	public Object[] backStep(String instanceId, String userId) {
		return null;
	}
	
	//任务转办操作
	public void transferAssignee(String taskId, String userId) {  
        taskService.setAssignee(taskId, userId);  
    } 
	
	
	//========================>private operation
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId){
		return findProcessDefinitionEntityById(findTaskEntityById(taskId).getProcessDefinitionId());
	}
	private ExecutionEntity findExecutionEntityByInstanceId(String instanceId){
		ExecutionEntity execution = (ExecutionEntity)runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		if(execution == null){
			throw new SystemException("Execution not Found");
		}
		return execution;
	}
	private TaskEntity findTaskEntityById(String taskId){
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null){
			throw new SystemException("Task not Found");
		}
		return task;
	}
	private ProcessDefinitionEntity findProcessDefinitionEntityById(String definitionId){
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionId);
		if(definition == null){
			throw new SystemException("ProcessDefinition not Found");
		}
		return definition;
	}
	/**
	 * 根据任务ID和节点ID获取活动节点
	 * @param taskId
	 * @param activityId
	 * @return
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId){
		//获取流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
		// 获取当前活动节点ID
		if (StringUtils.isBlank(activityId)) { 
			activityId = findTaskEntityById(taskId).getTaskDefinitionKey();
		}
		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals(ACTIVITY_ID_END)) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
			}
		}
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);
		return activityImpl;
	}
	/**
	 * 提交流程操作，确定下一步流向
	 * 
	 * @param taskId
	 * @param variables
	 * @param activityId
	 */
	private void commitProcess(String taskId, Map<String, Object> variables,  String activityId){
		if (variables == null) {  
            variables = new HashMap<String, Object>();  
        } 
		//活动为空，即没有手工指定流向活动，执行默认流向，有条件根据条件流向，无条件默认第一个流向
		if (StringUtils.isBlank(activityId)) { 
			taskService.complete(taskId, variables);
		}else{
			turnTransition(taskId,activityId, variables);
		}
	}
	/**
	 * 手工指定流程转向操作
	 * 
	 * @param taskId
	 * @param activityId
	 * @param variables
	 */
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables){
		// 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity); 
        // 执行转向任务  
        taskService.complete(taskId, variables);
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);
	}
	/**
	 * 清空指定活动节点流向 
	 * 
	 * @param activityImpl
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) { 
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
		// 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }
        pvmTransitionList.clear();
        return oriPvmTransitionList;
	}
	/**
	 * 还原指定活动节点流向 
	 * @param activityImpl
	 * @param oriPvmTransitionList
	 */
	private void restoreTransition(ActivityImpl activityImpl,  List<PvmTransition> oriPvmTransitionList) { 
		// 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
	}
}
