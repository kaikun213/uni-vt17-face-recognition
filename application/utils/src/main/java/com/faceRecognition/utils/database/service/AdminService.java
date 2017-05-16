package com.faceRecognition.utils.database.service;

import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import com.faceRecognition.utils.database.model.UserEntity;

public interface AdminService {
	
	void addUserEntity(String id, String personalNumber) throws InvalidAttributeValueException;
	
	void updateUserEntity(String id, String personalNumber) throws NotFoundException;
	
	void deleteUserEntity(String id) throws NotFoundException;
	
	UserEntity getUserEntity(String id) throws NotFoundException;
	
	List<UserEntity> getUserEntities();
}