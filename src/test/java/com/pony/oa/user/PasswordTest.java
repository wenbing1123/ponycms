package com.pony.oa.user;

import java.util.Random;

import org.junit.Test;

import com.pony.core.util.RandomUtils;
import com.pony.oa.util.SecurityUtils;


public class PasswordTest {

	@Test
	public void testPassword() {
		String password = SecurityUtils.encryptPassword("admin", "admin");
		//password="ePmTxgphJsUoiIaW54fs+4+1VN83N5FjU41lTmHi3J+J4quL3x68Id+T4XMHsFp2qLpqnyFN7VHGv0COa2jBZw=="
		System.out.print(password);
	}
	
	@Test
	public void testRandom(){
		Random rd = new Random();
		String n = "";
		int getNum;
		do {
			//getNum = Math.abs(rd.nextInt()) % 10 + 48;// 产生数字0-9的随机数
			getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
			char num1 = (char) getNum;
			String dn = Character.toString(num1);
			n += dn;
		} while (n.length() < 6);
		System.out.println("随机的6位密码是：" + RandomUtils.randomPwd(6));
	}
}
