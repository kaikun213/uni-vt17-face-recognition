package com.faceRecognition.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.faceRecognition.utils.database.service.DatabaseServiceImpl;
import com.faceRecognition.utils.face.service.FaceServiceImpl;
import com.faceRecognition.utils.storage.service.StorageServiceImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = { Application.class, DatabaseServiceImpl.class, FaceServiceImpl.class, StorageServiceImpl.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}