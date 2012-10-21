package com.plugtree.integration.external.client;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.CamelContextFactoryBean;
import org.apache.camel.spring.remoting.CamelProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration  
public class GreeterClientSpringConfig {  
      
    @Inject  
    private ApplicationContext applicationContext;  
  
    @Bean  
    public ConnectionFactory cf() throws JMSException {  
        //com.sun.messaging.ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        //connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, "localhost:7676");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
  
        return connectionFactory;  
    }  
      
    @Bean  
    public GreeterClient greeterClient() {  
        return new GreeterClient();  
    }  
      
    @Bean  
    public JmsComponent jms() throws JMSException {  
        JmsComponent jms = new JmsComponent();  
        jms.setConnectionFactory(cf());  
        return jms;  
    }  
      
    @Bean  
    public HelloService helloProxy() throws Exception {  
        CamelProxyFactoryBean factory = new CamelProxyFactoryBean();  
        factory.setServiceInterface(HelloService.class);  
        factory.setServiceUrl("jms:queue:hello");  
        factory.setCamelContext(camelContext());  
        factory.afterPropertiesSet();  
        return (HelloService) factory.getObject();  
    }  
      
    @Bean  
    public GoodbyeService goodbyeProxy() throws Exception {  
        CamelProxyFactoryBean factory = new CamelProxyFactoryBean();  
        factory.setServiceInterface(GoodbyeService.class);  
        factory.setServiceUrl("jms:queue:goodbye");  
        factory.setCamelContext(camelContext());  
        factory.afterPropertiesSet();  
        return (GoodbyeService) factory.getObject();  
    }  
      
    @Bean  
    public CamelContext camelContext() throws Exception {  
        CamelContextFactoryBean factory = new CamelContextFactoryBean();  
        factory.setApplicationContext(applicationContext);  
        factory.setId("jms-client");  
        factory.afterPropertiesSet();  
        return (CamelContext) factory.getObject();  
    }  
} 
