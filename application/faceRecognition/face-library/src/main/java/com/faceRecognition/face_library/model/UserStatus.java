package com.faceRecognition.face_library.model;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class UserStatus {
	private final String uid;
	private final int training_set_size;
	private final long last_trained;
	private final boolean training_in_progress;

	public UserStatus(JSONObject jObj) throws JSONException {
		this.uid = jObj.getString("uid");
		this.training_set_size = jObj.getInt("training_set_size");
		this.last_trained = jObj.getLong("last_trained");
		this.training_in_progress = jObj.getBoolean("training_in_progress");
	}

	public String getUID() {
		return this.uid;
	}

	public int setSize() {
		return this.training_set_size;
	}

	public boolean isInProgress() {
		return this.training_in_progress;
	}

	public Date getLastTrained() {
		return new Date(this.last_trained);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserStatus [last_trained=").append(this.last_trained).append(", training_in_progress=")
				.append(this.training_in_progress).append(", training_set_size=").append(this.training_set_size)
				.append(", uid=").append(this.uid).append("]");
		return builder.toString();
	}
}
