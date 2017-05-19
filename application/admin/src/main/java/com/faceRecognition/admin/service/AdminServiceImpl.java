package com.faceRecognition.admin.service;

import java.io.IOException;
import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.storage.service.StorageService;

// TODO: add storage/face Service, add pagination support, add database returns userEntity
public class AdminServiceImpl implements AdminService {

	@Autowired
	com.faceRecognition.utils.database.service.AdminService databaseService;

	@Autowired
	private StorageService storageService;

	@Override
	public UserEntity create(MultipartFile file, String personalNumber) throws InvalidAttributeValueException, IOException {
		String link = storageService.upload(file);
		String faceId = "1"; // faceId = faceService.save(link)
		UserEntity userEntity = databaseService.addUserEntity(personalNumber, link);
		return userEntity;
	}

	@Override
	public UserEntity retrieve(String id) throws NotFoundException {
		return databaseService.getUserEntity(id);
	}

	@Override
	public UserEntity update(MultipartFile file, String id, String personalNumber) throws NotFoundException, IOException {
		String link = storageService.upload(file);
		// faceService.update(id, link);
		UserEntity userEntity = databaseService.updateUserEntity(id, personalNumber, link);
		String faceId = "1"; // faceId = faceService.save(link)
		return userEntity;
	}

	@Override
	public void delete(String id) throws NotFoundException {
		// faceService.delete(id);
		databaseService.deleteUserEntity(id);
	}

	@Override
	public List<UserEntity> list(int size, int page) {
		List<UserEntity> userEntities = databaseService.getUserEntities(); // page
																			// and
																			// size
		return userEntities;
	}
}