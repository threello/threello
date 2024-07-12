package com.sparta.threello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ThreelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreelloApplication.class, args);


	}

}
