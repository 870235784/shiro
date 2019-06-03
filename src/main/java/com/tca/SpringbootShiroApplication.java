package com.tca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootShiroApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringbootShiroApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootShiroApplication.class, args);
		logger.info("---------------- shiro server starts ---------------");
	}

}
