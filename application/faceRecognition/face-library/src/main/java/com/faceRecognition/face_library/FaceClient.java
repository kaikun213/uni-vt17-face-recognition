package com.faceRecognition.face_library;

import java.io.File;
import java.io.Serializable;
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

public abstract interface FaceClient extends Serializable {
	public abstract Photo detect(File paramFile) throws FaceClientException, FaceServerException;

	public abstract List<Photo> detect(String paramString) throws FaceClientException, FaceServerException;

	public abstract Photo recognize(File paramFile, String paramString) throws FaceClientException, FaceServerException;

	public abstract List<Photo> recognize(String paramString1, String paramString2)
			throws FaceClientException, FaceServerException;

	public abstract GroupResponse group(File paramFile, String paramString)
			throws FaceClientException, FaceServerException;

	public abstract GroupResponse group(String paramString1, String paramString2)
			throws FaceClientException, FaceServerException;

	public abstract TrainResponse train(String paramString) throws FaceClientException, FaceServerException;

	public abstract List<UserStatus> status(String paramString) throws FaceClientException, FaceServerException;

	public abstract List<Photo> getTags(String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, boolean paramBoolean, int paramInt)
			throws FaceClientException, FaceServerException;

	public abstract List<Photo> getTags(String paramString1, String paramString2, String paramString3,
			String paramString4, boolean paramBoolean, int paramInt) throws FaceClientException, FaceServerException;

	public abstract void addTag(String paramString1, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2,
			String paramString2, String paramString3, String paramString4)
			throws FaceClientException, FaceServerException;

	public abstract List<SavedTag> saveTags(String paramString1, String paramString2, String paramString3)
			throws FaceClientException, FaceServerException;

	public abstract List<RemovedTag> removeTags(String paramString) throws FaceClientException, FaceServerException;

	public abstract List<Photo> facebookGet(String paramString) throws FaceClientException, FaceServerException;

	public abstract UsersResponse users(String paramString) throws FaceClientException, FaceServerException;

	public abstract LimitsResponse getLimits() throws FaceClientException, FaceServerException;

	public abstract List<Namespace> namespaces() throws FaceClientException, FaceServerException;

	public abstract Namespace getNamespace(String paramString) throws FaceClientException, FaceServerException;

	public abstract void setFacebookOauth2(String paramString1, String paramString2);

	public abstract void setTwitterOauth(String paramString1, String paramString2, String paramString3);

	public abstract void clearFacebookCreds();

	public abstract void clearTwitterCreds();

	public abstract void setAggressive(boolean paramBoolean);

	public abstract boolean isAggressive();
}
