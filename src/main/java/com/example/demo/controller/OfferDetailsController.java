package com.example.demo.controller;

import com.example.demo.model.OfferDetails;
import com.example.demo.repository.OfferDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/offers")
public class OfferDetailsController {

    @Autowired
    OfferDetailsRepository repository;

    @GetMapping("/search/{category}/{type}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchOffers(@PathVariable("category") String category, @PathVariable("type") String type,@RequestParam("q") Optional<String> q, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "1") int page)
    {
        int rightPage = page - 1;
        Pageable pageable = PageRequest.of(rightPage, size);

        List<OfferDetails> list = new ArrayList<>();
        Page<OfferDetails> paging;
        
        if(q.isPresent())
        {
            if(category.equals("all") && type.equals("all"))
            {
                paging = repository.findByName(q, pageable);
            }
            else if(type.equals("all"))
            {
                paging = repository.findByCategoryAndName(category, q, pageable);
            }
            else if(category.equals("all"))
            {
                paging = repository.findByJobTypeAndName(type, q, pageable);
            }
            else
            {
                paging = repository.findByCategoryAndJobTypeAndName(category, type, q, pageable);
            }          
        }
        else
        {
            if(category.equals("all") && type.equals("all"))
            {
                paging = repository.findAll(pageable);
            }
            else if(type.equals("all"))
            {
                paging = repository.findByCategory(category, pageable);
            }
            else if(category.equals("all"))
            {
                paging = repository.findByJobType(type, pageable);
            }
            else
            {
                paging = repository.findByCategoryAndJobType(category, type, pageable);
            }
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
    Optional<OfferDetails> getOffer(@PathVariable String id)
    {
        return repository.findById(id);
    }

}
