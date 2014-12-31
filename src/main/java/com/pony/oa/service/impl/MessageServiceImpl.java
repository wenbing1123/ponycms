package com.pony.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pony.core.dao.IDao;
import com.pony.oa.entity.Message;
import com.pony.oa.service.IMessageService;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements IMessageService {

	@Resource private IDao dao;
	
	public void markRead(Long[] ids) {
		Assert.notNull(ids);
		for (Long id : ids) {
			Message message = dao.find(Message.class, id);
			if(!message.getIfRead()){
				message.setIfRead(true);
				dao.update(message);
			}
		}
	}
	
	public Integer getUnreadCount(Long userId){
		Assert.notNull(userId);
		Long count = dao.findOne("select count(m.id) from Message m where m.receiver.id = :userId",userId);
		if(count != null){
			return count.intValue();
		}else{
			return 0;
		}
	}

}
