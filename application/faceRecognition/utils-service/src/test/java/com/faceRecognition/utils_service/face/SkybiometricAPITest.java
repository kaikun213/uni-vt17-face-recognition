package com.faceRecognition.utils_service.face;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.faceRecognition.utils_service.face.service.AdminFaceService;
import com.faceRecognition.utils_service.face.service.UserFaceService;
import com.github.mhendred.face4j.exception.FaceClientException;
import com.github.mhendred.face4j.exception.FaceServerException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkybiometricAPITest {

	String exampleImage = "http://seedmagazine.com/images/uploads/attractive_article.jpg";
	String exampleImage2 = "http://pngimg.com/uploads/face/face_PNG11756.png";
	String exampleImage3 = "http://pngimg.com/uploads/face/face_PNG5659.png";

	@Autowired
	AdminFaceService afs;

	@Autowired
	UserFaceService usf;

	@Before
	public void setup() throws FaceClientException, FaceServerException {

	}

	// @Test
	public void delete() throws FaceClientException, FaceServerException {
		afs.create("001", exampleImage);
		afs.create("002", exampleImage2);
		afs.create("003", exampleImage3);

		String matchId = usf.match(exampleImage2);
		System.out.println("Image2 Matched with:" + matchId);
		matchId = usf.match(exampleImage3);
		System.out.println("Image3 Matched with:" + matchId);
		afs.delete("001");
	}

	@Test
	public void match() throws FaceClientException, FaceServerException {
		usf.match(exampleImage2);
	}
}