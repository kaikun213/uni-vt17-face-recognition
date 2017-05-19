package com.faceRecognition.utils.database;

import javax.naming.directory.InvalidAttributeValueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.faceRecognition.utils.database.model.Credentials;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.repository.CredentialsRepository;
import com.faceRecognition.utils.database.service.AdminService;
import com.faceRecognition.utils.database.service.AuthenticationService;
import com.faceRecognition.utils.database.service.UserService;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class DatabaseServiceTest extends TestCase {

	@Autowired
	AdminService admin;

	@Autowired
	UserService user;

	@Autowired
	AuthenticationService auth;

	@Autowired
	CredentialsRepository credentialsRepository;
	
	UserEntity tester;

	@Before
	public void setUp() throws InvalidAttributeValueException {
		tester = admin.addUserEntity("photolink", "201705181234");
	}

	@Test
	public void adminShoulGetAllUserEntities() {
		assertEquals(1, admin.getUserEntities().size());
	}

	@Test
	public void adminShouldAddUserEntity() {
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals("201705181234", result.getPersonalNumber());
		assertEquals("photolink", result.getPhotoLink());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotDeleteUserEntity() throws NotFoundException {
		admin.deleteUserEntity("10001");
	}

	@Test
	public void adminShouldUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity(tester.getId(), "100000000000", "newPhotoLink");
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals(tester.getId(), result.getId());
		assertEquals("100000000000", result.getPersonalNumber());
		assertEquals("newPhotoLink", result.getPhotoLink());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity("10001", "1000000000000", "newPhotoLink");
	}

	@Test
	public void userShouldGetPN() throws NotFoundException {
		assertEquals(tester.getPersonalNumber(), user.getPersonalNumber(tester.getId()));
	}

	@Test(expected = NotFoundException.class)
	public void userShouldNotGetPN() throws NotFoundException {
		assertEquals("199501310271", user.getPersonalNumber("100"));
	}

	@Test
	public void credentialsShouldBeValid() {
		credentialsRepository.saveAndFlush(new Credentials("username", "password"));
		assertEquals(true, auth.isValidCredentials("username", "password"));
	}

	@Test
	public void credentialsShouldNotBeValid() {
		assertEquals(false, auth.isValidCredentials("username", "passwooord"));
	}
}