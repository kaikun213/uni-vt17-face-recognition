package com.faceRecognition.utils_service.database.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface UserDBService {

	String getPersonalNumber(String id) throws NotFoundException;
}