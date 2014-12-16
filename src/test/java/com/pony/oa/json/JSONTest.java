package com.pony.oa.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.pony.core.json.JSON;
import com.pony.oa.entity.User;

public class JSONTest {

	@Test
	public void testNotnull() throws Exception{
		
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setUsername("测试");
		user.setCreatetime(new Date());
		users.add(user);
		System.out.println(JSON.get().readAsString(users));
	}
	
	
}
