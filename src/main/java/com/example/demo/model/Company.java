package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="companies")
public class Company {

    @Id
    private String id;
    private String companyName;
    private String companyWebsite;
    private String companyLogo;
    private String companyOverview;
    private String companyMail;
    private String[] jobs;
}
