package com.faceRecognition.utils_service.face.service;

import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;

public interface UserFaceService {
	/**
	 * Matches the photo from the given link with a photo from the database
	 * 
	 * @param link
	 *            to the photo to compare with
	 * @return returns the ID with the matched photo or null if no match
	 *         occoured
	 * @throws FaceClientException
	 * @throws FaceServerException
	 */
	String match(String link) throws FaceClientException, FaceServerException;
}