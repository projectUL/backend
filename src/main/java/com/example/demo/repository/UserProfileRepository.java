package com.example.demo.repository;

import com.example.demo.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserProfileRepository extends MongoRepository<UserProfile,Integer> {
    Optional<UserProfile> findById(String id);
    Optional<UserProfile> findByUserEmail(String email);
}
