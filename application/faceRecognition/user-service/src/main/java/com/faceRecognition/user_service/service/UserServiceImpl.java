package com.faceRecognition.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.utils_service.database.service.UserDBService;
import com.faceRecognition.utils_service.face.service.UserFaceService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDBService databaseService;

	@Autowired
	private UserFaceService faceService;

	@Override
	public String retrieve(String id) throws NotFoundException, FaceClientException, FaceServerException {
		if (faceService.match(id) != null) {
			return databaseService.getPersonalNumber(id);
		}
		throw new NotFoundException();
	}
}