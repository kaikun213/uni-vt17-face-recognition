package com.faceRecognition.faceRecognition_api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@PostMapping
	public String login(@RequestParam(value = "company", required = true)String company,@RequestParam(value = "password", required = true) String password){
		// User role
		if (company.equals("user"))
			return "user";
		// Admin role
		if (company.equals("admin"))
			return "admin";
		// User not found
		else 
			return "rejected";
	}

}
