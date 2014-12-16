package com.pony.core.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 流程引擎门面类
 * 
 * @author scott
 *
 */
public interface ActivitiFacade {

	/**
	 * 根据类路径下的流程定义文件来部署流程
	 * 
	 * @param name 流程定义的显示别名,存储工作流（workflow）中的name
	 * @param xmlDefinition 流程定义文件
	 */
	public void deployProcessDefinition(String name, String xmlDefinition);
	
	/**
	 * 根据类路径下的流程定义文件来部署流程
	 * 
	 * @param name 流程定义的显示别名,存储工作流（workflow）中的name
	 * @param xmlDefinition 流程定义文件
	 * @param imgDefinition 流程定义图片
	 */
	public void deployProcessDefinition(String name, String xmlDefinition, String imgDefinition);
	
	/**
	 * 指定流程定义包部署
	 * 
	 * @param name 流程定义的显示别名,存储工作流（workflow）中的name
	 * @param zipDefinition 流程定义ZIP包
	 */
	public void deployProcessDefinition(String name, ZipInputStream zipDefinition);
	
	/**
	 * 删除流程定义
	 * 
	 * @param deploymentId 流程部署Id
	 * @param cascade 是否级联删除流程实例，为true则会删除和当前规则相关的所有信息，包括历史，为false则抛出异常
	 */
	public void undeployProcessDefinition(String deploymentId, boolean cascade);
	
	/**
	 * 流程定义查询列表
	 * 
	 * @return
	 */
	public List<ProcessDefinition> queryProcessDefinitionList();
	
	/**
	 * 流程定义查询记录数
	 * 
	 * @return
	 */
	public long queryProcessDefinitionCount();
	
	/**
	 * 流程定义分页查询
	 * 
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public List<ProcessDefinition> queryProcessDefinitionListPage(int firstIndex, int maxResult);
	
	/**
	 * 查看流程定义图片
	 * 
	 * @param deploymentId
	 * @return
	 */
	public InputStream queryProcessDefinitionImage(String deploymentId);
	
	/**
	 * 启动流程实例
	 * 
	 * @param processKey 流程定义Key
	 * @param userId	启动流程的用户
	 * @param variables	流程流转附带的变量集，由动态表单及公文公共组合而成
	 * @return 流程实例Id
	 */
	public String startProcessInstance(String processDefinitionKey, String userId, Map<String,Object> variables);
	
	/**
	 * 启动流程实例
	 * 
	 * @param processKey 流程定义Key
	 * @param userId	启动流程的用户
	 * @param bussinessKey	业务Key,存储工作流（workflow）中Id
	 * @param variables	流程流转附带的变量集，由动态表单及公文公共组合而成
	 * @return 流程实例Id
	 */
	public String startProcessInstance(String processDefinitionKey, String userId, String bussinessKey, Map<String,Object> variables);
	
	/**
	 * 流程实例查询列表
	 * 
	 * @return
	 */
	public List<ProcessInstance> queryProcessInstanceList(String processDefinitionKey);
	
	/**
	 * 流程实例查询记录数
	 * 
	 * @return
	 */
	public long queryProcessInstanceCount(String processDefinitionKey);
	
	/**
	 * 流程实例分页查询
	 * 
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public List<ProcessInstance> queryProcessInstanceListPage(String processDefinitionKey, int firstIndex, int maxResult);

	/**
	 * 领取任务，具有候选组的用户把任务变成指派任务，候选组内的其它人就没有此任务
	 * 
	 * @param taskId 任务实例
	 * @param userId 领取用户
	 */
	public void claimTask(String taskId, String userId);
	
	/**
	 * 完成任务
	 * 
	 * @param taskId 任务实例
	 */
	public void completeTask(String taskId);
	
	/**
	 * 委派任务，领导把任务委派给指定人去做
	 * 
	 * @param taskId 任务实例
	 * @param userId 委派用户
	 */
	public void delegateTask(String taskId, String userId);
	
	/**
	 * 完成委派任务
	 * 
	 * @param taskId 任务实例
	 */
	public void resolveTask(String taskId);
	
	/**
	 * 查询指派任务
	 * 
	 * @param userId 指派用户
	 * @return 任务列表
	 */
	public List<Task> queryAssigneeTaskList(String userId);
	
	/**
	 * 查询候选任务
	 * 
	 * @param userId 候选用户
	 * @return 任务列表
	 */
	public List<Task> queryCandidateTaskList(String userId);
	
	/**
	 * 查询委派任务
	 * 
	 * @param userId 候选用户
	 * @return 任务列表
	 */
	public List<Task> queryDelegateTaskList(String userId);
	
	/**
	 * 查询已完成委派任务
	 * 
	 * @param userId 候选用户
	 * @return 任务列表
	 */
	public List<Task> queryResolveTaskList(String userId);
	
}
