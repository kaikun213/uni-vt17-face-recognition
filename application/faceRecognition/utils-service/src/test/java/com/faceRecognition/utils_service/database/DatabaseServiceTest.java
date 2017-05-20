package com.faceRecognition.utils_service.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import com.faceRecognition.utils_service.database.model.UserEntity;
import com.faceRecognition.utils_service.database.service.AdminDBService;
import com.faceRecognition.utils_service.database.service.AuthenticationService;
import com.faceRecognition.utils_service.database.service.UserDBService;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseServiceTest extends TestCase {

	@Autowired
	AdminDBService admin;

	@Autowired
	UserDBService user;

	@Autowired
	AuthenticationService auth;

	@Test
	public void adminShoulGetAllUserEntities() {
		assertEquals(1, admin.getUserEntities().size());
	}

	@Test
	public void userShouldGetPN() throws NotFoundException {
		assertEquals("000000000000", user.getPersonalNumber("1"));
	}
	
	@Test
	public void adminShouldAddUserEntity() {
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals("000000000000", result.getPersonalNumber());
		assertEquals("link", result.getPhotoLink());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotDeleteUserEntity() throws NotFoundException {
		admin.deleteUserEntity("10001");
	}

	@Test
	public void adminShouldUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity("1", "100000000000", "newPhotoLink");
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals("1", "" + result.getId());
		assertEquals("100000000000", result.getPersonalNumber());
		assertEquals("newPhotoLink", result.getPhotoLink());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity("10001", "1000000000000", "newPhotoLink");
	}

	@Test(expected = NotFoundException.class)
	public void userShouldNotGetPN() throws NotFoundException {
		assertEquals("199501310271", user.getPersonalNumber("100"));
	}

	@Test
	public void credentialsShouldBeValid() {
		assertEquals(true, auth.isValidCredentials("username", "password"));
	}

	@Test
	public void credentialsShouldNotBeValid() {
		assertEquals(false, auth.isValidCredentials("username", "passwooord"));
	}
}