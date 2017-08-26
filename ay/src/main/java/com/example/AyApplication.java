package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ImportResource(locations={"classpath:spring-mvc.xml"})
//filter和listener监听器注解需要用的的
@ServletComponentScan
@EnableRetry
@EnableAsync
public class AyApplication {

	public static void main(String[] args) {
		System.out.println("");

		SpringApplication.run(AyApplication.class, args);
	}
}
