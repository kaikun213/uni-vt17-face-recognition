package com.faceRecognition.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.faceRecognition.admin.service.AdminServiceImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = { Application.class, AdminServiceImpl.class})
public class Application {
	
	public static void main(String[] args){
        SpringApplication.run(Application.class, args);
	}

}
