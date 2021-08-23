package com.example.spring01;

import lombok.Data;

import java.util.List;

@Data
public class Home {

    List<Person> persons;


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void printHomeInfo(){
        System.out.println(getPersons());
//        persons.parallelStream().filter(v->v.getAge()>20).forEach(
//                v->v.print()
//        );
    }

}
