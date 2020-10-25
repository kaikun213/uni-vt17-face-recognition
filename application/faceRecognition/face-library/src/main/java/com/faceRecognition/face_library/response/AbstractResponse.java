package com.faceRecognition.face_library.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.faceRecognition.face_library.exception.FaceClientException;

abstract class AbstractResponse {
	private static final int NUM_TABS = 2;
	protected static final Logger logger = LoggerFactory.getLogger(AbstractResponse.class);
	protected final JSONObject response;

	protected AbstractResponse(String json) throws FaceClientException {
		try {
			this.response = new JSONObject(json);
			if (logger.isDebugEnabled()) {
				logger.debug(toString());
			}
		} catch (JSONException jex) {
			logger.debug("Caught exception: ", jex.getMessage(), jex);
			throw new FaceClientException(jex);
		}
	}

	public String toString() {
		try {
			return this.response.toString(2);
		} catch (JSONException e) {
		}
		return null;
	}
}
