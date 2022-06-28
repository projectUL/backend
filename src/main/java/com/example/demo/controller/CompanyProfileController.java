package com.example.demo.controller;

import com.example.demo.ExtraClasses.*;
import com.example.demo.model.CompanyProfile;
import com.example.demo.model.OfferDetails;
import com.example.demo.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/companyprofile")
public class CompanyProfileController {
    @Autowired
    CompanyProfileRepository repository;

    @PostMapping("/createprofile/{email}")
    public String createCompanyProfile(@PathVariable String email) {

        if (repository.findByUserEmail(email).isEmpty()){
            CompanyProfile profile = new CompanyProfile();
            profile.setUserEmail(email);
            return repository.save(profile).getId();
        }
        return "Profile already exists";
    }


    @GetMapping("/{email}")
    public String getUserId(@PathVariable String email) {
        if (repository.findByUserEmail(email).isPresent())
            return repository.findByUserEmail(email).get().getId();
        return null;
    }


    @GetMapping("/getprofile/{email}")
    public CompanyProfile getCompanyProfile(@PathVariable String email) {
        if (repository.findByUserEmail(email).isPresent())
            return repository.findByUserEmail(email).get();
        return null;
    }

    @PutMapping("/createprofile/description/{id}")
    public void createCompanyProfileDescription(
            @RequestBody Description descr,
            @PathVariable String id
    ) {
        Optional<CompanyProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            profile.get().setDescription(descr);
            repository.save(profile.get());
        }
    }


    @GetMapping("/createprofile/description/{id}")
    public Description getCompanyProfileDescription(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getDescription();
        return null;
    }


    @DeleteMapping("/delete/description/{id}")
    public CompanyProfile deleteCompanyProfileDescription(@PathVariable String id) {
        Optional<CompanyProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            profile.get().setDescription(null);
            repository.save(profile.get());
            return profile.get();
        }
        return null;
    }



    @PutMapping("/createprofile/createjob/{id}")
    public Optional<CompanyProfile> createCompanyProfileJobDetails(
            @RequestBody OfferDetails details,
            @PathVariable String id
    ) {
        Optional<CompanyProfile> profile = repository.findById(id);
        profile.get().getOfferDetails().add(details);

        repository.save(profile.get());

        return profile;
    }

}
