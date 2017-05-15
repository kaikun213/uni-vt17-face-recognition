package com.faceRecognition.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceRecognition.admin.api.ApiResponse;
import com.faceRecognition.admin.api.ApiResponse.Status;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService databaseService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ApiResponse create(@RequestParam(value = "file", required=true) MultipartFile file,
			  				  @RequestParam(value = "personalNumber", required=true) String personalNumber){
		String link = "linkToFile";		// link = storageService.store(file);
		String faceId = "1";			// faceId = faceService.save(link)	
		UserEntity userEntity = null; // database should return result
		databaseService.addUserEntity(faceId, personalNumber);
		return new ApiResponse(Status.OK, userEntity);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ApiResponse retrieve(@PathVariable String id){
		UserEntity userEntity = databaseService.getUserEntity(id);
		return new ApiResponse(Status.OK, userEntity);
	}


}
