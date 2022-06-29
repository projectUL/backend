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


//    @GetMapping("/{email}")
//    public String getUserId(@PathVariable String email) {
//        if (repository.findByUserEmail(email).isPresent())
//            return repository.findByUserEmail(email).get().getId();
//        return null;
//    }


    @GetMapping("/getprofile/{email}")
    public CompanyProfile getCompanyProfileByEmail(@PathVariable String email) {
        if (repository.findByUserEmail(email).isPresent())
            return repository.findByUserEmail(email).get();
        return null;
    }


//    @GetMapping("/getprofile/{id}")
//    public CompanyProfile getCompanyProfileById(@PathVariable String id) {
//        System.out.println("co jest");
//        if (repository.findById(id).isPresent()){
//            return repository.findById(id).get();
//        }
//        return null;
//    }

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
//
//
    @GetMapping("/createprofile/description/{id}")
    public Description getCompanyProfileDescription(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getDescription();
        return null;
    }
//
//
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

        if (profile.isPresent()) {
            profile.get().getOfferDetails().add(details);
            repository.save(profile.get());
        }

        return profile;
    }


    @GetMapping("/offer/{id}")
    public java.util.List<OfferDetails> getCompanyOffers(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getOfferDetails();
        return null;
    }


//    @DeleteMapping("/delete/offer/{id}")
//    public Optional<CompanyProfile> deleteApplication(@PathVariable String id,
//                                                      @RequestBody ApplicationHolder toDelete) {
//
//        Optional<CompanyProfile> profile = repository.findById(id);
//
//        if (profile.isPresent()){
//            int tempIndex=0;
//            System.out.println("size:"+profile.get().getOfferDetails().size());
//            for(int i =0; i< profile.get().getOfferDetails().get((toDelete.getOfferDetailsIndex())).getApplications().length; i++){
//                System.out.println("xd");
//                profile.get().getOfferDetails().get(0).getApplications()[0];
//                System.out.println(profile.get().getOfferDetails().get(toDelete.getOfferDetailsIndex()).getApplications());
//                if (profile.get().getOfferDetails().get(toDelete.getOfferDetailsIndex()).getApplications()[i].equals(toDelete.getApplicationID()))
//                    tempIndex=i;
//            }
//            System.out.println("tempIndex"+ tempIndex);
////            profile.get().getOfferDetails().remove(tempIndex);
//            profile.get().getOfferDetails().remove(tempIndex);
//
//            repository.save(profile.get());
//            return Optional.of(profile.get());
//        }
//
//        return Optional.empty();
//    }
}
