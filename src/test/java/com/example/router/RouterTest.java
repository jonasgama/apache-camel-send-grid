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
import org.springframework.core.env.Environment;
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

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private Environment env;

    @Test
    public void shouldSendMail(){
        producerTemplate.sendBody(env.getProperty("route.from"), jsonInput());
        producerTemplate.sendBody(env.getProperty("route.from"), jsonInput2());
    }

    private String jsonInput(){
        CallCenterRequest callCenterRequest = new CallCenterRequest();
        callCenterRequest.setFileName("sample.pdf");
        return new Gson().toJson(callCenterRequest);
    }

    private String jsonInput2(){
        CallCenterRequest callCenterRequest = new CallCenterRequest();
        callCenterRequest.setFileName("sample - Copia.pdf");
        return new Gson().toJson(callCenterRequest);
    }

}
