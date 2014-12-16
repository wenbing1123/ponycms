package com.pony.oa;

import java.util.Date;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.pony.oa.entity.User;
import com.pony.oa.service.ILogService;
import com.pony.oa.service.IUserService;
import com.pony.oa.util.SecurityUtils;

public class AuthenticationRealm  extends AuthorizingRealm{
	
	public final static String ROLE_ADMIN = "user";
	public final static String ROLE_Member = "member";

	@Autowired IUserService userService;
	@Autowired ILogService logService;
	
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if(principal != null){
			Set<String> perms = userService.findPermsByUserid(principal.getUserid());
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addStringPermissions(perms);
			authorizationInfo.addRole(ROLE_ADMIN);
			return authorizationInfo;
		}
		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken)token;
		String username = authenticationToken.getUsername();
		String password = authenticationToken.getPassword()!=null?new String(authenticationToken.getPassword()):"";
		
		if(username != null && password != null){
			User user = userService.findByUsername(username);
			if(user == null){
				throw new UnknownAccountException();
			}
			if(user.getEnabled()!=null && !user.getEnabled()){
				throw new DisabledAccountException();
			}
			if(user.getLocked()!=null && user.getLocked()){
				throw new LockedAccountException();
			}
			if(!SecurityUtils.encryptPassword(username, password).equals(user.getPassword())){
				throw new IncorrectCredentialsException();
			}
			user.setLastLoginIp(authenticationToken.getHost());
			user.setLastLoginDate(new Date());
			userService.update(user);
			return new SimpleAuthenticationInfo(new Principal(user.getId(), user.getUsername()), password, getName());
		}
		
		throw new UnknownAccountException();
		
	}

}
