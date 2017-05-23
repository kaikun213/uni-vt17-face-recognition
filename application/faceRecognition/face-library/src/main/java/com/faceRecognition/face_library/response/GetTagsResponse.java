package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.Photo;

public abstract interface GetTagsResponse {
	public abstract List<Photo> getPhotos();
}
