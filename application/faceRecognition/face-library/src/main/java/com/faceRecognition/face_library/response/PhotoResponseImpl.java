package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.Photo;

public class PhotoResponseImpl extends AbstractResponse implements PhotoResponse {
	private final List<Photo> photos;

	public PhotoResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.photos = ResponseHelper.toPhotoList(this.response.getJSONArray("photos"));
		} catch (JSONException jex) {
			logger.error("Error getting photos: " + jex.getMessage(), jex);
			throw new FaceClientException(jex);
		}
	}

	public List<Photo> getPhotos() {
		return this.photos;
	}

	public Photo getPhoto() {
		try {
			return (Photo) this.photos.get(0);
		} catch (IndexOutOfBoundsException ioob) {
			if (logger.isInfoEnabled()) {
				logger.info("No photos...");
			}
		}
		return null;
	}

	public String toString() {
		return super.toString();
	}
}
