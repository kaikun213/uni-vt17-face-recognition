package com.faceRecognition.face_library.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.faceRecognition.face_library.exception.FaceClientException;

public class UsersResponseImpl extends AbstractResponse implements UsersResponse {
	private final Map<String, List<String>> userNamespaceMap;

	public UsersResponseImpl(String json, String namespaces) throws FaceClientException {
		super(json);
		try {
			this.userNamespaceMap = new HashMap();
			JSONObject jObj = this.response.getJSONObject("users");
			for (String namespace : namespaces.split(",")) {
				this.userNamespaceMap.put(namespace, ResponseHelper.toStringList(jObj.getJSONArray(namespace)));
			}
		} catch (JSONException jex) {
			logger.error("Error: ", jex);
			throw new FaceClientException(jex);
		}
	}

	public List<String> getUsers(String namespace) {
		return (List) this.userNamespaceMap.get(namespace);
	}

	public String toString() {
		return this.userNamespaceMap.toString();
	}
}
