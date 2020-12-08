package com.example.router;

import com.example.SpringApp;
import com.example.model.ClientRequest;
import com.google.gson.Gson;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringApp.class,  RouterTest.SendGridConfigTest.class})
@ActiveProfiles("test")
public class RouterTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private Environment env;

    @Autowired
    private SendGrid sendGrid;

    @Test
    public void shouldSendMail() throws IOException {

        Response response = new Response();
        response.setStatusCode(202);

        Mockito.when(sendGrid.api(any(Request.class))).thenReturn(response);
        producerTemplate.sendBody(env.getProperty("route.from"), jsonInput());
    }

    private String jsonInput(){
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFileName("sample.pdf");
        clientRequest.setDocNumber("00001");
        clientRequest.setEmail("someone@email.com");
        clientRequest.setProtocol("848400A");
        clientRequest.setClientName("Someone");
        clientRequest.setRequestDate(LocalDateTime.now().toString());

        return new Gson().toJson(clientRequest);
    }

    @TestConfiguration
    public static class SendGridConfigTest {
        @Bean
        @Primary
        public SendGrid sendGridMock() {
            return Mockito.mock(SendGrid.class);
        }
    }
}
