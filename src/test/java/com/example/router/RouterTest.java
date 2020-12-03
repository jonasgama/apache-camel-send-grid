package com.example.router;

import com.example.SpringApp;
import com.example.model.ClientRequest;
import com.google.gson.Gson;
import org.apache.camel.ProducerTemplate;
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
    }

    private String jsonInput(){
        ClientRequest callCenterRequest = new ClientRequest();
        callCenterRequest.setFileName("sample.pdf");
        return new Gson().toJson(callCenterRequest);
    }
}
