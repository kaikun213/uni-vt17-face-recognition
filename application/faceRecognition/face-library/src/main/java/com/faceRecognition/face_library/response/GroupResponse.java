package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.Group;
import com.faceRecognition.face_library.model.Photo;

public abstract interface GroupResponse {
	public abstract List<Group> getGroups();

	public abstract List<Photo> getPhotos();
}
