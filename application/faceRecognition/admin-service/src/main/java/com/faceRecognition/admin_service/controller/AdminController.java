package com.faceRecognition.admin_service.controller;

import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faceRecognition.admin_service.service.AdminService;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.utils_service.database.model.UserEntity;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping
	public ResponseEntity create(@RequestParam(value = "file", required = true) String file,
			@RequestParam(value = "personalNumber", required = true) String personalNumber) {
		try {
			UserEntity userEntity = adminService.create(file, personalNumber);
			return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
		} catch (InvalidAttributeValueException e) {
			return new ResponseEntity<String>("Invalid Attribute Value. (Personal Number incorrect?)", HttpStatus.BAD_REQUEST);
		} catch (UnirestException e) {
			return  new ResponseEntity<String>("Storage Exception. ", HttpStatus.FAILED_DEPENDENCY);
		} catch (FaceClientException e) {
			return  new ResponseEntity<String>("FaceClient Error:",  HttpStatus.FAILED_DEPENDENCY);
		} catch (FaceServerException e) {
			return  new ResponseEntity<String>("FaceServer Error:",  HttpStatus.FAILED_DEPENDENCY);
		} catch (JSONException e) {
			return  new ResponseEntity<String>("JSON Error:",  HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity retrieve(@PathVariable String id) {
		try {
			UserEntity userEntity = adminService.retrieve(id);
			return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
		} catch (NotFoundException e) {
			return  new ResponseEntity<String>("Not Found. ",  HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@RequestParam(value = "file", required = true) String file,
			@RequestParam(value = "personalNumber", required = true) String personalNumber, @PathVariable String id) {
		try {
			UserEntity userEntity = adminService.update(file, id, personalNumber);
			return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
		} catch (UnirestException e) {
			return  new ResponseEntity<String>("Storage Exception. ", HttpStatus.FAILED_DEPENDENCY);
		} catch (FaceClientException e) {
			return  new ResponseEntity<String>("FaceClient Error:",  HttpStatus.FAILED_DEPENDENCY);
		} catch (FaceServerException e) {
			return  new ResponseEntity<String>("FaceServer Error:",  HttpStatus.FAILED_DEPENDENCY);
		} catch (JSONException e) {
			return  new ResponseEntity<String>("JSON Error:",  HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return  new ResponseEntity<String>("Not Found. ",  HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		try {
			adminService.delete(id);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (NotFoundException e) {
			return  new ResponseEntity<String>("Not Found. ",  HttpStatus.NOT_FOUND);
		} catch (FaceClientException e) {
			return  new ResponseEntity<String>("FaceClient Error:",  HttpStatus.FAILED_DEPENDENCY);
		} catch (FaceServerException e) {
			return  new ResponseEntity<String>("FaceServer Error:",  HttpStatus.FAILED_DEPENDENCY);
		}
	}

	@GetMapping
	public ResponseEntity<List<UserEntity>> list(@RequestParam(value = "size", required = false, defaultValue = "20") int size,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		return new ResponseEntity<List<UserEntity>>(adminService.list(size, page), HttpStatus.OK);
	}
}