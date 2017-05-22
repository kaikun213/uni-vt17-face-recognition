package com.faceRecognition.face_library.response;

import java.util.List;
import org.json.JSONException;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.model.Namespace;

public class NamespaceResponseImpl extends AbstractResponse implements NamespaceResponse {
	private final List<Namespace> namespaces;

	public NamespaceResponseImpl(String json) throws FaceClientException {
		super(json);
		try {
			this.namespaces = ResponseHelper.toNamespaceList(this.response.getJSONArray("namespaces"));
		} catch (JSONException jex) {
			logger.error("Error: ", jex);
			throw new FaceClientException(jex);
		}
	}

	public List<Namespace> getNamespaces() {
		return this.namespaces;
	}
}
