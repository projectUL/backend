package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="admin")
public class Admin {

    @Id
    private String id;
    private String companyName;
    private String companyMail;
    private Boolean isAccepted;
}