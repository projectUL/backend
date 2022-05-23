package com.example.demo.repository;

import com.example.demo.model.OfferDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface OfferDetailsRepository extends MongoRepository<OfferDetails, String> {

    Page<OfferDetails> findByCategory(String category, Pageable pageable);
    Page<OfferDetails> findByJobType(String jobType, Pageable pageable);
    Page<OfferDetails> findByCategoryAndJobType(String category, String jobType, Pageable pageable);

    @Query("{'offerTitle':{'$regex':'?0','$options':'i'}}")
    Page<OfferDetails> searchByName(Optional<String> name, Pageable pageable);

}
