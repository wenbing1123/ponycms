package com.pony.oa.controller.personal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pony.core.Result;
import com.pony.core.hibernate4.jpa.SearchFilter;
import com.pony.core.hibernate4.jpa.SearchFilter.Operator;
import com.pony.core.pageable.Page;
import com.pony.core.service.ICrudService;
import com.pony.oa.entity.Message;
import com.pony.oa.service.IMessageService;
import com.pony.oa.util.SecurityUtils;

@Controller
@RequestMapping("/messageController")
public class MessageController {

	@Autowired ICrudService crudService;
	@Autowired IMessageService messageService;
	
	@RequestMapping(params="gridData")
	public @ResponseBody Page<Message> gridData(HttpServletRequest request) {
		List<SearchFilter> filters = SearchFilter.parse(request);
		filters.add(new SearchFilter("receiver.id",Operator.EQ,SecurityUtils.getUserid()));
		return crudService.findPage(Message.class,filters);
	}
	
	@RequestMapping(params="markRead")
	public @ResponseBody Result markRead(Long[] ids) throws Exception{
		messageService.markRead(ids);
		return Result.success();
	}
	
	@RequestMapping(params="remove")
	public @ResponseBody Result remove(Long[] ids) throws Exception{
		crudService.remove(Message.class, ids);
		return Result.success();
	}
	
}
