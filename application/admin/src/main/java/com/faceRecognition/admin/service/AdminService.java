package com.faceRecognition.admin.service;

import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.faceRecognition.utils.database.model.UserEntity;

public interface AdminService {
	
	UserEntity create(MultipartFile file, String personalNumber) throws InvalidAttributeValueException;
	UserEntity retrieve(String id) throws NotFoundException;
	UserEntity update(MultipartFile file, String id, String personalNumber) throws NotFoundException;
	void delete(String id) throws NotFoundException;
	List<UserEntity> list(int size, int page);

}
