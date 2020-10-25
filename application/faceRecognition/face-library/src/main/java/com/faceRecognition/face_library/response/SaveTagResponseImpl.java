package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.SavedTag;

public class SaveTagResponseImpl extends AbstractResponse implements SaveTagResponse {
	private final List<SavedTag> tags;

	public SaveTagResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.tags = ResponseHelper.toSavedTagList(this.response.getJSONArray("saved_tags"));
		} catch (JSONException jex) {
			logger.error("Error getting saved_tags: " + jex.getMessage(), jex);
			throw new FaceClientException(jex);
		}
	}

	public List<SavedTag> getSavedTags() {
		return this.tags;
	}

	public String toString() {
		return super.toString();
	}
}
