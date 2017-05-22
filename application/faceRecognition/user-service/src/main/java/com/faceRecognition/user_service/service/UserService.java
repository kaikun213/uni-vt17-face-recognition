package com.faceRecognition.user_service.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;

public interface UserService {

	String retrieve(String id) throws NotFoundException, FaceClientException, FaceServerException;
}