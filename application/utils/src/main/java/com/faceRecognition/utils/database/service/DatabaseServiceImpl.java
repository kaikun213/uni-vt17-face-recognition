package com.faceRecognition.utils.database.service;

import java.util.List;
import javax.ejb.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import com.faceRecognition.utils.database.model.UserEntity;
import com.faceRecognition.utils.database.repository.UserEntitiesRepository;

class DatabaseServiceImpl implements AdminService, UserService, AuthenticationService {

	@Autowired
	private UserEntitiesRepository repository;

	public long getPersonalNumber(long id) {
		UserEntity entity = this.repository.findOne(id);
		if (entity != null) {
			return entity.getPersonalNumber();
		}
		throw new NoSuchEntityException();
	}

	public void addUserEntity(Long personalNumber, String photoLink) {
		this.repository.saveAndFlush(new UserEntity(personalNumber, photoLink));
	}

	public void updateUserEntity(Long id, Long personalNumber, String photoLink) {
		UserEntity entity = this.repository.findOne(id);
		if (entity != null) {
			entity.setPersonalNumber(personalNumber);
			entity.setPhotoLink(photoLink);
		} else
			throw new NoSuchEntityException();
	}

	public void deleteUserEntity(Long id) throws NoSuchEntityException {
		UserEntity entity = this.repository.findOne(id);
		if (entity != null)
			this.repository.delete(id);
		else
			throw new NoSuchEntityException();
	}

	public List<UserEntity> getUserEntities() {
		return repository.findAll();
	}
}