package com.example.demo.repository;

import com.example.demo.model.Company;
import com.example.demo.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, Integer> {

    Optional<Users> findById(String id);
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    void save(Optional<Users> user);

}
