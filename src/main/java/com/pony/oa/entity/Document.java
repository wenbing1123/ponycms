package com.pony.oa.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.pony.core.SystemException;
import com.pony.core.entity.BaseEntity;
import com.pony.core.util.BeanUtilsEx;

@Entity
@Table(name="oa_document")
public class Document extends BaseEntity {

	protected Logger logger = Logger.getLogger(getClass());
	
	private	String	title;		//标题
	private String	content; 	//内容
	private	String	description;//描述
	
	private User		creator; 	//创建者，创建时间在父类中
	private	String		status; 	//状态,新建，提交流程后每一步更新状态，最后归档
	private	Workflow 	workflow; 	//工作流
	private	String		processInstanceId; //流程实例Id
	private Map<String,DocumentProperty> properties; //公文动态表单属性
	
	/**
	 * 设置公文的动态属性集合
	 * 
	 * @param props
	 */
	public void setPropertiesMap(Map<String, Object> props){
        if(workflow.getForm() == null){  
            return;
        }
        List<Field> fields = workflow.getForm().getFields();
        Field field = null; //=>
        for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
            field =  iterator.next();
            setProperty(field.getName(), props.get(field.getName()));
        }
    }
	
	/**
	 * 设置动态属性
	 * @param name
	 * @param value
	 */
   public void setProperty(String name, Object value){
		try{
			String type = getPropertyType(name);
			 DocumentProperty dp = new DocumentProperty();
	         if(!DocumentProperty.support(type)){
	                throw new SystemException("不支持的数据类型！");
	         }
	         
	         //触发相应的Converter被调用
	         BeanUtilsEx.copyProperty(dp, type, value);
	             
	         if(properties == null){
	                properties = new HashMap<String, DocumentProperty>();
	         }
	         properties.put(name, dp);
		}catch(Exception e){
			throw new SystemException("公文动态属性设置出现异常！", e);
		}
	}
   
   /**
    * 获取动态属性
    * 
    * @param name
    * @return
    */
   public Object getProperty(String name){
       //根据对应的fieldType，从DocumentProperty转换为相应的类型，并返回
       try {
           if(properties == null){
               return null;
           }
           DocumentProperty dp = (DocumentProperty)properties.get(name);
           String type = getPropertyType(name);
            
           return PropertyUtils.getProperty(dp, type);
            
       } catch (Exception e) {
           throw new SystemException("获取公文属性["+name+"]的时候发生异常！",e);
       }
        
   }
   
   /**
    * 获取动态属性类型
    * @param name
    * @return
    */
   private String getPropertyType(String name){
       //需根据对应fieldType，转换为相应的类型(DocumentProperty)，并存储
       try {
           List<Field> fields = workflow.getForm().getFields();
           Field field = null; //=>
           for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
               field =  iterator.next();
               if(field.getName().equals(name)){
                   break;
               }
           }
           //获得对应属性的类型名称
           return field.getType();
       } catch (Exception ignore) {
           logger.warn("获取对应属性["+name+"]的类型名称时出现异常！",ignore);
       }
       throw new SystemException("无法获得对应属性的类型名称");
   }
	
	@Column(length=100)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Lob @Basic(fetch=FetchType.LAZY)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(length=200)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(length=36)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="flow_id")
	public Workflow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
	@Column(length=36)
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@OneToMany(mappedBy="document",cascade=CascadeType.ALL)
	@JoinColumn(name="document_id")
	@MapKey(name="property_name")
	public Map<String, DocumentProperty> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, DocumentProperty> properties) {
		this.properties = properties;
	}
	
}
