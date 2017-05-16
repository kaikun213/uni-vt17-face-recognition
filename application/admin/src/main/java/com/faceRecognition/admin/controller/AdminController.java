package com.faceRecognition.admin.controller;

import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.faceRecognition.admin.api.ApiResponse;
import com.faceRecognition.admin.api.ApiResponse.ApiError;
import com.faceRecognition.admin.api.ApiResponse.Status;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService databaseService;

	@RequestMapping(method = RequestMethod.POST)
	public ApiResponse create(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "personalNumber", required = true) String personalNumber) {

		try {
			String link = "linkToFile"; // link = storageService.store(file);
			String faceId = "1"; // faceId = faceService.save(link)
			UserEntity userEntity = null; // database should return result
			databaseService.addUserEntity(faceId, personalNumber);
			return new ApiResponse(Status.OK, userEntity);
		} catch (InvalidAttributeValueException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(400, "Not Found"));
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ApiResponse retrieve(@PathVariable String id) {
		try {
			return new ApiResponse(Status.OK, databaseService.getUserEntity(id));
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(404, "Not Found"));
		}
	}
}