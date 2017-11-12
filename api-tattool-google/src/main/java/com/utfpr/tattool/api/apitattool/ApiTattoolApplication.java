package com.utfpr.tattool.api.apitattool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ApiTattoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTattoolApplication.class, args);
	}
}
