package com.tca.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tca.SpringbootShiroApplicationTests;

public class MyRealmTest extends SpringbootShiroApplicationTests{
	
	private static final Logger logger = LoggerFactory.getLogger(MyRealmTest.class);

	@Test
	public void testMyRealm() {
		// 第一步:根据shiro.ini配置文件创建IniSecurityManagerFactory工厂类
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:chapter2/shiro-realm.ini");
		// 第二步:创建SecurityManager实例
		SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
		// 第三步:绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 第四步:获取Subject实例
		Subject subject = SecurityUtils.getSubject();
		// 第五步:获取UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhou", "123");
		// 第六步:登录
		try {
			subject.login(usernamePasswordToken);
			logger.info("登录成功!");
		} catch (Exception e) {
			logger.error("登录失败:", e);
		}
	}
	
	@Test
	public void testMyRealmMulti() {
		// 第一步:根据shiro.ini配置文件创建IniSecurityManagerFactory工厂类
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:chapter2/shiro-realm-multi.ini");
		// 第二步:创建SecurityManager实例
		SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
		// 第三步:绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 第四步:获取Subject实例
		Subject subject = SecurityUtils.getSubject();
		// 第五步:获取UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhou", "123");
		// 第六步:登录
		try {
			subject.login(usernamePasswordToken);
			logger.info("登录成功!");
		} catch (Exception e) {
			logger.error("登录失败:", e);
		}
	}
	
	@Test
	public void testRealmAuthenticationStrategy() {
		// 第一步:根据shiro.ini配置文件创建IniSecurityManagerFactory工厂类
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory(
				"classpath:chapter2/shiro-realm-authentication-strategy.ini");
		// 第二步:创建SecurityManager实例
		SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
		// 第三步:绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 第四步:获取Subject实例
		Subject subject = SecurityUtils.getSubject();
		// 第五步:获取UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhou", "123");
		// 第六步:登录
		try {
			subject.login(usernamePasswordToken);
			logger.info("登录成功!");
		} catch (Exception e) {
			logger.error("登录失败:", e);
		}
	}
	
}
