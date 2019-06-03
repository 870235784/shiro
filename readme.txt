第一章 shiro简介
1.主要功能
	认证，授权，加密，会话管理，与web继承，缓存等
2.主要模块
	Authentication：身份认证/登录，验证用户是不是拥有相应的身份;
	Authorization：授权，即权限验证，验证某个已认证的用户是否拥有某个权限;
	Session Manager：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中;会话可以是javase环境，也可以是web环境
	Cryptography：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储;
	Web Support：web支持，集成到web环境;
	Caching：缓存，比如用户登录后，其用户信息，拥有的角色/权限不必每次去查，可以提升效率;
	Concurrency：shiro支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去;
	Testing：提供测试支持;
	Run As：允许一个用户假装为另一个用户(如果他们允许)的身份进行访问;
	Remember Me：记住我
3.核心组件
	Subject 主体
	SucurityManager
	Realm 验证主体的数据源
	
第二章 身份验证
1.jar包依赖
	<dependency>
		<groupId>org.apache.shiro</groupId>
		<artifactId>shiro-core</artifactId>
		<version>1.2.2</version>
	</dependency>
	
2.登录登出流程
	2.1 步骤:
		step1:首先调用Subject.login(token)进行登录，其会自动委托给Security Manager，调用之前必须通过SecurityUtils. setSecurityManager()设置；
		step2:SecurityManager负责真正的身份验证逻辑；它会委托给Authenticator进行身份验证；
		step3:Authenticator才是真正的身份验证者，Shiro API中核心的身份认证入口点，此处可以自定义插入自己的实现；
		step4:Authenticator可能会委托给相应的AuthenticationStrategy进行多Realm身份验证，默认ModularRealmAuthenticator会调用
			AuthenticationStrategy进行多Realm身份验证；
		step5:Authenticator 会把相应的token 传入Realm，从Realm 获取身份验证信息，如果没有返回/抛出异常表示身份验证失败了。此处可以配置多个Realm，
			将按照相应的顺序及策略进行访问。
	2.2 代码:
		step1:在resource类路径下添加shiro.ini配置文件
			[users]
			zhou=123
			tca=123
		step2:写业务代码
			见 com.tca.chapter1.HelloWorldTest的testHelloworld方法

3.Realm
	3.1 概念:
		域，Shiro从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定
		用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource ， 即安全数据源。如我们之前的ini配置方式
		将使用org.apache.shiro.realm.text.IniRealm。
	3.2 Realm接口
		方法一: String getName(); //返回一个唯一的Realm名字
		方法二: boolean supports(AuthenticationToken token); //判断此Realm是否支持此Token
		方法三: AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException; //根据Token获取认证信息
	3.3 单个Realm配置使用
		step1: 实现自定义MyRealm implements Realm接口
			见 com.tca.chapter1.MyRealm
		step2: 在resource类路径下添加shiro-realm.ini配置文件
			#声明一个realm
			myRealm1=MyRealm
			#指定securityManager的realms实现
			securityManager.realms=$myRealm1
		step3: 写业务代码
			见 com.tca.chapter1.MyRealmTest的testMyRealm方法
	3.4 多个Realm配置使用
		step1: 实现自定义MyRealm1, MyRealm2 implements Realm接口
			见 com.tca.chapter1.MyRealm1  com.tca.chapter1.MyRealm2
		step2: 在resource类路径下添加shiro-realm-multi.ini配置文件
			myRealm1=MyRealm1
			myRealm2=MyRealm2
			securityManager.realms=$myRealm1,$myRealm2
		step3: 写业务代码
			见 com.tca.chapter1.MyRealmTest的testMyRealmMulti方法
		注意: 此处有2个Realm, 默认验证策略为: AtLeastOneSuccessfulStrategy, 即只需要有一个Realm验证成功即可
	3.5 shiro提供的Realm实现
		<I>Realm
		<A>CachingRealm
		<A>AuthenticatingRealm
		<A>AuthorizingRealm
		... ...
		最常用的Realm:
			1.org.apache.shiro.realm.text.IniRealm:[users]部分指定用户名/密码及其角色;[roles]部分指定角色即权限信息;
			2.org.apache.shiro.realm.text.PropertiesRealm:user.username=password,role1,role2指定用户名/密码及其角色;
				role.role1=permission1,permission2指定角色及权限信息;
			3.org.apache.shiro.realm.jdbc.JdbcRealm:通过sql查询相应的信息

4.Authenticator及AuthenticationStrategy
	4.1 Authenticator
		Authenticator的职责是验证用户帐号,是Shiro API中身份验证核心的入口点:
			public AuthenticationInfo authenticate(AuthenticationToken authenticationToken) throws AuthenticationException;
			如果验证成功,将返回AuthenticationInfo 验证信息;此信息中包含了身份及凭证;如果验证失败将抛出相应的AuthenticationException实现
	4.2 AuthenticationStrategy
		AuthenticationStrategy表示认证策略
			FirstSuccessfulStrategy:只要有一个Realm验证成功即可,只返回第一个Realm身份验证成功的认证信息,其他的忽略;
			AtLeastOneSuccessfulStrategy:只要有一个Realm验证成功即可,和FirstSuccessfulStrategy不同,返回所有Realm身份验证成功的认证信息;
			AllSuccessfulStrategy:所有Realm验证成功才算成功,且返回所有Realm身份验证成功的认证信息,如果有一个失败就失败了.
		ModularRealmAuthenticator默认使用AtLeastOneSuccessfulStrategy策略。
	4.3 认证策略的选择使用
		step1: 实现自定义MyRealm1, MyRealm2 implements Realm接口
			见 com.tca.chapter1.MyRealm1  com.tca.chapter1.MyRealm2
		step2: 在resource类路径下添加shiro-realm-authentication-strategy.ini配置文件
			#指定securityManager的authenticator实现
			authenticator=org.apache.shiro.authc.pam.ModularRealmAuthenticator
			securityManager.authenticator=$authenticator
			#指定securityManager.authenticator的authenticationStrategy(本例中使用AllSuccessfulStrategy)
			allSuccessfulStrategy=org.apache.shiro.authc.pam.AllSuccessfulStrategy
			securityManager.authenticator.authenticationStrategy=$allSuccessfulStrategy 
			#指定realm
			myRealm1=MyRealm1
			myRealm2=MyRealm2
			securityManager.realms=$myRealm1,$myRealm2
		step3: 写业务代码
			
		
		
		
		
		
		
		
		
		