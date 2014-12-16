package com.pony.oa.util;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;

import com.pony.oa.Principal;

//当前用户安全相关
public class SecurityUtils {

	public static Long getUserid(){
		return getPrincipal().getUserid();
	}
	
	public static String getUsername(){
		return getPrincipal().getUsername();
	}
	
	public static Principal getPrincipal(){
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		return (Principal) subject.getPrincipal();
	}
	
	public static String encryptPassword(String salt, String str){
		Sha512Hash sha512Hash = new Sha512Hash(str, salt, 1024);
		return sha512Hash.toBase64();
	}
	
}
