package com.faceRecognition.face_library.exception;

public abstract class FaceAPIException extends Exception {
	
	public FaceAPIException(Throwable cause) {
		super(cause);
	}

	public FaceAPIException(String errMsg) {
		super(errMsg);
	}
}