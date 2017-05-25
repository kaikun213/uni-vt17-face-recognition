package com.faceRecognition.admin_service.service;

import javax.naming.directory.InvalidAttributeValueException;

import org.json.JSONException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.utils_service.database.model.UserEntity;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface AdminService {

	UserEntity create(String file, String personalNumber) throws InvalidAttributeValueException, UnirestException,
			FaceClientException, FaceServerException, JSONException;

	UserEntity retrieve(String id) throws NotFoundException;

	UserEntity update(String file, String id, String personalNumber)
			throws NotFoundException, UnirestException, FaceClientException, FaceServerException, JSONException;

	void delete(String id) throws NotFoundException, FaceClientException, FaceServerException;

	Page<UserEntity> list(PageRequest request);
}