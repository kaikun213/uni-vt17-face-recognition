package com.faceRecognition.utils.database.service;

import java.util.Collections;
import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;
import com.faceRecognition.utils.database.model.Credentials;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.repository.CredentialsRepository;
import com.faceRecognition.utils.database.repository.UserEntitiesRepository;

@Component
public class DatabaseServiceImpl implements AdminService, UserService, AuthenticationService {

	@Autowired
	private UserEntitiesRepository userEntitiesRepository;

	@Autowired
	private CredentialsRepository credentialsRepository;

	public String getPersonalNumber(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null)
			return entity.getPersonalNumber();
		throw new NotFoundException();
	}

	// PN Format = YYYYMMDDXXXX
	public void addUserEntity(String id, String personalNumber) throws InvalidAttributeValueException {
		if (personalNumber.length() != 12 || !personalNumber.matches("\\d+"))
			throw new InvalidAttributeValueException();
		this.userEntitiesRepository.saveAndFlush(new UserEntity(id, personalNumber));
	}

	public void updateUserEntity(String id, String personalNumber) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null) {
			entity.setId(id);
			entity.setPersonalNumber(personalNumber);
			this.userEntitiesRepository.saveAndFlush(entity);
		} else
			throw new NotFoundException();
	}

	public void deleteUserEntity(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null)
			this.userEntitiesRepository.delete(entity);
		else
			throw new NotFoundException();
	}

	public UserEntity getUserEntity(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null)
			return entity;
		throw new NotFoundException();
	}

	public List<UserEntity> getUserEntities() {
		List<UserEntity> entities = userEntitiesRepository.findAll();
		return entities == null ? Collections.emptyList() : entities;
	}

	public boolean isValidCredentials(String username, String password) {
		Credentials credentials = this.credentialsRepository.findOne(username);
		return credentials == null ? false : credentials.getPassword().equals(password);
	}
}