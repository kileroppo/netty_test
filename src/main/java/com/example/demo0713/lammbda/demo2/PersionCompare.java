package com.example.demo0713.lammbda.demo2;

import java.util.Arrays;
import java.util.Comparator;

public class PersionCompare {

    public static void main(String[] args) {
        Person[] array = {
                new Person("xh01", 19),
                new Person("xh02", 18),
                new Person("xh03", 20) };
        Comparator<Person > com = new Comparator<Person >() {
            @Override
            public int compare(Person  o1, Person  o2) {
                return o1.getAge() - o2.getAge();
            }

        };

        Arrays.sort(array,(Person p1, Person p2)->{
            return p1.getAge() - p2.getAge();
        });

    }
}
