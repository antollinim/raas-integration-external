package com.plugtree.integration.external.jms;

import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.plugtree.integration.model.Person;

public class JMSClient {

    //@Produce(uri = "activemq:personnel.records")
    @Produce(uri = "activemq:events.model")
    ProducerTemplate producer;

    @Consume(uri = "bean:personService?method=getPerson")
    public void onSendToQueue(Person person) {
        System.out.println("Sending...");
        if(person != null){
            producer.sendBody(person);
        }
    }
    
    public JMSClient() {
        
    }
    
    public void sendMessage(){
//        System.out.println("About to send message");
//        Person person = new Person("John", "Doe");
//        producer.sendBody(person);
    }
    
//    public static void main(String[] args) {
//        new JMSClient().sendMessage();
//    }
    
    
}
