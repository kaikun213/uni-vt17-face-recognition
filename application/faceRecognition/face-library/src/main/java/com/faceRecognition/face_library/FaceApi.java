package com.faceRecognition.face_library;

public enum FaceApi {
	RECOGNIZE("/fc/faces/recognize.json", true), DETECT("/fc/faces/detect.json", false), GROUP("/fc/faces/group.json",
			true), TRAIN("/fc/faces/train.json", true), STATUS("/fc/faces/status.json", true), REMOVE_TAGS(
					"/fc/tags/remove.json", true), SAVE_TAGS("/fc/tags/save.json", true), GET_TAGS("/fc/tags/get.json",
							true), ADD_TAG("/fc/tags/add.json", true), LIMITS("/fc/account/limits.json",
									false), NAMESPACES("/fc/account/namespaces.json", false), USERS(
											"/fc/account/users.json", false), FACEBOOK("/fc/facebook/get.json", true);

	private final String path;
	private final boolean takesAuth;

	private FaceApi(String path, boolean takesAuth) {
		this.path = path;
		this.takesAuth = takesAuth;
	}

	public String getPath() {
		return this.path;
	}

	public boolean takesAuth() {
		return this.takesAuth;
	}
}
