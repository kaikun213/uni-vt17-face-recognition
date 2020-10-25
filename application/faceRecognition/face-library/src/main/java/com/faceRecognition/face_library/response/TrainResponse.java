package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.UserStatus;

public abstract interface TrainResponse {
	public abstract List<UserStatus> getCreated();

	public abstract List<UserStatus> getNoTrainingSet();

	public abstract List<UserStatus> getUpdated();

	public abstract List<UserStatus> getUnchanged();

	public abstract List<UserStatus> getInProgress();

	public abstract String toString();
}
