package com.redhat.kafka.sample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;

@Component
public class KafkaConsumerCamelRoute extends RouteBuilder {

    @Autowired
	private DataProcessor dataProcessor;

    @Autowired
    @Qualifier("errorDlqBuilder")
    private DefaultErrorHandlerBuilder errorDlqBuilder;
    
    @Override
    public void configure() throws Exception {

        //hawtio
        //http://localhost:8081/actuator/hawtio/camel/routeDiagram?
        errorHandler(errorDlqBuilder);
        
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
            .to("sftp://{{ftp.user}}@{{ftp.server}}:{{ftp.port}}/{{ftp.directory}}?password={{ftp.password}}")
            .onException(Exception.class)
            .handled(true)
            .log("KafkaConsumerCamelRoute ended");

        
        from("direct:error")
            .log("erro ao processar rota");
       
    }

}