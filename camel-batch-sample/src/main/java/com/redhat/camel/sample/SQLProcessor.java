package com.redhat.camel.sample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Component
public class SQLProcessor implements Processor {

    @PropertyInject("{{insert.query}}")
    private String insertQuery;

    private final static Logger LOGGER = LoggerFactory.getLogger(SQLProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        // fill up random values and generate query
        String random = exchange.getMessage().getBody(String.class);
        LOGGER.info("String randomica: "+random);
        
        String query = String.format(insertQuery, getRandomNumber(),"Randomico", random );
        LOGGER.info("Query a ser executada:"+query);
        // set query to exchange
        exchange.getIn().setBody(query);
    }

    // provides random number as ID
    private int getRandomNumber() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}
