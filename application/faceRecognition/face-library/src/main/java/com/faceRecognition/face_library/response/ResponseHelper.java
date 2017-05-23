package com.faceRecognition.face_library.response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.faceRecognition.face_library.model.Group;
import com.faceRecognition.face_library.model.Namespace;
import com.faceRecognition.face_library.model.Photo;
import com.faceRecognition.face_library.model.RemovedTag;
import com.faceRecognition.face_library.model.SavedTag;
import com.faceRecognition.face_library.model.UserStatus;

final class ResponseHelper {
	private ResponseHelper() {
		throw new AssertionError();
	}

	static List<SavedTag> toSavedTagList(JSONArray jArr) throws JSONException {
		List<SavedTag> savedTags = new ArrayList();
		if (jArr != null) {
			for (int i = 0; i < jArr.length(); i++) {
				savedTags.add(new SavedTag(jArr.getJSONObject(i)));
			}
		}
		return savedTags;
	}

	static List<Photo> toPhotoList(JSONArray jArr) throws JSONException {
		List<Photo> photos = new ArrayList();
		for (int i = 0; i < jArr.length(); i++) {
			photos.add(new Photo(jArr.getJSONObject(i)));
		}
		return photos;
	}

	static List<UserStatus> toUserStatusList(JSONArray jArr) throws JSONException {
		List<UserStatus> status = new LinkedList();
		if (jArr != null) {
			for (int i = 0; i < jArr.length(); i++) {
				status.add(new UserStatus(jArr.getJSONObject(i)));
			}
		}
		return status;
	}

	static List<RemovedTag> toRemovedTagList(JSONArray jArr) throws JSONException {
		List<RemovedTag> removedTags = new LinkedList();
		for (int i = 0; i < jArr.length(); i++) {
			removedTags.add(new RemovedTag(jArr.getJSONObject(i)));
		}
		return removedTags;
	}

	static List<Group> toGroupList(JSONArray jArr) throws JSONException {
		List<Group> groups = new ArrayList();
		for (int i = 0; i < jArr.length(); i++) {
			groups.add(new Group(jArr.getJSONObject(i)));
		}
		return groups;
	}

	static List<String> toStringList(JSONArray jArr) throws JSONException {
		List<String> strings = new ArrayList();
		for (int i = 0; i < jArr.length(); i++) {
			strings.add(jArr.getString(i));
		}
		return strings;
	}

	static List<Namespace> toNamespaceList(JSONArray jArr) throws JSONException {
		List<Namespace> namespaces = new ArrayList();
		for (int i = 0; i < jArr.length(); i++) {
			namespaces.add(new Namespace(jArr.getJSONObject(i)));
		}
		return namespaces;
	}

	static int optInt(JSONObject jObj, String name) throws JSONException {
		return jObj.isNull(name) ? -1 : jObj.getInt(name);
	}
}
