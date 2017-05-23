package com.faceRecognition.face_library;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.faceRecognition.face_library.exception.FaceClientException;
import com.faceRecognition.face_library.exception.FaceServerException;

class ResponderImpl implements Responder {
	private static final Logger logger = LoggerFactory.getLogger(Responder.class);
	private static final String FAILURE = "failure";
	private final HttpClient httpClient;
	private final HttpPost postMethod;
	private final HttpGet getMethod;

	public ResponderImpl() {
		this.httpClient = new DefaultHttpClient();
		this.postMethod = new HttpPost();
		this.getMethod = new HttpGet();
	}

	public String doPost(URI uri, List<NameValuePair> params) throws FaceClientException, FaceServerException {
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			this.postMethod.setURI(uri);
			this.postMethod.setEntity(entity);

			HttpResponse response = this.httpClient.execute(this.postMethod);

			return checkResponse(response);
		} catch (IOException ioe) {
			logger.error("Error while POSTing to {} ", uri, ioe);
			throw new FaceClientException(ioe);
		}
	}

	public String doPost(File file, URI uri, List<NameValuePair> params)
			throws FaceClientException, FaceServerException {
		try {
			MultipartEntity entity = new MultipartEntity();
			if (logger.isInfoEnabled()) {
				logger.info("Adding image entity, size: [{}] bytes", Long.valueOf(file.length()));
			}
			entity.addPart("image", new FileBody(file));
			try {
				for (NameValuePair nvp : params) {
					entity.addPart(nvp.getName(), new StringBody(nvp.getValue()));
				}
			} catch (UnsupportedEncodingException uee) {
				logger.error("Error adding entity", uee);
				throw new FaceClientException(uee);
			}
			this.postMethod.setURI(uri);
			this.postMethod.setEntity(entity);

			long start = System.currentTimeMillis();
			HttpResponse response = this.httpClient.execute(this.postMethod);
			if (logger.isDebugEnabled()) {
				logger.debug("POST took {} (ms)", Long.valueOf(System.currentTimeMillis() - start));
			}
			return checkResponse(response);
		} catch (IOException ioe) {
			logger.error("Error while POSTing to {} ", uri, ioe);
			throw new FaceClientException(ioe);
		}
	}

	public String doGet(URI uri) throws FaceClientException, FaceServerException {
		this.getMethod.setURI(uri);
		try {
			HttpResponse response = this.httpClient.execute(this.getMethod);
			return checkResponse(response);
		} catch (IOException ioe) {
			logger.error("Error while POSTing to {} ", uri, ioe);
			throw new FaceClientException(ioe);
		}
	}

	private String checkResponse(HttpResponse httpResponse) throws FaceServerException, FaceClientException {
		try {
			String json = EntityUtils.toString(httpResponse.getEntity());
			if (json.contains("failure")) {
				JSONObject obj = new JSONObject(json);
				String message = obj.getString("error_message");
				int errorCode = obj.getInt("error_code");

				FaceServerException fse = new FaceServerException(message, errorCode);
				if (logger.isDebugEnabled()) {
					logger.debug("Error: ", fse);
				}
				throw fse;
			}
			if (logger.isInfoEnabled()) {
				logger.info("SUCCESS:{} ", httpResponse.getStatusLine());
			}
			return json;
		} catch (JSONException jse) {
			logger.error("Error parsing response", jse);
			throw new FaceClientException(jse);
		} catch (IOException ioe) {
			logger.error("Error parsing response", ioe);
			throw new FaceClientException(ioe);
		}
	}
}
