package com.faceRecognition.admin.service;

import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.faceRecognition.utils.database.model.UserEntity;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface AdminService {
	
	UserEntity create(String file, String personalNumber) throws InvalidAttributeValueException, UnirestException, FaceClientException, FaceServerException;
	UserEntity retrieve(String id) throws NotFoundException;
	UserEntity update(String file, String id, String personalNumber) throws NotFoundException, UnirestException, FaceClientException, FaceServerException;
	void delete(String id) throws NotFoundException, FaceClientException, FaceServerException;
	List<UserEntity> list(int size, int page);

}
