package com.faceRecognition.face_library.exception;

public class FaceServerException extends FaceAPIException {
	private static final long serialVersionUID = 1L;
	private final int errorCode;

	public FaceServerException(String msg, int errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
}