package com.example.demo.repository;

import com.example.demo.model.CompanyProfile;
import com.example.demo.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface  CompanyProfileRepository extends MongoRepository<CompanyProfile,Integer> {
    Optional<CompanyProfile> findById(String id);
    Optional<CompanyProfile> findByUserEmail(String email);
}
