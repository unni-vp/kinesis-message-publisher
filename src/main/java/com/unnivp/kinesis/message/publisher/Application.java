package com.unnivp.kinesis.message.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application startup class for the Spring boot executable.
 * 
 * @author unnivp
 * 
 */
@EnableScheduling
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootServletInitializer.class);

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		logger.info("Kinesis Message Publisher Simulator Started");
	}

}
