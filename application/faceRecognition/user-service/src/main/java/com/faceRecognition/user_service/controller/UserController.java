package com.faceRecognition.user_service.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.user_service.service.UserService;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	
	@PostMapping
	public ResponseEntity<String> retrieve(@RequestParam(value = "file", required = true) String image) {
		try {
			return new ResponseEntity<String>(this.userService.retrieve(image), HttpStatus.OK);
		} catch ( NotFoundException e) {
			return new ResponseEntity<String>("Not Found: " + e.getMessage(), HttpStatus.NOT_FOUND);
		} catch ( FaceClientException e ){
			return new ResponseEntity<String>("FaceClientException: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch ( FaceServerException e ){
			return new ResponseEntity<String>("FaceServerException: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch ( UnirestException e ){
			return new ResponseEntity<String>("UnirestException: " + e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		} catch ( JSONException e ){
			return new ResponseEntity<String>("JSONException: " + e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
}