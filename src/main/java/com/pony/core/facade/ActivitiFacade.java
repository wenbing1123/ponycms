package com.pony.core.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * activiti5.16.4 version
 * 对于在业务归档中的每一个流程定义会执行下面的步骤来初始化一个key,version,name和id：
 * 	xml文件中的流程定义的id属性是被用于作为流程定义的key属性。
 * 	xml文件中的流程定义的name属性是被用于作为流程定义的name属性。如果name属性没有被指定，那么id属性被作为name属性。
 * 	一个有特殊key的流程第一次被部署，版本号为1。对于拥有同样key的流程定义，所有之后被部署的，版本号会递增1。key属性被用于区分不同的流程定义。
 * 	id属性被设置成 {processDefinitionKey}:{processDefinitionVersion}:{generated-id}，在一个集群环境中的流程定义缓存中，这里的generated-id是一个唯一的数字，为了保证流程id的唯一性。
 *  
 * <definitions id="myDefinitions">
 * 	<process id="myProcess" name="My important process">
 * 
 * 
 * @author scott
 *
 */
public interface ActivitiFacade {

	/**
	 * 根据流程定义xml来部署流程定义
	 * 
	 * @param name 流程部署名称
	 * @param xmlName 流程定义xml名称
	 * @param xmlData 流程定义xml数据
	 */
	public void deployProcessDefinition(String name, String xmlName, byte[] xmlData);
	
	/**
	 * 根据流程定义xml及img来部署流程定义
	 * 
	 * @param name 流程部署名称
	 * @param xmlName 流程定义xml名称
	 * @param xmlData 流程定义xml数据
	 * @param imgName 流程定义img名称
	 * @param imgData 流程定义img数据
	 * @return
	 */
	public void deployProcessDefinition(String name, String xmlName, byte[] xmlData, String imgName, byte[] imgData);
	
	/**
	 * 根据流程定义xml的Classpath路径来部署流程定义
	 * 
	 * @param classpathXml
	 */
	public void deployProcessDefinition(String classpathXml);
	
	/**
	 * 根据流程定义xml及img的Classpath路径来部署流程定义
	 * 
	 * @param classpathXml
	 * @param classpathImg
	 */
	public void deployProcessDefinition(String classpathXml, String classpathImg);
	
	/**
	 * 根据流程定义ZIP包部署流程定义,部署单个流程
	 * 
	 * @param name 流程部署名称
	 * @param zipData 流程定义ZIP包
	 */
	public void deployProcessDefinition(String name, ZipInputStream zipData);
	
	/**
	 * 删除流程定义
	 * 
	 * @param 流程定义名称
	 */
	public void undeployProcessDefinition(String name);
	
	/**
	 * 删除流程定义
	 * 
	 * @param 流程定义名称
	 * @param cascade Deletes the given deployment and cascade deletion to process instances, history process instances and jobs.
	 */
	public void undeployProcessDefinition(String name, boolean cascade);
	
	//=============================================================================
	
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
	 * 根据流程实例查看图表
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public InputStream getProcessInstanceDiagram(String processInstanceId);
	
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
