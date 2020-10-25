package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.UserStatus;

public final class StatusResponseImpl extends AbstractResponse implements StatusResponse {
	private final List<UserStatus> user_status;

	public StatusResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.user_status = ResponseHelper.toUserStatusList(this.response.getJSONArray("user_statuses"));
		} catch (JSONException jex) {
			logger.error("Error adaption user_status: " + jex.getMessage(), jex);

			throw new FaceClientException(jex);
		}
	}

	public List<UserStatus> getTrainingStatus() {
		return this.user_status;
	}

	public String toString() {
		return super.toString();
	}
}
