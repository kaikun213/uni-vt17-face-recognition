package com.faceRecognition.utils.face.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;
import com.faceRecognition.utils.face.service.AdminService;
import com.faceRecognition.utils.face.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkybiometricAPITest {
	
	String exampleImage = "http://seedmagazine.com/images/uploads/attractive_article.jpg";

	@Autowired
	AdminService afs;
	
	@Autowired
	UserService usf;
	
	@Before
	public void setup(){
		
	}
	
	@Test
	public void getPicture() throws FaceClientException, FaceServerException{
//		afs.create("001", exampleImage);
//		usf.match(exampleImage);
	}
}
