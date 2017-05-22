package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.UserStatus;

public abstract interface StatusResponse {
	public abstract List<UserStatus> getTrainingStatus();
}
