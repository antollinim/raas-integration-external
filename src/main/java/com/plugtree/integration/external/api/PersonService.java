package com.plugtree.integration.external.api;

public class PersonService {

    public PersonService() {
        Person one = new Person("John", "Doe");
    }
    
    public Person getPerson(){
        return new Person("John" + Math.random()*1000, "Doe");
    }
}
