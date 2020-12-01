package com.example.router;

import com.example.SpringApp;
import com.example.model.CallCenterRequest;
import com.google.gson.Gson;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringApp.class })
@ActiveProfiles("test")
public class RouterTest {


    @EndpointInject("{{route.from}}")
    private MockEndpoint topic;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void shouldSendMail(){
        producerTemplate.sendBody(topic, jsonInput());
    }

    private String jsonInput(){
        CallCenterRequest callCenterRequest = new CallCenterRequest();
        callCenterRequest.setFileName("sample.pdf");
        return new Gson().toJson(callCenterRequest);
    }

}
