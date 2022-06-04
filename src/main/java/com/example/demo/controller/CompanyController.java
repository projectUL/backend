package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
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

        List<Company> list = new ArrayList<>();
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
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Company> getOneCompany(@PathVariable String id)
    {
        return repository.findById(id);
    }

}
