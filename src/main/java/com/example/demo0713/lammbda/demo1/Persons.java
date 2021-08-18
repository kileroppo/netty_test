package com.example.demo0713.lammbda.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Persons {
 
private String firstName, lastName, job, gender;
private int salary, age;


    public Persons(String firstName, String lastName, String job,
                  String gender, int age, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.job = job;
        this.salary = salary;
    }
// Getter and Setter 
// . . . . .
}