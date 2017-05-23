package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.Photo;

public abstract interface PhotoResponse {
	public abstract List<Photo> getPhotos();

	public abstract Photo getPhoto();
}
