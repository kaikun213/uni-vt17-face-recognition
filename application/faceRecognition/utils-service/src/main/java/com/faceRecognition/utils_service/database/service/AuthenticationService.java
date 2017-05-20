package com.faceRecognition.utils_service.database.service;

public interface AuthenticationService {

	boolean isValidCredentials(String username, String password);
}
