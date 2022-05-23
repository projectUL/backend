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

@RestController
@RequestMapping("/offers")
public class OfferDetailsController {

    @Autowired
    OfferDetailsRepository repository;

    @GetMapping("/{category}/{type}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOffersCategoryType(@PathVariable("category") String category, @PathVariable("type") String type, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "1") int page)
    {
        int rightPage = page - 1; //Do zmiany w zaleznosci od tego czy strony liczymy od 0 czy od 1
        Pageable pageable = PageRequest.of(rightPage, size);

        List<OfferDetails> list = new ArrayList<>();
        Page<OfferDetails> paging;

        if(category.equals("all"))
        {
            paging = repository.findByJobType(type, pageable);
        }
        else if(type.equals("all"))
        {
            paging = repository.findByCategory(category, pageable);
        }
        else
        {
            paging = repository.findByCategoryAndJobType(category, type, pageable);
        }

        list = paging.getContent();
        Map<String, Object> response = new HashMap<>();

        response.put("pages", paging.getTotalPages());
        response.put("next", paging.hasNext());
        response.put("previous", paging.hasPrevious());
        response.put("data", list);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOffersSearch(@RequestParam("q") Optional<String> q, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "1") int page)
    {
        int rightPage = page - 1;
        Pageable pageable = PageRequest.of(rightPage, size);

        List<OfferDetails> list = new ArrayList<>();
        Page<OfferDetails> paging;

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
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    Optional<OfferDetails> getOffer(@PathVariable String id)
    {
        return repository.findById(id);
    }

}
