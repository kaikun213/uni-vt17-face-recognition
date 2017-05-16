package com.faceRecognition.utils.face.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.mhendred.face4j.DefaultFaceClient;
import com.github.mhendred.face4j.FaceClient;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.github.mhendred.face4j.model.Face;
import com.github.mhendred.face4j.model.Photo;

@Component
public class FaceServiceImpl implements AdminService, UserService{
	
	@Autowired
	private Environment env;
	
	@Value( "${skybio.api.key}" )
	private String apiKey;
	
	@Value( "${skybio.api.secret}" )
	private String apiSecret = "";
	
	// SkyBioMetric Namespace
	private static final String NAMESPACE = "lnuFace";
	
	FaceClient faceClient;// = new DefaultFaceClient(apiKey, apiSecret);

	@Override
	public String get(String id) {
		System.out.println("----------------------------- \n" + apiKey + " - " + apiSecret + "------");
		return null;
	}

	@Override
	public void update(String id, String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(String id, String url) throws FaceClientException, FaceServerException {
    	Photo photo = faceClient.detect(url).get(0);
    	Face f = photo.getFace();
    	faceClient.saveTags(f.getTID(), id+"@"+NAMESPACE, "label from: " + id);
    	faceClient.train(id+"@"+NAMESPACE);
    	// check user status: faceClient.status(id)
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String match(String url) throws FaceClientException, FaceServerException {
		Photo photo = faceClient.recognize(url, "all@" + NAMESPACE).get(0);
    	
    	for (Face face : photo.getFaces())
    	{
    		System.out.println(face.getGuesses());
    	}
		return null;
	}

}
