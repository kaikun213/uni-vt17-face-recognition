package com.faceRecognition.user_service.service;

import org.json.JSONException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface UserService {

	String retrieve(String image) throws NotFoundException, FaceClientException, FaceServerException, UnirestException, JSONException;
}