package com.plugtree.integration.external.client;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.plugtree.integration.external.api.GoodbyeService;
import com.plugtree.integration.external.api.HelloService;
import com.plugtree.integration.external.api.Person;

public class GreeterClient implements Runnable {  
    
    private static Logger log = Logger.getLogger(GreeterClient.class);  
  
    @Inject  
    private HelloService helloProxy;  
      
    @Inject  
    private GoodbyeService goodbyeProxy;  
      
    private Person donald = new Person("Donald", "Duck");  
    private Person mickey = new Person("Mickey", "Mouse");  
    private Person charlie = new Person("Charlie", "Brown");  
  
    public void run() {  
        log.info("invoking greeter");  
          
        helloProxy.sayHello(donald);  
        helloProxy.sayHello(mickey);  
        helloProxy.sayHello(charlie);  
  
        goodbyeProxy.sayGoodbye(donald);  
        goodbyeProxy.sayGoodbye(mickey);  
        goodbyeProxy.sayGoodbye(charlie);  
  
        log.info("done");  
    }  
          
    public static void main(String[] args) {  
        ApplicationContext context = new AnnotationConfigApplicationContext(GreeterClientSpringConfig.class);  
        GreeterClient client = context.getBean(GreeterClient.class);  
        client.run();  
    }      
}  
