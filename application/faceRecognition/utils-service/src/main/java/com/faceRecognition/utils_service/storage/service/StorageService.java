package com.faceRecognition.utils_service.storage.service;

import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface StorageService {
	/** Delete the image afterwards again
	 * 
	 * @param url
	 * @throws UnirestException
	 */
	void delete(String url) throws UnirestException;
	
	/** Uploads the image and returns the link
	 * 
	 * @param file in base64 String format to upload
	 * @return link
	 * @throws UnirestException
	 * @throws JSONException
	 */

	String store(String file) throws UnirestException, JSONException;
}