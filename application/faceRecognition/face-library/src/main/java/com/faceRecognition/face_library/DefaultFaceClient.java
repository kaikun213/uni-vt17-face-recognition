package com.faceRecognition.face_library;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.face_library.model.Namespace;
import com.faceRecognition.face_library.model.Photo;
import com.faceRecognition.face_library.model.RemovedTag;
import com.faceRecognition.face_library.model.SavedTag;
import com.faceRecognition.face_library.model.UserStatus;
import com.faceRecognition.face_library.response.GetTagsResponse;
import com.faceRecognition.face_library.response.GetTagsResponseImpl;
import com.faceRecognition.face_library.response.GroupResponse;
import com.faceRecognition.face_library.response.GroupResponseImpl;
import com.faceRecognition.face_library.response.LimitsResponse;
import com.faceRecognition.face_library.response.LimitsResponseImpl;
import com.faceRecognition.face_library.response.NamespaceResponse;
import com.faceRecognition.face_library.response.NamespaceResponseImpl;
import com.faceRecognition.face_library.response.PhotoResponse;
import com.faceRecognition.face_library.response.PhotoResponseImpl;
import com.faceRecognition.face_library.response.RemoveTagResponse;
import com.faceRecognition.face_library.response.RemoveTagResponseImpl;
import com.faceRecognition.face_library.response.SaveTagResponse;
import com.faceRecognition.face_library.response.SaveTagResponseImpl;
import com.faceRecognition.face_library.response.StatusResponse;
import com.faceRecognition.face_library.response.StatusResponseImpl;
import com.faceRecognition.face_library.response.TrainResponse;
import com.faceRecognition.face_library.response.TrainResponseImpl;
import com.faceRecognition.face_library.response.UsersResponse;
import com.faceRecognition.face_library.response.UsersResponseImpl;

public class DefaultFaceClient implements FaceClient, Serializable {
	private static final String API_ENDPOINT = "http://api.skybiometry.com";
	private final Responder http;
	private final Credentials creds;
	private final Parameters reqd;
	private final URI baseURI;
	private boolean isAggressive;

	public DefaultFaceClient(String apiKey, String apiSecret) {
		this(apiKey, apiSecret, new ResponderImpl());
	}

	public DefaultFaceClient(String apiKey, String apiSecret, Responder responder) {
		Validate.notEmpty(apiKey, "API key cannot be empty");
		Validate.notEmpty(apiSecret, "API secret cannot be empty");

		this.baseURI = URI.create("http://api.skybiometry.com");
		this.http = responder;
		this.creds = new Credentials();
		this.reqd = new Parameters();

		this.reqd.put("api_key", apiKey);
		this.reqd.put("api_secret", apiSecret);

		setAggressive(false);
	}

	public List<RemovedTag> removeTags(String tids) throws FaceClientException, FaceServerException {
		Validate.notEmpty(tids, "Tag ids cannot be empty");

		Parameters params = new Parameters("tids", tids);
		String json = executePost(FaceApi.REMOVE_TAGS, params);
		RemoveTagResponse response = new RemoveTagResponseImpl(json);

		return response.getRemovedTags();
	}

	public TrainResponse train(String uids) throws FaceClientException, FaceServerException {
		Parameters params = new Parameters("uids", uids);
		String json = executePost(FaceApi.TRAIN, params);
		TrainResponse response = new TrainResponseImpl(json);

		return response;
	}

	public void addTag(String url, float x, float y, int width, int height, String uid, String label, String taggerId)
			throws FaceClientException, FaceServerException {
		Validate.notNull(uid, "UID cannot be null");

		Parameters params = new Parameters();

		params.put("x", x);
		params.put("y", y);
		params.put("width", width);
		params.put("height", height);
		params.put("tagger_id", taggerId);
		params.put("url", url);
		params.put("uid", uid);
		params.put("label", label);

		executePost(FaceApi.ADD_TAG, params);
	}

	public List<Photo> getTags(String urls, String uids, String order, String filter, boolean together, int limit)
			throws FaceClientException, FaceServerException {
		return getTags(null, urls, uids, order, filter, together, limit);
	}

	public List<Photo> getTags(String pids, String urls, String uids, String order, String filter, boolean together,
			int limit) throws FaceClientException, FaceServerException {
		Parameters params = new Parameters();

		params.put("pids", pids);
		params.put("urls", urls);
		params.put("uids", uids);
		params.put("order", order);
		params.put("filter", filter);
		params.put("together", together);
		params.put("limit", limit);

		String json = executePost(FaceApi.GET_TAGS, params);
		GetTagsResponse response = new GetTagsResponseImpl(json);

		return response.getPhotos();
	}

	public List<SavedTag> saveTags(String tids, String uid, String label)
			throws FaceClientException, FaceServerException {
		Validate.notEmpty(uid, "User IDs cannot be null");
		Validate.notEmpty(tids, "Tag IDs cannot be null");

		Parameters params = new Parameters("tids", tids);

		params.put("uid", uid);
		params.put("label", label);

		String json = executePost(FaceApi.SAVE_TAGS, params);
		SaveTagResponse response = new SaveTagResponseImpl(json);

		return response.getSavedTags();
	}

	public Photo recognize(File imageFile, String uids) throws FaceClientException, FaceServerException {
		Validate.notNull(imageFile, "File is null");
		Validate.isTrue(imageFile.exists(), "File does not exist!");
		Validate.notEmpty(uids, "User IDs cannot be null");

		Parameters params = new Parameters("uids", uids);
		String json = executePost(imageFile, FaceApi.RECOGNIZE, params);
		PhotoResponse response = new PhotoResponseImpl(json);

		return response.getPhoto();
	}

	public List<Photo> recognize(String urls, String uids) throws FaceClientException, FaceServerException {
		Validate.notEmpty(urls, "URLs cant be empty");
		Validate.notEmpty(uids, "User IDs can't be empty");

		Parameters params = new Parameters("uids", uids);

		params.put("urls", urls);

		String json = executePost(FaceApi.RECOGNIZE, params);
		PhotoResponse response = new PhotoResponseImpl(json);

		return response.getPhotos();
	}

	public Photo detect(File imageFile) throws FaceClientException, FaceServerException {
		Validate.notNull(imageFile, "File is null");
		Validate.isTrue(imageFile.exists(), "File doesn't exist!");

		String json = executePost(imageFile, FaceApi.DETECT, new Parameters());
		PhotoResponse response = new PhotoResponseImpl(json);

		return response.getPhoto();
	}

	public List<Photo> detect(String urls) throws FaceClientException, FaceServerException {
		Validate.notNull(urls, "URLs cannot be null");

		Parameters params = new Parameters();

		params.put("urls", urls);

		String json = executePost(FaceApi.DETECT, params);
		PhotoResponse response = new PhotoResponseImpl(json);

		return response.getPhotos();
	}

	public List<UserStatus> status(String uids) throws FaceClientException, FaceServerException {
		Validate.notEmpty(uids, "UIDs cant be empty");

		Parameters params = new Parameters();

		params.put("uids", uids);

		String json = executePost(FaceApi.STATUS, params);
		StatusResponse response = new StatusResponseImpl(json);

		return response.getTrainingStatus();
	}

	public List<Photo> facebookGet(String uids) throws FaceClientException, FaceServerException {
		Validate.notEmpty(uids, "User IDs cannot be empty");

		Parameters params = new Parameters();

		params.put("uids", uids);

		String json = executePost(FaceApi.FACEBOOK, params);
		PhotoResponse response = new PhotoResponseImpl(json);

		return response.getPhotos();
	}

	public GroupResponse group(String urls, String uids) throws FaceClientException, FaceServerException {
		Validate.notEmpty(urls, "URLs cannot be empty");
		Validate.notEmpty(uids, "UIDs cannot be empty");

		Parameters params = new Parameters();

		params.put("uids", uids);
		params.put("urls", urls);

		String json = executePost(FaceApi.GROUP, params);
		GroupResponse response = new GroupResponseImpl(json);

		return response;
	}

	public GroupResponse group(File imageFile, String uids) throws FaceClientException, FaceServerException {
		Validate.isTrue(imageFile.exists(), "File does not exist");
		Validate.notEmpty(uids, "UIDs cannot be empty");

		Parameters params = new Parameters();

		params.put("uids", uids);

		String json = executePost(imageFile, FaceApi.GROUP, params);
		GroupResponse response = new GroupResponseImpl(json);

		return response;
	}

	public UsersResponse users(String namespaces) throws FaceClientException, FaceServerException {
		Validate.notEmpty(namespaces, "Must supply namespace(s)");

		Parameters params = new Parameters();
		params.put("namespaces", namespaces);

		String json = executePost(FaceApi.USERS, params);
		UsersResponse response = new UsersResponseImpl(json, namespaces);

		return response;
	}

	public LimitsResponse getLimits() throws FaceClientException, FaceServerException {
		String json = executePost(FaceApi.LIMITS, new Parameters());
		LimitsResponse response = new LimitsResponseImpl(json);

		return response;
	}

	public List<Namespace> namespaces() throws FaceClientException, FaceServerException {
		String json = executePost(FaceApi.NAMESPACES, new Parameters());
		NamespaceResponse response = new NamespaceResponseImpl(json);

		return response.getNamespaces();
	}

	public Namespace getNamespace(String namespace) throws FaceClientException, FaceServerException {
		for (Namespace ns : namespaces()) {
			if (ns.getName().equals(namespace)) {
				return ns;
			}
		}
		return null;
	}

	public void setFacebookOauth2(String fbUserId, String oauthToken) {
		this.creds.put("fb_user", fbUserId);
		this.creds.put("fb_oauth_token", oauthToken);
	}

	public void setTwitterOauth(String oauthUser, String oauthSecret, String oauthToken) {
		this.creds.put("twitter_oauth_user", oauthUser);
		this.creds.put("twitter_oauth_secret", oauthSecret);
		this.creds.put("twitter_oauth_token", oauthToken);
	}

	public void clearFacebookCreds() {
		this.creds.remove("fb_oauth_token");
		this.creds.remove("fb_user");
	}

	public void clearTwitterCreds() {
		this.creds.remove("twitter_oauth_user");
		this.creds.remove("twitter_oauth_secret");
		this.creds.remove("twitter_oauth_token");
	}

	public void setAggressive(boolean isAggressive) {
		this.isAggressive = isAggressive;

		this.reqd.put("detector", isAggressive ? "Aggressive" : "Normal");
	}

	public boolean isAggressive() {
		return this.isAggressive;
	}

	private String executePost(FaceApi faceApi, Parameters params) throws FaceClientException, FaceServerException {
		return executePost(null, faceApi, params);
	}

	private String executePost(File file, FaceApi faceApi, Parameters params)
			throws FaceClientException, FaceServerException {
		URI uri = this.baseURI.resolve(faceApi.getPath());

		params.putAll(this.reqd.getMap());
		if ((faceApi.takesAuth()) && (!this.creds.isEmpty())) {
			params.put("user_auth", this.creds.getAuthString());
		}
		if (file != null) {
			return this.http.doPost(file, uri, params.toPostParams());
		}
		return this.http.doPost(uri, params.toPostParams());
	}
}
