package com.faceRecognition.face_library.response;

import java.util.Date;

public abstract interface LimitsResponse {
	public abstract int getUsed();

	public abstract int getRemaining();

	public abstract int getLimit();

	public abstract int getNamespaceLimit();

	public abstract int getNamespaceUsed();

	public abstract int getNamespaceRemaining();

	public abstract String getRestTimeString();

	public abstract Date getResetDate();
}
