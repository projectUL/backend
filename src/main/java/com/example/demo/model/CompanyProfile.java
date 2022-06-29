package com.example.demo.model;

import com.example.demo.ExtraClasses.Description;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection="companyProfile")
public class CompanyProfile {

    @Id
    private String id;
    Description description;
    String userEmail;
    List<OfferDetails> offerDetails = new ArrayList<OfferDetails>();

}
