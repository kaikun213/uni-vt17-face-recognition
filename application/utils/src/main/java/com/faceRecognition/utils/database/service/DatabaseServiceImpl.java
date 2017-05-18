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

	@Override
	public String getPersonalNumber(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity == null)
			throw new NotFoundException();
		return entity.getPersonalNumber();
	}

	@Override // PN Format = YYYYMMDDXXXX
	public UserEntity addUserEntity(String personalNumber, String photoLink) throws InvalidAttributeValueException {
		if (personalNumber.length() != 12 || !personalNumber.matches("\\d+"))
			throw new InvalidAttributeValueException();
		UserEntity entity = new UserEntity(personalNumber, photoLink);
		this.userEntitiesRepository.saveAndFlush(entity);
		return entity;
	}

	@Override
	public UserEntity updateUserEntity(String id, String personalNumber, String photoLink) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity == null)
			throw new NotFoundException();
		if (!personalNumber.isEmpty() && personalNumber != null)
			entity.setPersonalNumber(personalNumber);
		if (!photoLink.isEmpty() && photoLink != null)
			entity.setPhotoLink(photoLink);
		this.userEntitiesRepository.saveAndFlush(entity);
		return entity;
	}

	@Override
	public void deleteUserEntity(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity == null)
			throw new NotFoundException();
		this.userEntitiesRepository.delete(entity);
	}

	@Override
	public UserEntity getUserEntity(String id) throws NotFoundException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity == null)
			throw new NotFoundException();
		return entity;
	}

	@Override
	public List<UserEntity> getUserEntities() {
		List<UserEntity> entities = userEntitiesRepository.findAll();
		return entities == null ? Collections.emptyList() : entities;
	}

	@Override
	public boolean isValidCredentials(String username, String password) {
		Credentials credentials = this.credentialsRepository.findOne(username);
		return credentials == null ? false : credentials.getPassword().equals(password);
	}
}