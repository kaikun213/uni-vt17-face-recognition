package com.faceRecognition.admin.controller;

import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faceRecognition.admin.api.ApiResponse;
import com.faceRecognition.admin.api.ApiResponse.ApiError;
import com.faceRecognition.admin.api.ApiResponse.Status;
import com.faceRecognition.admin.api.ListApiResponse;
import com.faceRecognition.admin.service.AdminService;
import com.faceRecognition.utils.database.model.UserEntity;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping
	public ApiResponse create(@RequestParam(value = "file", required=true) MultipartFile file,
			  				  @RequestParam(value = "personalNumber", required=true) String personalNumber){
		try {
			UserEntity userEntity = adminService.create(file, personalNumber);
			return new ApiResponse(Status.OK, userEntity);
		} catch (InvalidAttributeValueException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(2, "Invalid Attribute Value. (Personal Number incorrect?)"));
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ApiResponse retrieve(@PathVariable String id) {
		try {
			UserEntity userEntity = adminService.retrieve(id);
			return new ApiResponse(Status.OK, userEntity);
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(404, "Not Found"));
		}
	}
	
	@PutMapping("/{id}")
	public ApiResponse update(@RequestParam(value = "file", required=true) MultipartFile file,
			  				  @RequestParam(value = "personalNumber", required=true) String personalNumber,
			  				  @PathVariable String id){
		try {
			UserEntity userEntity = adminService.update(file, id, personalNumber);
			return new ApiResponse(Status.OK, userEntity);
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(1, "Entity not found"));
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable String id){
		try {
			adminService.delete(id);
			return new ApiResponse(Status.OK, null, null);
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(1, "Entity not found."));
		}
	}
	
	@GetMapping("/")
	public ListApiResponse list(@PathVariable int size, @PathVariable int page){
		return new ListApiResponse(Status.OK, adminService.list(size, page),null, page, "nextPage", new Long(1));
	}


}