package com.faceRecognition.face_library;

import java.io.File;
import java.net.URI;
import java.util.List;
import org.apache.http.NameValuePair;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;

public abstract interface Responder {
	public abstract String doGet(URI paramURI) throws FaceClientException, FaceServerException;

	public abstract String doPost(URI paramURI, List<NameValuePair> paramList)
			throws FaceClientException, FaceServerException;

	public abstract String doPost(File paramFile, URI paramURI, List<NameValuePair> paramList)
			throws FaceClientException, FaceServerException;
}
