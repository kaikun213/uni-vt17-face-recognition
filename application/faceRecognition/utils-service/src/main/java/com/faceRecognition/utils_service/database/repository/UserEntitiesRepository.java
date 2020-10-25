package com.faceRecognition.utils_service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.faceRecognition.utils_service.database.model.UserEntity;

@Repository
public interface UserEntitiesRepository extends JpaRepository<UserEntity, Long> {
}