package com.faceRecognition.utils_service.face.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.mhendred.face4j.DefaultFaceClient;
import com.github.mhendred.face4j.FaceClient;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.github.mhendred.face4j.model.Face;
import com.github.mhendred.face4j.model.Photo;
import com.github.mhendred.face4j.model.RemovedTag;

@Service
public class FaceServiceImpl implements AdminFaceService, UserFaceService {

	@Value("${skybio.api.key}")
	private String apiKey;

	@Value("${skybio.api.secret}")
	private String apiSecret;

	// SkyBioMetric Namespace
	private static final String NAMESPACE = "@lnuFace";

	private static final String USER_ID = "example";

	FaceClient faceClient;

	@PostConstruct
	public void init() {
		System.err.println("APIKEY: " + apiKey + " : " + apiSecret);
		this.faceClient = new DefaultFaceClient(apiKey, apiSecret);
	}

	public void update(String id, String url) throws FaceClientException, FaceServerException {
		delete(id);
		create(id, url);
	}

	public void create(String id, String url) throws FaceClientException, FaceServerException {
		Photo photo = faceClient.detect(url).get(0);
		Face f = photo.getFace();
		faceClient.saveTags(f.getTID(), USER_ID + NAMESPACE, "label from: " + id);
		faceClient.train(USER_ID + NAMESPACE);
	}

	public void delete(String id) throws FaceClientException, FaceServerException {
		List<Photo> tags = faceClient.getTags("", id + NAMESPACE, "", "", false, 1);
		String TID = tags.get(0).getFace().getTID();
		List<RemovedTag> removedTags = faceClient.removeTags(TID);

		// must be called to persist changes
		faceClient.train(USER_ID + NAMESPACE);
	}

	public String match(String url) throws FaceClientException, FaceServerException {
		Photo photo = faceClient.recognize(url, "all" + NAMESPACE).get(0);
		System.out.println("Faces:");
		for (Face face : photo.getFaces()) {
			System.out.print(face.getGuesses());
			System.out.print(" : " + face.getTID() + "\n");
		}
		// return userId if confidence is higher than 85%
		return photo.getFace().getGuess().second > 85 ? photo.getFace().getGuess().first : null;
	}
}