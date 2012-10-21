package com.plugtree.integration.external.client;

import org.apache.camel.InOnly;

import com.plugtree.integration.model.Person;

@InOnly   
public interface HelloService {  

    void sayHello(Person person);

}  