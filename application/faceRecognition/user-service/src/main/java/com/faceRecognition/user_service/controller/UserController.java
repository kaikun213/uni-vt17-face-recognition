package com.faceRecognition.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.faceRecognition.user_service.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<String> retrieve(@PathVariable String image) {
		try {
			return new ResponseEntity<String>(this.userService.retrieve(image), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
		}
	}
}