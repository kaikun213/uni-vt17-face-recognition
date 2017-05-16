package com.faceRecognition.admin.service;

import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.faceRecognition.utils.database.model.UserEntity;

// TODO: add storage/face Service, add pagination support, add database returns userEntity
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	com.faceRecognition.utils.database.service.AdminService databaseService;

	@Override
	public UserEntity create(MultipartFile file, String personalNumber) throws InvalidAttributeValueException {
		String link = "linkToFile";		// link = storageService.store(file);
		String faceId = "1";			// faceId = faceService.save(link)	
		UserEntity userEntity = null; 	// database should return result
		databaseService.addUserEntity(faceId, personalNumber);
		return userEntity;
	}

	@Override
	public UserEntity retrieve(String id) throws NotFoundException {
		return databaseService.getUserEntity(id);
	}

	@Override
	public UserEntity update(MultipartFile file, String id, String personalNumber) throws NotFoundException {
		String link = "linkToFile";			// link = storageService.store(file);
		// faceService.update(id, link);
		UserEntity userEntity = null;		// database should return result	
		databaseService.updateUserEntity(id, personalNumber);
		String faceId = "1";				// faceId = faceService.save(link)
		return userEntity;
	}

	@Override
	public void delete(String id) throws NotFoundException {
		// faceService.delete(id);
		databaseService.deleteUserEntity(id);
	}

	@Override
	public List<UserEntity> list(int size, int page) {
		List<UserEntity> userEntities = databaseService.getUserEntities();	// page and size
		return userEntities;
	}

}