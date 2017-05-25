package com.faceRecognition.utils_service.database.service;

import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.faceRecognition.utils_service.database.model.UserEntity;

public interface AdminDBService {

	UserEntity addUserEntity(String url, String personalNumber) throws InvalidAttributeValueException;

	UserEntity updateUserEntity(String id, String personalNumber, String url) throws NotFoundException;

	void deleteUserEntity(String id) throws NotFoundException;

	UserEntity getUserEntity(String id) throws NotFoundException;

	Page<UserEntity> getUserEntities(PageRequest request);
}