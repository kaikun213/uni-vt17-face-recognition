package com.faceRecognition.face_library.response;

import java.util.List;

public abstract interface UsersResponse {
	public abstract List<String> getUsers(String paramString);
}
