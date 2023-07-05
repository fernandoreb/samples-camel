package com.redhat.kafka.sample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class KafkaConsumerCamelRoute extends RouteBuilder {

    @Autowired
	private DataProcessor dataProcessor;

    @Override
    public void configure() throws Exception {

        //hawtio
        //http://localhost:8081/actuator/hawtio/camel/routeDiagram?
        from("kafka:{{topic.name.consumer}}?brokers={{kafka.bootstrap-servers}}"+
                                           "&securityProtocol={{kafka.security.protocol}}"+
                                           "&saslMechanism={{kafka.consumer.properties.sasl.mechanism}}"+
                                           "&sslTruststoreLocation={{kafka.consumer.ssl.trust-store-location}}"+
                                           "&sslTruststorePassword={{kafka.consumer.ssl.trust-store-password}}"+
                                           "&sslTruststoreType={{kafka.consumer.ssl.trust-store-type}}"+
                                           "&saslJaasConfig={{kafka.consumer.properties.sasl.jaas.config}}"+
                                           "&groupId={{kafka.consumer.group-id}}"+
                                           "&consumersCount={{kafka.consumer.count}}"+
                                           "&autoOffsetReset={{kafka.consumer.auto-offset-reset}}")
            .routeId("KafkaConsumerCamelRoute").log("KafkaConsumerCamelRoute started")
            .process(dataProcessor).id("data-processor")
            .log("KafkaConsumerCamelRoute file: {{file.diretory}}/${header.CamelFileName}")
            .to("file:{{file.diretory}}")
            //.to("sftp://{{ftp.user}}@{{ftp.server}}:{{ftp.port}}/{{ftp.directory}}?password={{ftp.password}}")
            .to("sftp://{{ftp.user}}@{{ftp.server}}:{{ftp.port}}/{{ftp.directory}}?password={{ftp.password}}&knownHostsFile=/home/fguimara/.ssh/known_hosts&strictHostKeyChecking=no")
            .log("KafkaConsumerCamelRoute ended");
       
    }

}