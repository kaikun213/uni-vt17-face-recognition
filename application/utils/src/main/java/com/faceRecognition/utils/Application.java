package com.faceRecognition.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.faceRecognition.utils.database.service.DatabaseServiceImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = { Application.class, DatabaseServiceImpl.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}