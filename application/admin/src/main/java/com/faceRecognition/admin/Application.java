package com.faceRecognition.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.faceRecognition.admin.config.JPAConfig;
import com.faceRecognition.admin.service.AdminServiceImpl;
import com.faceRecognition.utils.database.repository.UserEntitiesRepository;
import com.faceRecognition.utils.database.service.DatabaseServiceImpl;
import com.faceRecognition.utils.face.service.FaceServiceImpl;
import com.faceRecognition.utils.storage.service.StorageServiceImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, AdminServiceImpl.class,
										StorageServiceImpl.class,DatabaseServiceImpl.class, FaceServiceImpl.class,
										UserEntitiesRepository.class, JPAConfig.class})
public class Application {
	
	public static void main(String[] args){
        SpringApplication.run(Application.class, args);
	}

}
