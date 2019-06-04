package com.tca.chapter3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tca.SpringbootShiroApplicationTests;

public class RealmRoleTest extends SpringbootShiroApplicationTests{

	private static final Logger logger = LoggerFactory.getLogger(RealmRoleTest.class);
	
	private Subject login(String configurationClasspath, String username, String password) {
		// 第一步:根据shiro.ini配置文件创建IniSecurityManagerFactory工厂类
		IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:chapter3/" + configurationClasspath);
		// 第二步:创建SecurityManager实例
		SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
		// 第三步:绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 第四步:获取Subject实例
		Subject subject = SecurityUtils.getSubject();
		// 第五步:获取UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		// 第六步:登录
		try {
			subject.login(usernamePasswordToken);
			logger.info("登录成功!");
			return subject;
		} catch (Exception e) {
			logger.error("登录失败:", e);
			return null;
		}
	}

	@Test
	public void testRole() {
		// 登录
		Subject subject = login("shiro-role.ini", "tca", "123");
		if (subject == null) {
			return;
		}
		// 验证
		try {
			subject.checkRole("role1");
			subject.checkRole("role2");
			logger.info("验证成功!");
		} catch (Exception e) {
			logger.error("验证失败:", e);
		}
	}
	
	@Test
	public void testRolePermission() {
		// 登录
		Subject subject = login("shiro-role-permission.ini", "zhou", "123");
		if (subject == null) {
			return;
		}
		// 验证
		try {
			subject.checkRole("role1");
//			subject.checkRole("role2");
			logger.info("验证成功角色!");
			subject.checkPermission("user:create");
			subject.checkPermissions("user:create", "user:delete");
			boolean isPermitted = subject.isPermitted("user:update");
			logger.info("user:update isPermitted? " + isPermitted);
			logger.info("验证角色权限成功!");
		} catch (Exception e) {
			logger.error("验证失败:", e);
		}
	}
}
