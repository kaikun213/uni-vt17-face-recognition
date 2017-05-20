package com.faceRecognition.utils_service.database.service;

import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import com.faceRecognition.utils_service.database.model.UserEntity;

public interface AdminDBService {

	UserEntity addUserEntity(String url, String personalNumber) throws InvalidAttributeValueException;

	UserEntity updateUserEntity(String id, String personalNumber, String url) throws NotFoundException;

	void deleteUserEntity(String id) throws NotFoundException;

	UserEntity getUserEntity(String id) throws NotFoundException;

	List<UserEntity> getUserEntities();
}