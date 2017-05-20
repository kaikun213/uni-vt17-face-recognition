package com.faceRecognition.user_service.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

public interface UserService {

	String retrieve(String id) throws NotFoundException, FaceClientException, FaceServerException;
}