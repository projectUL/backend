package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyRepository repository;

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCompanySearch(@RequestParam("q") Optional<String> q, @RequestParam(defaultValue = "9") int size, @RequestParam(defaultValue = "1") int page)
    {
        int rightPage = page - 1;
        Pageable pageable = PageRequest.of(rightPage, size);

        List<Company> list;
        Page<Company> paging;

        if(q.isPresent())
        {
            paging = repository.searchByName(q, pageable);
        }
        else
        {
            paging = repository.findAll(pageable);
        }

        list = paging.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("pages", paging.getTotalPages());
        response.put("next", paging.hasNext());
        response.put("previous", paging.hasPrevious());
        response.put("data", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOneCompany(@PathVariable String email)
    {
        Map<String, Object> response = new HashMap<>();

        Optional<Company> company = repository.findById(email);

        if(company.isPresent())
        {
            response.put("data", company);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            response.put("error", "Empty company");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/email/{email}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOneCompany(@PathVariable String email)
    {
        Map<String, Object> response = new HashMap<>();

        Optional<Company> company = repository.findByEmail(email);

        if(company.isPresent())
        {
            response.put("data", company);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            response.put("error", "Empty company");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public String updateOneCompany(@RequestBody Company company)
    {
        Company old = repository.findById(company.getId()).get();
        old = company;
        return repository.save(old).getId();
    }
}
