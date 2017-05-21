package com.faceRecognition.faceRecognition_api;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import com.faceRecognition.admin_service.Admin;
import com.faceRecognition.utils_service.Utils;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE)
				.sources(Utils.class, Admin.class, Application.class).run(args);
	}
}