package com.faceRecognition.admin.service;

import java.io.IOException;
import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.faceRecognition.utils.database.model.UserEntity;

public interface AdminService {

	UserEntity create(MultipartFile file, String personalNumber) throws InvalidAttributeValueException, IOException;

	UserEntity retrieve(String id) throws NotFoundException;

	UserEntity update(MultipartFile file, String id, String personalNumber) throws NotFoundException, IOException;

	void delete(String id) throws NotFoundException;

	List<UserEntity> list(int size, int page);
}