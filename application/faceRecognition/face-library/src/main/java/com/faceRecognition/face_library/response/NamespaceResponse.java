package com.faceRecognition.face_library.response;

import java.util.List;
import com.faceRecognition.face_library.model.Namespace;

public abstract interface NamespaceResponse {
	public abstract List<Namespace> getNamespaces();
}
