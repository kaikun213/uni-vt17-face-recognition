package com.faceRecognition.faceRecognition_api;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.faceRecognition.admin_service.Admin;
import com.faceRecognition.user_service.User;
import com.faceRecognition.utils_service.Utils;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE)
				.sources(Utils.class, Admin.class, User.class, Application.class).run(args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/admin").allowedOrigins("*");
	            registry.addMapping("/admin").allowedMethods("POST, GET, OPTIONS, DELETE");
	            registry.addMapping("/admin/*").allowedOrigins("*");
	            registry.addMapping("/user").allowedOrigins("*");
	            registry.addMapping("/user").allowedMethods("POST, GET, OPTIONS, DELETE");
	            registry.addMapping("/login").allowedOrigins("*");
	            registry.addMapping("/login").allowedMethods("POST, GET, OPTIONS, DELETE");
	        }
	    };
	}
}

