package com.faceRecognition.face_library.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RemovedTag {
	private String removed_tid;
	private String detected_tid;

	public RemovedTag(JSONObject jObj) throws JSONException {
		this.removed_tid = jObj.getString("removed_tid");
		this.detected_tid = jObj.getString("tid");
	}

	public String getRemovedTID() {
		return this.removed_tid;
	}

	public String getDetectedTID() {
		return this.detected_tid;
	}
}
