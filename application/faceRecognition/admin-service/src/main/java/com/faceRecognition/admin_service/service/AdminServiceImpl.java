package com.faceRecognition.admin_service.service;

import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.faceRecognition.utils_service.database.model.UserEntity;
import com.faceRecognition.utils_service.database.service.AdminDBService;
import com.faceRecognition.utils_service.face.service.AdminFaceService;
import com.faceRecognition.utils_service.storage.service.StorageService;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDBService databaseService;

	@Autowired
	private AdminFaceService faceService;

	@Autowired
	private StorageService storageService;

	@Override
	public UserEntity create(String file, String personalNumber) throws InvalidAttributeValueException,
			UnirestException, FaceClientException, FaceServerException, JSONException {
		String url = storageService.store(file);
		UserEntity userEntity = databaseService.addUserEntity(url, personalNumber);
		faceService.create("" + userEntity.getId(), url);
		return userEntity;
	}

	@Override
	public UserEntity retrieve(String id) throws NotFoundException {
		return databaseService.getUserEntity(id);
	}

	@Override
	public UserEntity update(String file, String id, String personalNumber)
			throws NotFoundException, UnirestException, FaceClientException, FaceServerException, JSONException {
		String url = storageService.store(file);
		UserEntity userEntity = databaseService.updateUserEntity(id, personalNumber, url);
		faceService.update("" + userEntity.getId(), url);
		return userEntity;
	}

	@Override
	public void delete(String id) throws NotFoundException, FaceClientException, FaceServerException {
		faceService.delete(id);
		databaseService.deleteUserEntity(id);
	}

	@Override
	public List<UserEntity> list(int size, int page) {
		// Page and size
		List<UserEntity> userEntities = databaseService.getUserEntities();
		return userEntities;
	}
}