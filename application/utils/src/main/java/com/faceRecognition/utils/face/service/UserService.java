package com.faceRecognition.utils.face.service;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

public interface UserService {
	/** Matches the photo from the given link with a photo from the database
	 * 
	 * @param link to the photo to compare with
	 * @return returns the ID with the matched photo or null if no match occoured
	 * @throws FaceClientException 
	 * @throws FaceServerException
	 */
	String match(String link) throws FaceClientException, FaceServerException;
}
