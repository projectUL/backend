package com.example.demo.repository;

import com.example.demo.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface CompanyRepository extends MongoRepository<Company, Integer> {

    @Query("{'companyName':{'$regex':'?0','$options':'i'}}")
    Page<Company> searchByName(Optional<String> name, Pageable pageable);

    Optional<Company> findById(String id);
    Optional<Company> findByCompanyMail(String email);

}