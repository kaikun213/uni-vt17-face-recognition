package com.faceRecognition.utils.database;

import javax.naming.directory.InvalidAttributeValueException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import com.faceRecognition.utils.database.model.Credentials;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.repository.CredentialsRepository;
import com.faceRecognition.utils.database.service.AdminService;
import com.faceRecognition.utils.database.service.AuthenticationService;
import com.faceRecognition.utils.database.service.UserService;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseServiceTest extends TestCase {

	@Autowired
	AdminService admin;

	@Autowired
	UserService user;

	@Autowired
	AuthenticationService auth;

	@Autowired
	CredentialsRepository credentialsRepository;

	@Before
	public void setUp() throws InvalidAttributeValueException {
		admin.addUserEntity("1", "199501310271");
	}

	@Test
	public void adminShoulGetAllUserEntities() {
		assertEquals(1, admin.getUserEntities().size());
	}

	@Test
	public void adminShoulGetEmptyUserEntitiesList() throws NotFoundException {
		admin.deleteUserEntity("1");
		assertEquals(true, admin.getUserEntities().isEmpty());
	}

	@Test
	public void adminShouldAddUserEntity() {
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals("1", result.getId());
		assertEquals("199501310271", result.getPersonalNumber());
	}

	@Test
	public void adminShouldDeleteUserEntity() throws NotFoundException {
		admin.deleteUserEntity("1");
		assertEquals(0, admin.getUserEntities().size());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotDeleteUserEntity() throws NotFoundException {
		admin.deleteUserEntity("100");
	}

	@Test
	public void adminShouldUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity("1", "100000000000");
		UserEntity result = admin.getUserEntities().get(0);
		assertEquals("1", result.getId());
		assertEquals("100000000000", result.getPersonalNumber());
	}

	@Test(expected = NotFoundException.class)
	public void adminShouldNotUpdateUserEntity() throws NotFoundException {
		admin.updateUserEntity("100", "1000000000000");
	}

	@Test
	public void userShouldGetPN() throws NotFoundException {
		assertEquals("199501310271", user.getPersonalNumber("1"));
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