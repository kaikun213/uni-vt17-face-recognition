package com.faceRecognition.utils.database.service;

import java.util.List;
import javax.ejb.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.repository.CredentialsRepository;
import com.faceRecognition.utils.database.repository.UserEntitiesRepository;

class DatabaseServiceImpl implements AdminService, UserService, AuthenticationService {

	@Autowired
	private UserEntitiesRepository userEntitiesRepository;

	@Autowired
	private CredentialsRepository credentialsRepository;

	public Long getPersonalNumber(Long id) {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null) {
			return entity.getPersonalNumber();
		}
		throw new NoSuchEntityException();
	}

	public void addUserEntity(Long personalNumber, String photoLink) {
		this.userEntitiesRepository.saveAndFlush(new UserEntity(personalNumber, photoLink));
	}

	public void updateUserEntity(Long id, Long personalNumber, String photoLink) {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null) {
			entity.setPersonalNumber(personalNumber);
			entity.setPhotoLink(photoLink);
		} else
			throw new NoSuchEntityException();
	}

	public void deleteUserEntity(Long id) throws NoSuchEntityException {
		UserEntity entity = this.userEntitiesRepository.findOne(id);
		if (entity != null)
			this.userEntitiesRepository.delete(id);
		else
			throw new NoSuchEntityException();
	}

	public List<UserEntity> getUserEntities() {
		return userEntitiesRepository.findAll();
	}

	public boolean isValidCredentials(String username, String password) {
		return this.credentialsRepository.findOne(username) != null;
	}
}