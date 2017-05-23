package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.Group;
import com.faceRecognition.face_library.model.Photo;

public final class GroupResponseImpl extends AbstractResponse implements GroupResponse {
	private final List<Group> groups;
	private final List<Photo> photos;

	public GroupResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.groups = ResponseHelper.toGroupList(this.response.getJSONArray("groups"));
			this.photos = ResponseHelper.toPhotoList(this.response.getJSONArray("photos"));
		} catch (JSONException jex) {
			throw new FaceClientException(jex);
		}
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public List<Photo> getPhotos() {
		return this.photos;
	}

	public String toString() {
		return super.toString();
	}
}
