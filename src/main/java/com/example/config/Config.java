package com.example.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${sendgrid.api.key}")
    private String sendGridAPIKey;

    @Bean
    public SendGrid sendGrid(){
        return new SendGrid(sendGridAPIKey);
    }
}
