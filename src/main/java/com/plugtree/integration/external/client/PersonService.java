package com.plugtree.integration.external.client;

import java.util.Date;

import com.plugtree.integration.model.Person;

public class PersonService {

    private boolean sent = false; 
    private Person p1 = null;
    
    public PersonService() {
           p1 = new Person();
           p1.setBirthDate(new Date());
           p1.setName("John Doe");
           p1.setNumberOfChildren(3);
    }
    
    public Person getPerson(){
        if(!sent) {
            sent = true;
            return p1;
        }
        return null;
    }
    
}
