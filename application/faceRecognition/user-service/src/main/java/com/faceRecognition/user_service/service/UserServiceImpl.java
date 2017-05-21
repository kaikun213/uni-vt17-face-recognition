package com.faceRecognition.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.faceRecognition.utils_service.database.service.UserDBService;
import com.faceRecognition.utils_service.face.service.UserFaceService;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

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