package com.redhat.camel.sample;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CronjobCamelRoute extends RouteBuilder {

    @Autowired
	private SQLProcessor sqlProcessor;
    
    @Override
    public void configure() throws Exception {
        from("quartz://execucaoAutomatica/processarApis?cron={{scheduler.cron.expression}}")
            .to("direct:processar").routeId("execucaoAutomatica");
        
        from("direct:processar").routeId("processar-dados")
            .log("job camel sample started")
            .to("{{api.random}}/random")
            .process(sqlProcessor).id("sql-processor")
            .to("jdbc:dataSource").id("jdbc-endpoint")
            .log("job camel sample finished");
        
    }

}