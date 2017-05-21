package com.faceRecognition.admin_service.controller;

import javax.naming.directory.InvalidAttributeValueException;
import org.json.JSONException;
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
import com.faceRecognition.admin_service.api.ApiResponse;
import com.faceRecognition.admin_service.api.ApiResponse.ApiError;
import com.faceRecognition.admin_service.api.ApiResponse.Status;
import com.faceRecognition.admin_service.api.ListApiResponse;
import com.faceRecognition.admin_service.service.AdminService;
import com.faceRecognition.utils_service.database.model.UserEntity;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping
	public ApiResponse create(@RequestParam(value = "file", required = true) String file,
			@RequestParam(value = "personalNumber", required = true) String personalNumber) {
		try {
			UserEntity userEntity = adminService.create(file, personalNumber);
			return new ApiResponse(Status.OK, userEntity);
		} catch (InvalidAttributeValueException e) {
			return new ApiResponse(Status.ERROR, null,
					new ApiError(2, "Invalid Attribute Value. (Personal Number incorrect?)"));
		} catch (UnirestException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(3, "Storage Exception. " + e.getMessage()));
		} catch (FaceClientException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(4, "FaceClient Error:" + e.getMessage()));
		} catch (FaceServerException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(5, "FaceServer Error:" + e.getMessage()));
		} catch (JSONException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(5, "JSON Error:" + e.getMessage()));
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
	public ApiResponse update(@RequestParam(value = "file", required = true) String file,
			@RequestParam(value = "personalNumber", required = true) String personalNumber, @PathVariable String id) {
		try {
			UserEntity userEntity = adminService.update(file, id, personalNumber);
			return new ApiResponse(Status.OK, userEntity);
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(1, "Entity not found"));
		} catch (UnirestException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(3, "Storage Exception." + e.getMessage()));
		} catch (FaceClientException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(4, "FaceClient Error:" + e.getMessage()));
		} catch (FaceServerException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(5, "FaceServer Error:" + e.getMessage()));
		} catch (JSONException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(5, "JSON Error:" + e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable String id) {
		try {
			adminService.delete(id);
			return new ApiResponse(Status.OK, null, null);
		} catch (NotFoundException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(1, "Entity not found."));
		} catch (FaceClientException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(4, "FaceClient Error:" + e.getMessage()));
		} catch (FaceServerException e) {
			return new ApiResponse(Status.ERROR, null, new ApiError(5, "FaceServer Error:" + e.getMessage()));
		}
	}

	@GetMapping
	public ListApiResponse list(@RequestParam(value = "size", required = false, defaultValue="20") int size, @RequestParam(value = "page", required = false, defaultValue="1") int page) {
		return new ListApiResponse(Status.OK, adminService.list(size, page), null, page, "nextPage", new Long(1));
	}
}