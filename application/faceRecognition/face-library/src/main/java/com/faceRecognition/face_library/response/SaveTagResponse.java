package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.SavedTag;

public abstract interface SaveTagResponse {
	public abstract List<SavedTag> getSavedTags();
}
