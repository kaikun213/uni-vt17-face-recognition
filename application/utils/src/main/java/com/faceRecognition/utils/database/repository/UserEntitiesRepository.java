package com.faceRecognition.utils.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.faceRecognition.utils.database.model.UserEntity;

@Repository
public interface UserEntitiesRepository extends JpaRepository<UserEntity, String> {
}