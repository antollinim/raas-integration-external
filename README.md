raas-integration-external
=========================

Sample external system exposing data to be consumed by raas-integration

Reference: http://camel.apache.org/tutorial-jmsremoting.html

http://hwellmann.blogspot.com.ar/2011/03/transparent-asynchronous-remoting-via.html
http://camel.apache.org/manual/camel-manual-2.6.0.html
http://camel.apache.org/pojo-messaging-example.html

How to Run
==========
As a standard java main application - just start its main class.

We can also start the clients using maven:
mvn compile exec:java -PCamelClient