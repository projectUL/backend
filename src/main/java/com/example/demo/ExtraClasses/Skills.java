package com.example.demo.ExtraClasses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Skills {

//    Skill[] skills;
//    String[] courses;
    public Skills(List<Skill> skill, List<String> courses){
        this.skill = skill;
        this.courses = courses;
    }

    List<Skill> skill = new ArrayList<Skill>();
    List<String> courses = new ArrayList<String>();
}
