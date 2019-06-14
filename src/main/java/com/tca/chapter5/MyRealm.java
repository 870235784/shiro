package com.tca.chapter5;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRealm extends AuthorizingRealm{
	
	private PasswordService passwordService;
	
	private static final String PASSWORD_ENC = 
			"$shiro1$SHA-512$1$$PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri"
			+ "4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==";
	
	private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String principal = (String)token.getPrincipal();// 用户名
		String credentials = new String((char[])token.getCredentials());// 密码
		if (!"zhou".equals(principal)) {
			throw new UnknownAccountException();
		}
		logger.info(credentials + " 加密后: " + passwordService.encryptPassword(credentials)); 
		if (!PASSWORD_ENC.equals(passwordService.encryptPassword(credentials))) {
			throw new IncorrectCredentialsException();
		}
		logger.info("getName() = " + getName()); 
		return new SimpleAuthenticationInfo(principal, PASSWORD_ENC, getName());
	}


}
