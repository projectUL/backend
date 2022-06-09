package com.example.demo.controller;

import com.example.demo.ExtraClasses.Description;
import com.example.demo.ExtraClasses.Experience;
import com.example.demo.ExtraClasses.Project;
import com.example.demo.ExtraClasses.Skills;
import com.example.demo.model.UserProfile;
import com.example.demo.model.Users;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/userprofile")
public class UserProfileController {
    @Autowired
    UserProfileRepository repository;

    @PostMapping("/createprofile/{email}")
    public String createUserProfile(@PathVariable String email) {
        UserProfile profile = new UserProfile();
        profile.setUserEmail(email);
        return repository.save(profile).getId();
    }


    @GetMapping("/{email}")
    public String getUserId(@PathVariable String email) {
        return repository.findByUserEmail(email).get().getId();
    }


    @GetMapping("/getprofile/{email}")
    public UserProfile getUserProfile(@PathVariable String email) {
        return repository.findByUserEmail(email).get();
    }

    @PutMapping("/createprofile/description/{id}")
    public void createUserProfileDescription(
            @RequestBody Description descr,
            @PathVariable String id
    ) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setDescription(descr);
        repository.save(profile.get());
    }

    @PutMapping("/createprofile/skills/{id}")
    public void createUserProfileSkills(
            @RequestBody Skills skill,
            @PathVariable String id
    ) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setSkills(skill);
        repository.save(profile.get());
    }

    @PutMapping("/createprofile/experience/{id}")
    public void createUserProfileExperience(
            @RequestBody Experience[] exp,
            @PathVariable String id
    ) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setExperience(exp);
        repository.save(profile.get());
    }

    @PutMapping("/createprofile/project/{id}")
    public void createUserProfileExperience(
            @RequestBody Project[] project,
            @PathVariable String id
    ) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setProjects(project);
        repository.save(profile.get());
    }

    @GetMapping("/createprofile/description/{id}")
    public Description getUserProfileDescription(@PathVariable String id) {
        return repository.findById(id).get().getDescription();
    }

    @GetMapping("/createprofile/skills/{id}")
    public Skills getUserProfileSkills(@PathVariable String id) {
        return repository.findById(id).get().getSkills();
    }

    @GetMapping("/createprofile/experience/{id}")
    public Experience[] getUserProfileExperience(@PathVariable String id) {
        return repository.findById(id).get().getExperience();
    }

    @GetMapping("/createprofile/project/{id}")
    public Project[] getUserProfileProjects(@PathVariable String id) {
        return repository.findById(id).get().getProjects();
    }


    @DeleteMapping("/delete/description/{id}")
    public UserProfile deleteUserProfileDescription(@PathVariable String id) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setDescription(null);
        repository.save(profile.get());
        return profile.get();
    }

    @DeleteMapping("/delete/skills/{id}")
    public UserProfile deleteUserProfileSkills(@PathVariable String id) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setSkills(null);
        repository.save(profile.get());
        return profile.get();
    }

    @DeleteMapping("/delete/experience/{id}")
    public UserProfile deleteUserProfileExperience(@PathVariable String id) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setExperience(null);
        repository.save(profile.get());
        return profile.get();
    }

    @DeleteMapping("/delete/project/{id}")
    public UserProfile deleteUserProfileProject(@PathVariable String id) {
        Optional<UserProfile> profile = repository.findById(id);
        profile.get().setProjects(null);
        repository.save(profile.get());
        return profile.get();
    }
}
