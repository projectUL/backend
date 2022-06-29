package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository repository;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCompanies(@RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "1") int page)
    {
        int rightPage = page - 1;
        Pageable pageable = PageRequest.of(rightPage, size);

        List<Admin> list = new ArrayList<>();
        Page<Admin> paging;

        paging = repository.findAll(pageable);

        list = paging.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("pages", paging.getTotalPages());
        response.put("next", paging.hasNext());
        response.put("previous", paging.hasPrevious());
        response.put("data", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyCompany(@PathVariable String email, @RequestParam("verify") boolean ver)
    {
        Admin admin = repository.findByCompanyMail(email);
        admin.setIsAccepted(ver);
        repository.save(admin);

        Map<String, Object> response = new HashMap<>();
        response.put("name", admin.getCompanyName());
        response.put("email", admin.getCompanyMail());
        response.put("accepted", admin.getIsAccepted());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}