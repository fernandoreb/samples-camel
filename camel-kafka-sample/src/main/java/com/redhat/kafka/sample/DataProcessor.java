package com.redhat.kafka.sample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Component
public class DataProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info("Dado Recebido:"+exchange.getIn().getBody());
        
        //algum processamento ou transformação
        exchange.getMessage().setHeader("CamelFileName", getRandomNumber()+".txt");
        
    }

    //Gerar um número randomico
    private int getRandomNumber() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

}
