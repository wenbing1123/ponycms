package com.pony.oa.service;

public interface IMessageService {

	/**
	 * 标记消息为已读
	 * 
	 * @param ids
	 */
	public void markRead(Long[] ids);
	
	/**
	 * 获取用户未读消息数
	 * 
	 * @param receiverId
	 * @return
	 */
	public Integer getUnreadCount(Long userId);
	
}
