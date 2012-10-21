package com.plugtree.integration.external.jms;

import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.plugtree.integration.model.Person;

public class JMSProducerRouteBuilder extends RouteBuilder {

    //http://camel.apache.org/pojo-messaging-example.html

    public JMSProducerRouteBuilder() {

        CamelContext context = new DefaultCamelContext();

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("camel-client.xml");
        //CamelContext context = appContext.getBean("camel-client", CamelContext.class);

        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //context.addComponent("jms", JmsComponent.jmsComponentClientAcknowledge(connectionFactory));

        //consumer = new DefaultConsumerTemplate(context);

        //ProducerTemplate producer = new DefaultProducerTemplate(context);

        try {
            context.addRoutes(this);
            //producer.start();
            context.start();
            //context.setAutoStartup(true);
            Thread.sleep(15000);
            context.stopRoute("jsonProducer");
            context.stopRoute("pojoProducer");
            context.stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public void configure() throws Exception {

        JsonDataFormat jsonFormat = new JsonDataFormat(JsonLibrary.XStream);
        //jsonFormat.setUnmarshalType(Person.class);

        from("timer://myTimer?fixedRate=true&period=5000")
        .process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                Person person = new Person();
                person.setBirthDate(new Date());
                person.setName("John Doe");
                person.setNumberOfChildren(3);
                exchange.getOut().setBody(person);
            }
        })
        .marshal(jsonFormat).to("activemq:events.json").routeId("jsonProducer");

        from("timer://myTimer?fixedRate=true&period=5000")
        .process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                Person person = new Person();
                person.setBirthDate(new Date());
                person.setName("John Doe");
                person.setNumberOfChildren(3);
                exchange.getOut().setBody(person);
            }
        })
        .to("activemq:events.pojo").routeId("pojoProducer");
    }

}