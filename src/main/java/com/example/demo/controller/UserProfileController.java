package com.example.demo.controller;

import com.example.demo.ExtraClasses.*;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/userprofile")
public class UserProfileController {
    @Autowired
    UserProfileRepository repository;

    @PostMapping("/createprofile/{email}")
    public String createUserProfile(@PathVariable String email) {

        if (repository.findByUserEmail(email).isEmpty()){
            UserProfile profile = new UserProfile();
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
    public UserProfile getUserProfile(@PathVariable String email) {
        if (repository.findByUserEmail(email).isPresent())
            return repository.findByUserEmail(email).get();
        return null;
    }

    @PutMapping("/createprofile/description/{id}")
    public void createUserProfileDescription(
            @RequestBody Description descr,
            @PathVariable String id
    ) {
        Optional<UserProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            profile.get().setDescription(descr);
            repository.save(profile.get());
        }
    }

    @PutMapping("/createprofile/skills/{id}")
    public void createUserProfileSkills(
            @RequestBody Skills skill,
            @PathVariable String id
    )  {
        Optional<UserProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            profile.get().setSkills(skill);
            repository.save(profile.get());
        }
    }

    @PutMapping("/createprofile/experience/{id}")
    public void createUserProfileExperience(
            @RequestBody ArrayList<Experience> exp,
            @PathVariable String id
    ) {
            Optional<UserProfile> profile = repository.findById(id);
            if(profile.isPresent()){
                profile.get().setExperience(exp);
                repository.save(profile.get());
            }
    }

    @PutMapping("/createprofile/project/{id}")
    public void createUserProfileExperience(
            @RequestBody List<Project> project,
            @PathVariable String id
    ) {
            Optional<UserProfile> profile = repository.findById(id);
            if (profile.isPresent()){
                profile.get().setProjects(project);
                repository.save(profile.get());
            }
    }

    @GetMapping("/createprofile/description/{id}")
    public Description getUserProfileDescription(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getDescription();
        return null;
    }

    @GetMapping("/createprofile/skills/{id}")
    public Skills getUserProfileSkills(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getSkills();
        return null;
    }

    @GetMapping("/createprofile/experience/{id}")
    public List<Experience> getUserProfileExperience(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getExperience();
        return null;
    }


    @GetMapping("/createprofile/project/{id}")
    public List<Project> getUserProfileProjects(@PathVariable String id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get().getProjects();
        return null;
    }


    @DeleteMapping("/delete/description/{id}")
    public UserProfile deleteUserProfileDescription(@PathVariable String id) {
        Optional<UserProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            profile.get().setDescription(null);
            repository.save(profile.get());
            return profile.get();
        }
        return null;
    }



    @DeleteMapping("/delete/skills/{id}")
    public UserProfile deleteUserProfileSkills
            (
                    @RequestBody SkillCoursesHolder toDelete,
                    @PathVariable String id) {

        Optional<UserProfile> profile = repository.findById(id);
        if (profile.isPresent()){
            List<Skill> tempSkills = profile.get().getSkills().getSkill();
            List<String> tempCourses = profile.get().getSkills().getCourses();

            List<Skill> listSkills = tempSkills.stream()
                    .filter((Skill obj) -> {
                        return !obj.getSkillName().equals(toDelete.getSkillName());
                    })
                    .toList();

            List<String> listCourses = tempCourses.stream()
                    .filter((String obj) -> {
                        return !obj.equals(toDelete.getCourse());
                    })
                    .toList();

            for(Skill i:listSkills){
                System.out.println(i.getSkillName() + " " + i.getSkillLevel());
            }

            for(String i:listCourses){
                System.out.println(i);
            }

            Skills output = new Skills(listSkills, listCourses);
            profile.get().setSkills(output);
            repository.save(profile.get());
            return profile.get();
        }
        return null;
    }

    @DeleteMapping("/delete/experience/{id}")
    public UserProfile deleteUserProfileExperience(@PathVariable String id,
                                                   @RequestBody Experience exp) {

        Optional<UserProfile> profile = repository.findById(id);
        List<Experience> basicList = profile.get().getExperience();

        List<Experience> finalList = basicList.stream()
                .filter((Experience obj) -> {
                    return !(obj.getCompanyName().equals(exp.getCompanyName()));
                })
                .toList();

        profile.get().setExperience(finalList);
        repository.save(profile.get());
        return profile.get();
    }

    @DeleteMapping("/delete/project/{id}")
    public UserProfile deleteUserProfileProject(@PathVariable String id,
                                                @RequestBody Project toDelete) {

            Optional<UserProfile> profile = repository.findById(id);

            if (profile.isPresent()){
                List<Project> basicList = profile.get().getProjects();

                List<Project> finalList = basicList.stream()
                        .filter((Project obj) -> {
                            return !(obj.getProjectName().equals(toDelete.getProjectName()));
                        })
                        .toList();

                profile.get().setProjects(finalList);
                repository.save(profile.get());
                return profile.get();
            }
            return null;
    }
}
