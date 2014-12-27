package com.pony.core.util;

import java.util.Random;

public class RandomUtils {
	
	/** 
	  * 生成随即密码 
	  * @param pwd_len 生成的密码的总长度 
	  * @return  密码的字符串 
	  */  
	 public static String randomPwd(int length) {
		  char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};  
		  StringBuffer pwd = new StringBuffer("");  
		  Random r = new Random();
		  int maxNum = 9; 
		  int i;  //生成的随机数  
		  int count = 0; //生成的密码的长度  
		  while(count < length){  
			   //生成随机数，取绝对值，防止生成负数，  
			   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为10-1  
			   if(i >= 0 && i < str.length) {  
				   pwd.append(str[i]);  
				   count ++;
			   }  
		  }  
		  return pwd.toString();  
	 }  
}
