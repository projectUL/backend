package com.example.demo.ExtraClasses;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobDetail {

    String location;
    Date starts;
    Date ends;
    int minSalary;
    int maxSalary;
    int numberOfCandidates;
}
