package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.RemovedTag;

public final class RemoveTagResponseImpl extends AbstractResponse implements RemoveTagResponse {
	private final List<RemovedTag> removedTags;

	public RemoveTagResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.removedTags = ResponseHelper.toRemovedTagList(this.response.getJSONArray("removed_tags"));
		} catch (JSONException jex) {
			logger.error("Error geting removed_tags: " + jex.getMessage(), jex);

			throw new FaceClientException(jex);
		}
	}

	public List<RemovedTag> getRemovedTags() {
		return this.removedTags;
	}

	public String toString() {
		return super.toString();
	}
}
