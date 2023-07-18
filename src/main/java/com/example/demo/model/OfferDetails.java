package com.example.demo.model;

import com.example.demo.ExtraClasses.JobDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="offerDetails")
public class OfferDetails {

    @Id
    private String id;
    String offerTitle;
    String companyOverview;
    String[] duties;
    String[] expectations;
    String[] weOffer;
    JobDetail jobDetail;
    String category;
    String jobType;
    String[] tags;
    String companyLogo;
    String companyName;
    List<String> applications = new ArrayList<String>();
    Date created;
}
