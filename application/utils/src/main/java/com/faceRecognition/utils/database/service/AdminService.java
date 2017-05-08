package com.faceRecognition.utils.database.service;

import java.util.List;
import com.faceRecognition.utils.database.model.UserEntity;

public interface AdminService {
	
	void addUserEntity(Long personalNumber, String photoLink);
	
	void updateUserEntity(Long id, Long personalNumber, String photoLink);
	
	void deleteUserEntity(Long id);
	
	List<UserEntity> getUserEntities();
}