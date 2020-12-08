package com.example.router;

import com.example.model.ClientRequest;
import com.example.aggregate.EmailAggregate;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.logging.Level;

@Service
public class Router extends RouteBuilder {


    @Value("${timeout.polling}")
    private Long timeout;

    @Autowired
    private EmailAggregate emailAggregate;

    private static final Logger LOG = Logger.getLogger( Router.class.getName() );

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .maximumRedeliveries("{{retry}}")
                .handled(true)
                .onRedelivery(exchange -> {
                    Exception camelExceptionCaught = (Exception)exchange.getProperty("CamelExceptionCaught");
                    LOG.log(Level.WARNING, "redelivering... ", camelExceptionCaught);
                    exchange.getIn().setBody(camelExceptionCaught.toString());
                })
                .delay(1000)
                .log(LoggingLevel.ERROR, "Redelivery exhausted:\n${body}");


        from("{{route.from}}")
                .log("Retrieved from the TOPIC :${body}")
                .unmarshal()
                .json(JsonLibrary.Gson, ClientRequest.class)
                .pollEnrich("{{file.path}}=${body.fileName}{{noop.config}}", timeout, emailAggregate)
                .marshal()
                .json(JsonLibrary.Gson)
                .log("Email has been sent: ${body}");

    }


}
