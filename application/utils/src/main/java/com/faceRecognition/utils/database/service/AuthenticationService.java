package com.faceRecognition.utils.database.service;

public interface AuthenticationService {

	boolean isValidCredentials(String username, String password);
}
