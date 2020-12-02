package com.example.router;

import com.example.model.CallCenterRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class Router extends RouteBuilder {


    @Value("${timeout.polling}")
    private Long timeout;

    @Autowired
    private EmailProcessor process;

    @Override
    public void configure() throws Exception {
        from("{{route.from}}")
                .log("Retrieved from the TOPIC :${body}")
                .unmarshal()
                .json(JsonLibrary.Gson, CallCenterRequest.class)
                .pollEnrich("{{file.path}}=${body.fileName}{{noop.config}}", timeout)
                .convertBodyTo(File.class)
                .process(MailProcessor)
    }


}
