package com.faceRecognition.face_library.model;

import org.json.JSONException;
import org.json.JSONObject;

public class SavedTag {
	private final String tid;
	private String detected_tid;

	public SavedTag(JSONObject jObj) throws JSONException {
		this.tid = jObj.getString("tid");
		this.detected_tid = jObj.optString("detected_tid");
	}

	public String getTID() {
		return this.tid;
	}

	public String getDetectedTID() {
		return this.detected_tid;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("tid : ").append(this.tid).append("\n").append("detected_tid: ").append(this.detected_tid)
				.append("\n").trimToSize();

		return sb.toString();
	}
}
