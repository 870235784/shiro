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

public class HelloWorldTest extends SpringbootShiroApplicationTests{
	
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);
	
	
	/**
	 * 存在问题：
	 * 	用户名密码应存储在 数据库 中, 且密码需要密文处理
	 */
	@Test
	public void testHelloworld() {
		// 第一步:根据shiro.ini配置文件创建IniSecurityManagerFactory工厂类
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 第二步:创建SecurityManager实例
		SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
		// 第三步:绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 第四步:获取Subject实例
		Subject subject = SecurityUtils.getSubject();
		// 第五步:获取UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("tca", "123");
		
		// 第六步:登录
		try {
			subject.login(usernamePasswordToken);
			logger.info("登录成功!");
		} catch (Exception e) {
			logger.error("登录失败:", e);
		}
		
		// 第七步:注销
//		subject.logout();
	}
}
