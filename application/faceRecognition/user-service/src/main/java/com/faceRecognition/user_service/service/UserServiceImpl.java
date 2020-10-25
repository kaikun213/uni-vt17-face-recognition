package com.faceRecognition.user_service.service;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.utils_service.database.service.UserDBService;
import com.faceRecognition.utils_service.face.service.UserFaceService;
import com.faceRecognition.utils_service.storage.service.StorageService;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDBService databaseService;

	@Autowired
	private UserFaceService faceService;
	
	@Autowired
	private StorageService storageService;

	@Override
	public String retrieve(String image) throws NotFoundException, FaceClientException, FaceServerException, UnirestException, JSONException {
		String tempLink = storageService.store(image);
		String id = faceService.match(tempLink);
		if (id == null) {
			throw new NotFoundException();
		}
		// delete tmp upload again
		storageService.delete(tempLink);
		return databaseService.getPersonalNumber(id);
	}
}