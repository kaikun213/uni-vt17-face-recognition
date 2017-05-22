package com.faceRecognition.face_library.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Namespace {
	private final String shareMode;
	private final String name;
	private final boolean isOwner;
	private final int size;

	public Namespace(JSONObject jObj) throws JSONException {
		this.shareMode = jObj.getString("share_mode");
		this.isOwner = jObj.getBoolean("owner");
		this.size = jObj.getInt("size");
		this.name = jObj.getString("name");
	}

	public String getShareMode() {
		return this.shareMode;
	}

	public String getName() {
		return this.name;
	}

	public boolean isOwner() {
		return this.isOwner;
	}

	public int getSize() {
		return this.size;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Namespace [isOwner=").append(this.isOwner).append(", name=").append(this.name)
				.append(", shareMode=").append(this.shareMode).append(", size=").append(this.size).append("]");

		return builder.toString();
	}
}
