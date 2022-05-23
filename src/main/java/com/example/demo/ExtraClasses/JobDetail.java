package com.example.demo.ExtraClasses;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobDetail {

    String jobType;
    String location;
    String starts;
    String ends;
    int minSalary;
    int maxSalary;
    int numberOfCandidates;
}
