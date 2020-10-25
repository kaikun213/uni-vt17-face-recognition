package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.RemovedTag;

public abstract interface RemoveTagResponse {
	public abstract List<RemovedTag> getRemovedTags();
}
