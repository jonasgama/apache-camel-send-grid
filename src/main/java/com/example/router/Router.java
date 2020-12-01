package com.example.router;

import com.example.model.CallCenterRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Service;


@Service
public class Router extends RouteBuilder {



    @Override
    public void configure() throws Exception {
        from("{{route.from}}")
                .log("Retrieved from the TOPIC :${body}")
                .unmarshal()
                .json(JsonLibrary.Gson, CallCenterRequest.class)
                .pollEnrich("{{file.path}}=${body.fileName}{{noop.config}}")
                .log("${body}");
    }


}
