package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:spring-mvc.xml"})
//filter和listener监听器注解需要用的的
@ServletComponentScan
public class AyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AyApplication.class, args);
	}
}
