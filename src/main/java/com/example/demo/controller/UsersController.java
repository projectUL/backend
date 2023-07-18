package com.example.demo.controller;

import com.example.demo.ExtraClasses.Application;
import com.example.demo.ExtraClasses.UserEdit;
import com.example.demo.ExtraClasses.UserRegister;
import com.example.demo.model.Admin;
import com.example.demo.model.OfferDetails;
import com.example.demo.model.UserProfile;
import com.example.demo.model.Users;
import com.example.demo.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepository repository;

    @Autowired
    UserProfileRepository userRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    OfferDetailsRepository offerRepo;

    
    @GetMapping("/id/{id}")
    Optional<Users> getUser(@PathVariable String id){
        return repository.findById(id);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegister register) {
        Map<String, Object> response = new HashMap<>();

        Users user = new Users();
        user.setEmail(register.getEmail());
        user.setApplications(new Application[0]);

        if(register.getCompanyName() == null || register.getCompanyName().isBlank())
        {
            user.setIsEmployer(false);
            user.setCompanyName("");
            user.setAccessLevel(1);
        }
        else
        {
            user.setIsEmployer(true);
            user.setCompanyName(register.getCompanyName());
            user.setAccessLevel(2);
        }


        if((user.getEmail().contains("edu.") && user.getIsEmployer() == false) || (!user.getEmail().contains("edu.") && user.getIsEmployer() == true))
        {
            if (repository.existsByEmail(user.getEmail()))
            {
                response.put("errorMessage", "User already exists");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }
            else
            {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                user.setPassword(encoder.encode(register.getPassword()));

                Long now = System.currentTimeMillis();
                String token = Jwts.builder().setSubject(user.getEmail()).claim("email", user.getEmail()).claim("accessLevel", user.getAccessLevel()).setExpiration(new Date(now + 3600000)).signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode("SafestPassEver")).compact();

                repository.save(user);
                response.put("email", user.getEmail());
                response.put("accessLevel", user.getAccessLevel());
                response.put("token", token);
                response.put("expiresIn", 3600);

                if(user.getIsEmployer())
                {
                    Admin admin = new Admin();
                    admin.setCompanyName(user.getCompanyName());
                    admin.setCompanyMail(user.getEmail());
                    admin.setIsAccepted(false);
                    admin.setIsVerified(false);
                    adminRepo.save(admin);

                }

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        else if (user.getEmail().contains("edu.") && user.getIsEmployer() == true)
        {
            response.put("errorMessage", "Student can not create company account");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        else
        {
            response.put("errorMessage", "User is not a student");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Users user) {

        Map<String, Object> response = new HashMap<>();

        if (repository.existsByEmail(user.getEmail())) {
            Optional<Users> usr = repository.findByEmail(user.getEmail());
            String PassDB = usr.get().getPassword();
            int accessLvl = usr.get().getAccessLevel();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(usr.get().getIsEmployer())
            {
                String em = usr.get().getEmail();

                Admin admin = adminRepo.findByCompanyMail(em);
                if(admin.getIsAccepted() == false)
                {
                    response.put("errorMessage", "Company is not accepted");
                    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
                }
            }

            if (encoder.matches(user.getPassword(), PassDB))
            {
                Long now = System.currentTimeMillis();
                String token = Jwts.builder().setSubject(user.getEmail()).claim("email", user.getEmail()).claim("accessLevel", accessLvl).setExpiration(new Date(now + 36000000)).signWith(SignatureAlgorithm.HS256, "SafestPassEver").compact();

                response.put("email", user.getEmail());
                response.put("accessLevel", accessLvl);
                response.put("token", token);
                response.put("expiresIn", 3600);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("errorMessage", "Password is incorect");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }

        }
        else
        {
            response.put("errorMessage", "User does not exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{email}")
    @ResponseBody
    public Optional<Users> getUerByEmail(@PathVariable String email) {
        return repository.findByEmail(email);
    }


    @PutMapping("/edit/email")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editUserEmail(@RequestBody UserEdit editUser) {
        Map<String, Object> response = new HashMap<>();

        if (repository.existsByEmail(editUser.getNewEmail())) {
            response.put("errorMessage", "Email is already taken");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else if (editUser.getNewEmail().equals(editUser.getOldEmail())) {
            response.put("errorMessage", "Provided same email address");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            Users user = repository.findByEmail(editUser.getOldEmail()).get();
            user.setEmail(editUser.getNewEmail());
            repository.save(user);
            response.put("email", user.getEmail());


            UserProfile profile = userRepo.findByUserEmail(editUser.getOldEmail()).get();
            profile.setUserEmail(editUser.getNewEmail());
            userRepo.save(profile);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/edit/pswd")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editUserPassword(@RequestBody UserEdit editUser) {
        Map<String, Object> response = new HashMap<>();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Users user = repository.findByEmail(editUser.getOldEmail()).get();

        if (encoder.matches(editUser.getOldPassword(), user.getPassword())) {
            if (editUser.getNewPassword().equals(editUser.getConfirmNew())) {
                if (editUser.getOldPassword().equals(editUser.getNewPassword())) {
                    response.put("errorMessage", "Same old and new password");
                    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
                } else {
                    user.setPassword(encoder.encode(editUser.getNewPassword()));
                    repository.save(user);

                    response.put("email", user.getEmail());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.put("errorMessage", "Confirmation password is incorrect");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            response.put("errorMessage", "Old password is incorrect");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/apply/{email}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addApplication(@PathVariable String email, @RequestBody Application application)
    {
        Users user = repository.findByEmail(email).get();
        int size = user.getApplications().length;

        Application[] newApplications = new Application[size+1];

        for(int i=0; i<size; i++)
        {
            newApplications[i] = user.getApplications()[i];
        }

        newApplications[size] = application;

        user.setApplications(newApplications);
        repository.save(user);

        OfferDetails offers = offerRepo.findById(application.getOfferID()).get();

        List<String> applicationUsers = offers.getApplications();
        applicationUsers.add(application.getUserID());
        offers.setApplications(applicationUsers);
        offerRepo.save(offers);


        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/change/{email}/{offerID}/{status}")
    @ResponseBody
    public String chageStatus(@PathVariable String email,@PathVariable String offerID, @PathVariable int status)
    {
        Users user = repository.findByEmail(email).get();
        Application[] applications = user.getApplications();
        int pos = 0;
        String stat = "";

        for(int i=0; i< applications.length; i++)
        {
            if(applications[i].getOfferID().equals(offerID))
            {
                pos = i;
            }
        }

        switch(status)
        {
            case 0:
                stat = "Rejected";
                break;
            case 1:
                stat = "Pending";
                break;
            case 2:
                stat = "Accepted";
                break;
        }

        applications[pos].setStatus(stat);
        user.setApplications(applications);
        return repository.save(user).getId();
    }    
}
