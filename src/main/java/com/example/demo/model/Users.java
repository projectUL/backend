package com.example.demo.model;

import com.example.demo.ExtraClasses.Application;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="users")
public class Users {

    @Id
    private String id;
    private String email;
    private String password;
    private Boolean isEmployer;
    private String companyName;
    private Application[] applications;
    private int accessLevel;
}
