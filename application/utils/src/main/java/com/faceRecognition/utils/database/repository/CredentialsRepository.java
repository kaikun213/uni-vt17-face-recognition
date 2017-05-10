package com.faceRecognition.utils.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faceRecognition.utils.database.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, String>{

}