package com.faceRecognition.utils.database.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface UserService {

	String getPersonalNumber(String id) throws NotFoundException;
}