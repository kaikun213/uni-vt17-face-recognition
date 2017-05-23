package com.faceRecognition.face_library;

import java.util.List;

import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.face_library.model.Namespace;
import com.faceRecognition.face_library.model.Photo;
import com.faceRecognition.face_library.model.RemovedTag;
import com.faceRecognition.face_library.model.SavedTag;
import com.faceRecognition.face_library.model.UserStatus;
import com.faceRecognition.face_library.response.GroupResponse;
import com.faceRecognition.face_library.response.LimitsResponse;
import com.faceRecognition.face_library.response.TrainResponse;
import com.faceRecognition.face_library.response.UsersResponse;

public class AsyncAdapter implements RequestListener {
	public void onAddTag() {
	}

	public void onDetect(Photo photo) {
	}

	public void onDetect(List<Photo> photos) {
	}

	public void onFaceClientException(FaceClientException fce, FaceApi faceApi) {
	}

	public void onFaceServerException(FaceServerException fse, FaceApi faceApi) {
	}

	public void onFacebookGet(List<Photo> photos) {
	}

	public void onGetLimits(LimitsResponse response) {
	}

	public void onGetNamespace(Namespace namespace) {
	}

	public void onGetNamespaces(List<Namespace> namespaces) {
	}

	public void onGetTags(List<Photo> photos) {
	}

	public void onGroup(GroupResponse response) {
	}

	public void onRecognize(Photo photo) {
	}

	public void onRecognize(List<Photo> photos) {
	}

	public void onRemoveTags(List<RemovedTag> removedTags) {
	}

	public void onSaveTags(List<SavedTag> savedTags) {
	}

	public void onStatus(List<UserStatus> userStatus) {
	}

	public void onTrain(TrainResponse response) {
	}

	public void onUsers(UsersResponse response) {
	}
}
