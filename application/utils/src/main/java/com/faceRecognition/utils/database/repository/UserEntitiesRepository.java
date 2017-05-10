package com.faceRecognition.utils.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faceRecognition.utils.database.model.UserEntity;

public interface UserEntitiesRepository extends JpaRepository<UserEntity, Long> {
}