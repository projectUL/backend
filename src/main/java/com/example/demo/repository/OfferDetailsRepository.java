package com.example.demo.repository;

import com.example.demo.model.OfferDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface OfferDetailsRepository extends MongoRepository<OfferDetails, String> {

    Page<OfferDetails> findByCategory(String category, Pageable pageable);
    Page<OfferDetails> findByJobType(String jobType, Pageable pageable);
    Page<OfferDetails> findByCategoryAndJobType(String category, String jobType, Pageable pageable);
    List<OfferDetails> findByCompanyName(String companyName);

    @Query("{'offerTitle':{'$regex':'?0','$options':'i'}}")
    Page<OfferDetails> findByName(Optional<String> name, Pageable pageable);

    @Query("{'category':{'$regex':'?0','$options':'i'}, 'offerTitle':{'$regex':'?1','$options':'i'}}")
    Page<OfferDetails> findByCategoryAndName(String category, Optional<String> name, Pageable pageable);
    
    @Query("{'jobType':{'$regex':'?0','$options':'i'}, 'offerTitle':{'$regex':'?1','$options':'i'}}")
    Page<OfferDetails> findByJobTypeAndName(String type, Optional<String> name, Pageable pageable);
    
    @Query("{'category':{'$regex':'?0','$options':'i'}, 'jobType':{'$regex':'?1','$options':'i'}, 'offerTitle':{'$regex':'?2','$options':'i'}}")
    Page<OfferDetails> findByCategoryAndJobTypeAndName(String category, String jobType, Optional<String> name, Pageable pageable);
}
