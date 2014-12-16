package com.pony.oa.user;

import org.junit.Test;

import com.pony.oa.util.SecurityUtils;


public class PasswordTest {

	@Test
	public void testPassword() {
		String password = SecurityUtils.encryptPassword("admin", "admin");
		//password="ePmTxgphJsUoiIaW54fs+4+1VN83N5FjU41lTmHi3J+J4quL3x68Id+T4XMHsFp2qLpqnyFN7VHGv0COa2jBZw=="
		System.out.print(password);
	}
}
