package com.example.demo0713.lammbda.demo2;

import com.example.demo0713.lammbda.demo1.Persons;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Demo08InvokeCalc {
    public static void main(String[] args) { 
        // TODO 请在此使用Lambda【标准格式】调用invokeCalc方法来计算120+130的结果ß
        invokeCalc(120,130, Integer::sum);
        invokeCalc(120, 130, Integer::min);
        testLambda();
    }
    private static void invokeCalc(int a, int b, Calculator calculator) { 
        int result = calculator.calc(a, b);
        System.out.println("结果是：" + result);
    }

    public static void testLambda() {
        List<Persons> javaProgrammers = new ArrayList<Persons>() {
            {
                add(new Persons("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
                add(new Persons("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
                add(new Persons("Floyd", "Donny", "Java programmer", "male", 33, 1800));
                add(new Persons("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
                add(new Persons("Vere", "Hervey", "Java programmer", "male", 22, 1200));
                add(new Persons("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
                add(new Persons("Shawn", "Randall", "Java programmer", "male", 30, 2300));
                add(new Persons("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
                add(new Persons("Palmer", "Dene", "Java programmer", "male", 33, 2000));
                add(new Persons("Addison", "Pam", "Java programmer", "female", 34, 1300));
            }
        };

        Consumer<Persons> giveRaise = e -> e.setSalary(e.getSalary()/100*5 + e.getSalary());
        Persons pers = javaProgrammers.stream().min(Comparator.comparingInt(Persons::getSalary)).get();
        Persons pers2 = javaProgrammers.stream().max(Comparator.comparingInt(Persons::getSalary)).get();
        System.out.printf("Name: %s %s; Salary: $%,d.", pers.getFirstName(), pers.getLastName(), pers.getSalary());
        System.out.println();
        System.out.printf("Name: %s %s; Salary: $%,d.", pers2.getFirstName(), pers2.getLastName(), pers2.getSalary());

        javaProgrammers.stream()
//                .filter(x->x.getSalary()>2)
//                .sorted((p,p2)-> p.getFirstName().compareTo(p2.getFirstName()))
//                .sorted(Comparator.comparing(Persons::getFirstName))
//                .min((p, p2) -> p.getSalary() - p2.getSalary()).get();
//                .limit(20)
//                .max(Comparator.comparingInt(Persons::getSalary)).get();
                .forEach(x -> System.out.printf("%s, %s: \n", x.getFirstName(), x.getSalary() ));
//                .min(Comparator.comparingInt(Persons::getSalary)).get();

        javaProgrammers.parallelStream()
                .map(Persons::getLastName)
//                .collect(Collectors.toSet());
//                .collect(Collectors.toCollection(TreeSet::new));
//                .collect(Collectors.joining(";"))
            ;
        IntSummaryStatistics totalSalary = javaProgrammers.parallelStream()
                .mapToInt(p -> p.getSalary())
                .summaryStatistics();
        System.out.println( System.nanoTime());
        System.out.println( System.currentTimeMillis());
    }




}
