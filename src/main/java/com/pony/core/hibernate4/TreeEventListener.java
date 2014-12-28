package com.pony.core.hibernate4;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pony.core.entity.TreeEntity;

public class TreeEventListener implements PreInsertEventListener,PreUpdateEventListener, PreDeleteEventListener {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TreeEventListener.class);
	
	/**
	 * 创建实体对象之前触发
	 * 
	 */
	public boolean onPreInsert(PreInsertEvent event) {
		Session session = event.getSession();
		Object object = event.getEntity();
		
		FlushMode mode = session.getFlushMode();
		session.setFlushMode(FlushMode.MANUAL); //额外操作单独刷新
		
		if( object instanceof TreeEntity ){
			TreeEntity entity = (TreeEntity)object;
			String entityName = entity.getClass().getSimpleName();
			Long parentId = entity.getParentId();
			
			Long myLft = null;
			Integer myLayer = null;
			
			//非顶层节点
			if(parentId != null){
				//查询父节点的右节点值及层次
				String hql = "select bean.rgt,bean.layer from "+entityName+" bean where bean.id=:pid";
				Object[] objArray = (Object[])session.createQuery(hql).setParameter("pid", parentId).uniqueResult();
				
				Long parentRgt = ((Number)objArray[0]).longValue();
				Integer parentLayer = ((Number)objArray[1]).intValue();
				//更新右节点值大于等于父节点的右节点值为+2
				String updateRgtHql = "update "+entityName+" bean set bean.rgt=bean.rgt+2 where bean.rgt>=:parentRgt";
				//更新左节点值大于等于父节点的右节点值为+2
				String updateLftHql = "update "+entityName+" bean set bean.lft=bean.lft+2 where bean.lft>=:parentRgt";
				
				session.createQuery(updateRgtHql).setParameter("parentRgt", parentRgt).executeUpdate();
				session.createQuery(updateLftHql).setParameter("parentRgt", parentRgt).executeUpdate();
				
				myLft = parentRgt; //设置当前节点的左值为父节点的右值
				myLayer = parentLayer+1;
				//顶层节点
			}else{
				//查询最大右节点值+1为新的节点的左节点值
				String hql = "select max(bean.rgt) from "+entityName+" bean";
				Number maxRgt = (Number) session.createQuery(hql).uniqueResult();
				
				if(maxRgt == null) {
					myLft = 1L; //设置当前节点的左值为1
				}else{
					myLft = maxRgt.longValue() + 1; //设置当前节点的左值为最大右节点的值+1
				}
				myLayer = 1;
			}
			
			session.setFlushMode(mode); //还原刷新模式
			
			entity.setLft(myLft);
			entity.setRgt(myLft+1);
			entity.setLayer(myLayer);
			
		}
		
		return false; //Return true if the operation should be vetoed
	}
	
	/**
	 * 更新实体对象之前触发
	 * 
	 */
	public boolean onPreUpdate(PreUpdateEvent event) {
		Object object = event.getEntity();
		
		if(!(object instanceof TreeEntity)){
			return false;
		}
		
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object[] oldState = event.getOldState();
		Object[] curState = event.getState();
		for(int i=0; i<propertyNames.length; i++){
			if("parentId".equals(propertyNames[i])) {
				Long oldParentId = (Long)oldState[i];
				Long newParentId = (Long)curState[i];
				return updateParent(event.getSession(),(TreeEntity)object,oldParentId,newParentId);
			}
		}
		
		return false;
	}

	private boolean updateParent(Session session, TreeEntity entity,Long oldParentId,Long newParentId){
		
		//父节点id不存在或者都存在且相等时不做节点移动
		if((null==oldParentId && null==newParentId)
				||(null != oldParentId && null != newParentId && oldParentId==newParentId)){
			return false;
		}
		
		String entityName = entity.getClass().getName();
		FlushMode model = session.getFlushMode();
		session.setFlushMode(FlushMode.MANUAL);
		
		//获取移动节点信息
		String hql = "select bean.lft,bean.rgt from "+entityName+" bean where bean.id=:id";
		Object[] node = (Object[])session.createQuery(hql)
					.setParameter("id", entity.getId()).uniqueResult();
		long nodeLft = ((Number) node[0]).longValue();
		long nodeRgt = ((Number) node[1]).longValue();
		long nodeSpan = nodeRgt - nodeLft + 1;
		
		//先空出新父节点的位置
		Long newParentRgt;
		if(null != newParentId){
			//获取移动节点的当前父节点信息
			Object[] newParent = (Object[]) session.createQuery(hql)
					.setParameter("id", newParentId).uniqueResult();
			long newParentLft = ((Number) newParent[0]).longValue();
			    newParentRgt = ((Number) newParent[1]).longValue();
			
			logger.debug("current parent lft={} rgt={}", newParentLft,
					newParentRgt);
			    
			String updateLftHql = "update "+entityName+" bean set bean.lft=bean.lft+:nodeSpan where bean.lft>=:newParentRgt";
			String updateRgtHql = "update "+entityName+" bean set bean.rgt=bean.rgt+:nodeSpan where bean.rgt>=:newParentRgt";
		
			session.createQuery(updateLftHql)
				.setParameter("nodeSpan", nodeSpan)
				.setParameter("newParentRgt", newParentRgt)
				.executeUpdate();
	
			session.createQuery(updateRgtHql)
				.setParameter("nodeSpan", nodeSpan)
				.setParameter("newParentRgt", newParentRgt)
				.executeUpdate();
		}else{
			// 否则查找最大的右边位置
			String maxRgtHql = "select max(bean.rgt) from "+entityName+" bean";
			Number maxRgt = (Number) session.createQuery(maxRgtHql)
					.uniqueResult();
			
			if (maxRgt == null) {
				newParentRgt = 1L; //设置当前节点的左值为1
			} else {
				newParentRgt = maxRgt.longValue() + 1; //设置当前节点的左值为最大右节点的值+1
			}
		}
		
		//复制移动节点到新的父节点下
		long offset = newParentRgt - nodeLft; //新父节点的右节点值变后，新父节点变化前的值变为移动节点的左节点值
		String updateHql = "update "+entityName+" bean set bean.lft=bean.lft+:offset,bean.rgt=bean.rgt+:offset where bean.lft between :nodeLft and :nodeRgt";
		session.createQuery(updateHql).setParameter("offset", offset)
				.setParameter("nodeLft", nodeLft)
				.setParameter("nodeRgt", nodeRgt)
				.executeUpdate();
		
		//删除移动节点（原先父节点下的）
		String updateLftHql = "update "+entityName+" bean set bean.lft=bean.lft-:nodeSpan where bean.lft>:nodeRgt";
		String updateRgtHql = "update "+entityName+" bean set bean.rgt=bean.rgt-:nodeSpan where bean.rgt>:nodeRgt";
		session.createQuery(updateLftHql)
				.setParameter("nodeSpan", nodeSpan)
				.setParameter("nodeRgt", nodeRgt)
				.executeUpdate();

		session.createQuery(updateRgtHql)
				.setParameter("nodeSpan", nodeSpan)
				.setParameter("nodeRgt", nodeRgt)
				.executeUpdate();
	
		session.setFlushMode(model);
		return false;
	}
	
	/**
	 * 删除实体对象之前触发
	 * 
	 */
	public boolean onPreDelete(PreDeleteEvent event) {
		Object object = event.getEntity();
		
		if( object instanceof TreeEntity ){
			TreeEntity entity = (TreeEntity)object;
			
			String entityName = entity.getClass().getName();
			Session session = event.getSession();
			FlushMode model = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			
			//查询当前节点的左右值
			String hql = "select bean.lft,bean.rgt from "+entityName+" bean where bean.id=:id";
			Object[] objArray = (Object[])session.createQuery(hql)
					.setParameter("id", entity.getId()).uniqueResult();
			
			Long myLft = ((Number)objArray[0]).longValue();
			Long myRgt = ((Number)objArray[1]).longValue();
			Long mySpan = myRgt-myLft+1;
			
			String deleteHql = "delete from "+entityName+" bean where bean.lft > :myLft and bean.lft < :myRgt";
			session.createQuery(deleteHql)
					.setParameter("myLft", myLft)
					.setParameter("myRgt", myRgt)
					.executeUpdate();
			
			//更新左节点大于删除节点的左-右+1
			String updateLftHql = "update "+entityName+" bean set bean.lft=bean.lft-:mySpan where bean.lft>:myLft";
			//更新右节点大于删除节点的左-右+1
			String updateRgtHql = "update "+entityName+" bean set bean.rgt=bean.rgt-:mySpan where bean.rgt>:myRgt";
			
			session.createQuery(updateLftHql)
					.setParameter("mySpan", mySpan)
					.setParameter("myLft", myLft)
					.executeUpdate();
			
			session.createQuery(updateRgtHql)
					.setParameter("mySpan", mySpan)
					.setParameter("myRgt", myRgt)
					.executeUpdate();
			
			session.setFlushMode(model);
			
		}
		
		return false;
	}
}
