package com.faceRecognition.utils_service.storage.service;

import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface StorageService {
	
	void delete(String url) throws UnirestException;

	String store(String file) throws UnirestException, JSONException;
}