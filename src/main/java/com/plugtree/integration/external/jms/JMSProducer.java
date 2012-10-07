/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plugtree.integration.external.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Producer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version 
 */
public class JMSProducer {

    public static void main(final String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("camel-client.xml");
        CamelContext camel = context.getBean("camel-client", CamelContext.class);

        // get the endpoint from the camel context
        Endpoint endpoint = camel.getEndpoint("jms:queue:numbers");

        // create the exchange used for the communication
        // we use the in out pattern for a synchronized exchange where we expect a response
        Exchange exchange = endpoint.createExchange(ExchangePattern.InOut);
        // set the input on the in body
        // must you correct type to match the expected type of an Integer object
        Map<String, String> props = new HashMap<String, String>();
        props.put("key1", "value1");
        props.put("key2", "value2");
        props.put("key3", "value3");
        
        exchange.getIn().setBody(props);

        // to send the exchange we need an producer to do it for us
        Producer producer = endpoint.createProducer();
        // start the producer so it can operate
        producer.start();

        // let the producer process the exchange where it does all the work in this oneline of code
        producer.process(exchange);

        // get the response from the out body and cast it to an integer
        String response = exchange.getOut().getBody(String.class);
        System.out.println("... the result is: " + response);
        System.exit(0);

    }

    //    public static void main(final String[] args) throws Exception {
    //		System.out.println("Notice this client requires that the CamelServer is already running!");
    //
    //		ApplicationContext context = new ClassPathXmlApplicationContext("camel-client.xml");
    //
    //		// get the camel template for Spring template style sending of messages (= producer)
    //		ProducerTemplate camelTemplate = context.getBean("camelTemplate", ProducerTemplate.class);
    //
    //		System.out.println("Invoking the multiply with 22");
    //		// as opposed to the CamelClientRemoting example we need to define the service URI in this java code
    //		//camelTemplate.sendBody("jms:queue:numbers", ExchangePattern.OutOnly, 22);
    //		
    //		camelTemplate.sendBody("jms:queue:numbers", ExchangePattern.OutOnly, 45);
    //		//System.out.println("... the result is: " + response);
    //
    //		System.exit(0);
    //	}

}
