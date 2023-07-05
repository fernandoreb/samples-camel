package com.redhat.kafka.sample;

import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLQEndpointConfig {
    
    @Value("${redelivery.delay}")
    private Integer redeliveryDelay;

    @Value("${maximum.redeliveries}")
    private Integer maximumRedeliveries;

    @Value("${maximum.redeliveries.delay}")
    private Integer maximumRedeliveryDelay;
    
    @Bean
    public DefaultErrorHandlerBuilder errorDlqBuilder() {
        return defaultConfiguration("direct:error");
    }

    private DefaultErrorHandlerBuilder defaultConfiguration(String endpoint) {
        return new DeadLetterChannelBuilder(endpoint)
                .useOriginalMessage()
                .maximumRedeliveries(maximumRedeliveries)
                .redeliveryDelay(redeliveryDelay)
                .logExhausted(true)
                .logHandled(true)
                ;
    }

}
