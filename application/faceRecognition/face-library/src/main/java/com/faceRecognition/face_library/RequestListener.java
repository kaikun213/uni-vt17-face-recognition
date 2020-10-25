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

public abstract interface RequestListener {
	public abstract void onDetect(Photo paramPhoto);

	public abstract void onDetect(List<Photo> paramList);

	public abstract void onRecognize(Photo paramPhoto);

	public abstract void onRecognize(List<Photo> paramList);

	public abstract void onAddTag();

	public abstract void onGetTags(List<Photo> paramList);

	public abstract void onSaveTags(List<SavedTag> paramList);

	public abstract void onStatus(List<UserStatus> paramList);

	public abstract void onFacebookGet(List<Photo> paramList);

	public abstract void onGroup(GroupResponse paramGroupResponse);

	public abstract void onGetNamespaces(List<Namespace> paramList);

	public abstract void onGetNamespace(Namespace paramNamespace);

	public abstract void onUsers(UsersResponse paramUsersResponse);

	public abstract void onGetLimits(LimitsResponse paramLimitsResponse);

	public abstract void onFaceClientException(FaceClientException paramFaceClientException, FaceApi paramFaceApi);

	public abstract void onFaceServerException(FaceServerException paramFaceServerException, FaceApi paramFaceApi);

	public abstract void onRemoveTags(List<RemovedTag> paramList);

	public abstract void onTrain(TrainResponse paramTrainResponse);
}
