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
	
	String exampleImage = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.uni-regensburg.de%2FFakultaeten%2Fphil_Fak_II%2FPsychologie%2FPsy_II%2Fbeautycheck%2Fenglish%2Fprototypen%2Fm_unsexy_gr.jpg&f=1";

	@Autowired
	AdminService afs;
	
	@Autowired
	UserService usf;
	
	@Before
	public void setup(){
		
	}
	
	@Test
	public void getPicture() throws FaceClientException, FaceServerException{
		afs.get("001");
//		afs.create("001", exampleImage);
//		usf.match(exampleImage);
	}
}
