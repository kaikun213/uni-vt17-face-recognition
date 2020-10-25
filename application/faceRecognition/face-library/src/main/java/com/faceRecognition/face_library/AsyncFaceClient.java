package com.faceRecognition.face_library;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;
import com.faceRecognition.face_library.model.Namespace;
import com.faceRecognition.face_library.model.Photo;
import com.faceRecognition.face_library.model.RemovedTag;
import com.faceRecognition.face_library.model.SavedTag;
import com.faceRecognition.face_library.model.UserStatus;
import com.faceRecognition.face_library.response.GroupResponse;
import com.faceRecognition.face_library.response.LimitsResponse;
import com.faceRecognition.face_library.response.TrainResponse;
import com.faceRecognition.face_library.response.UsersResponse;

public class AsyncFaceClient implements Serializable {
	private final List<RequestListener> listeners;
	private final FaceClient faceClient;

	public AsyncFaceClient(FaceClient faceClient) {
		this.faceClient = faceClient;
		this.listeners = new LinkedList();
	}

	public AsyncFaceClient(String apiKey, String apiSecret) {
		this(new DefaultFaceClient(apiKey, apiSecret));
	}

	public void addTag(final String url, final float x, final float y, final int width, final int height,
			final String uid, final String label, final String taggerId) {
		new Thread() {
			public void run() {
				try {
					AsyncFaceClient.this.faceClient.addTag(url, x, y, width, height, uid, label, taggerId);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onAddTag();
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.ADD_TAG);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.ADD_TAG);
				}
			}
		}.start();
	}

	public void detect(final File imageFile) {
		new Thread() {
			private Photo photo;

			public void run() {
				try {
					photo = AsyncFaceClient.this.faceClient.detect(imageFile);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onDetect(photo);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.DETECT);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.DETECT);
				}
			}
		}.start();
	}

	public void detect(final String urls) {
		new Thread() {
			private List<Photo> photos;

			public void run() {
				try {
					photos = AsyncFaceClient.this.faceClient.detect(urls);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onDetect(photos);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.DETECT);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.DETECT);
				}
			}
		}.start();
	}

	public void facebookGet(final String uids) {
		new Thread() {
			private List<Photo> photos;

			public void run() {
				try {
					photos = AsyncFaceClient.this.faceClient.facebookGet(uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onFacebookGet(photos);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.FACEBOOK);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.FACEBOOK);
				}
			}
		}.start();
	}

	public void getLimits() {
		new Thread() {
			private LimitsResponse response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.getLimits();
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGetLimits(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.LIMITS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.LIMITS);
				}
			}
		}.start();
	}

	public void getNamespace(final String namespace) {
		new Thread() {
			private Namespace response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.getNamespace(namespace);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGetNamespace(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.NAMESPACES);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.NAMESPACES);
				}
			}
		}.start();
	}

	public void getTags(final String pids, final String urls, final String uids, final String order,
			final String filter, final boolean together, final int limit) {
		new Thread() {
			private List<Photo> photos;

			public void run() {
				try {
					photos = AsyncFaceClient.this.faceClient.getTags(pids, urls, uids, order, filter, together, limit);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGetTags(photos);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.GET_TAGS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.GET_TAGS);
				}
			}
		}.start();
	}

	public void getTags(final String urls, final String uids, final String order, final String filter,
			final boolean together, final int limit) {
		new Thread() {
			private List<Photo> photos;

			public void run() {
				try {
					photos = AsyncFaceClient.this.faceClient.getTags(urls, uids, order, filter, together, limit);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGetTags(photos);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.GET_TAGS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.GET_TAGS);
				}
			}
		}.start();
	}

	public void group(final File imageFile, final String uids) throws FaceClientException, FaceServerException {
		new Thread() {
			private GroupResponse response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.group(imageFile, uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGroup(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.GROUP);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.GROUP);
				}
			}
		}.start();
	}

	public void group(final String urls, final String uids) throws FaceClientException, FaceServerException {
		new Thread() {
			private GroupResponse response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.group(urls, uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGroup(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.GROUP);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.GROUP);
				}
			}
		}.start();
	}

	public void namespaces() {
		new Thread() {
			private List<Namespace> namespaces;

			public void run() {
				try {
					namespaces = AsyncFaceClient.this.faceClient.namespaces();
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onGetNamespaces(namespaces);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.NAMESPACES);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.NAMESPACES);
				}
			}
		}.start();
	}

	public void recognize(final File imageFile, final String uids) {
		new Thread() {
			private Photo photo;

			public void run() {
				try {
					photo = AsyncFaceClient.this.faceClient.recognize(imageFile, uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onRecognize(photo);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.RECOGNIZE);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.RECOGNIZE);
				}
			}
		}.start();
	}

	public void recognize(final String urls, final String uids) {
		new Thread() {
			private List<Photo> photos;

			public void run() {
				try {
					photos = AsyncFaceClient.this.faceClient.recognize(urls, uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onRecognize(photos);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.RECOGNIZE);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.RECOGNIZE);
				}
			}
		}.start();
	}

	public void removeTags(final String tids) {
		new Thread() {
			private List<RemovedTag> removedTags;

			public void run() {
				try {
					removedTags = AsyncFaceClient.this.faceClient.removeTags(tids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onRemoveTags(removedTags);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.RECOGNIZE);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.RECOGNIZE);
				}
			}
		}.start();
	}

	public void saveTags(final String tids, final String uid, final String label) {
		new Thread() {
			private List<SavedTag> savedTags;

			public void run() {
				try {
					savedTags = AsyncFaceClient.this.faceClient.saveTags(tids, uid, label);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onSaveTags(savedTags);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.SAVE_TAGS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.SAVE_TAGS);
				}
			}
		}.start();
	}

	public void status(final String uids) {
		new Thread() {
			private List<UserStatus> userStatuses;

			public void run() {
				try {
					userStatuses = AsyncFaceClient.this.faceClient.status(uids);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onStatus(userStatuses);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.STATUS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.STATUS);
				}
			}
		}.start();
	}

	public void train(final String uid) {
		new Thread() {
			private TrainResponse response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.train(uid);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onTrain(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.TRAIN);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.TRAIN);
				}
			}
		}.start();
	}

	public void users(final String namespaces) {
		new Thread() {
			private UsersResponse response;

			public void run() {
				try {
					response = AsyncFaceClient.this.faceClient.users(namespaces);
					for (RequestListener listener : AsyncFaceClient.this.listeners) {
						listener.onUsers(response);
					}
				} catch (FaceServerException fse) {
					AsyncFaceClient.this.notifyListeners(fse, FaceApi.USERS);
				} catch (FaceClientException fce) {
					AsyncFaceClient.this.notifyListeners(fce, FaceApi.USERS);
				}
			}
		}.start();
	}

	public void addListener(RequestListener listener) {
		this.listeners.add(listener);
	}

	public void clearFacebookCreds() {
		this.faceClient.clearFacebookCreds();
	}

	public void clearTwitterCreds() {
		this.faceClient.clearTwitterCreds();
	}

	public void setAggressive(boolean isAggressive) {
		this.faceClient.setAggressive(isAggressive);
	}

	public void setFacebookOauth2(String fbUserId, String oauth2Token) {
		this.faceClient.setFacebookOauth2(fbUserId, oauth2Token);
	}

	public void setTwitterOauth(String oauthUser, String oauthSecret, String oauthToken) {
		this.faceClient.setTwitterOauth(oauthUser, oauthSecret, oauthToken);
	}

	public boolean isAggressive() {
		return this.faceClient.isAggressive();
	}

	public FaceClient getFaceClient() {
		return this.faceClient;
	}

	private void notifyListeners(FaceServerException fse, FaceApi faceApi) {
		for (RequestListener listener : this.listeners) {
			listener.onFaceServerException(fse, faceApi);
		}
	}

	private void notifyListeners(FaceClientException fce, FaceApi faceApi) {
		for (RequestListener listener : this.listeners) {
			listener.onFaceClientException(fce, faceApi);
		}
	}
}
