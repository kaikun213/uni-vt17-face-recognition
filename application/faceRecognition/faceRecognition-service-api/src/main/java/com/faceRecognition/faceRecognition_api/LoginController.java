package com.faceRecognition.faceRecognition_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@PostMapping
	public HttpStatus login(@RequestParam(value = "company", required = true)String company,@RequestParam(value = "password", required = true) String password){
		// User role
		if (company == "user")
			return HttpStatus.NON_AUTHORITATIVE_INFORMATION;
		// Admin role
		if (company == "admin")
			return HttpStatus.OK;
		// User not found
		else 
			return HttpStatus.NOT_FOUND;
	}

}
