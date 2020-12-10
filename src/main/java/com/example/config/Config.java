package com.example.config;

import com.sendgrid.SendGrid;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
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

    @Bean(name="kafkaBean")
    public KafkaComponent kafkaConfig(
            @Value("${kafka.url}") String url,
            @Value("${kafka.reset.offset}") String offsetUrl,
            @Value("${kafka.client.id}") String clientId,
            @Value("${kafka.max.poll}") Integer maxPollRecords) {
        KafkaComponent kafka = new KafkaComponent();

        KafkaConfiguration config = new KafkaConfiguration();
        config.setBrokers(url);
        config.setAutoOffsetReset(offsetUrl);
        config.setClientId(clientId);
        config.setMaxPollRecords(maxPollRecords);

        kafka.setConfiguration(config);
        return kafka;
    }
    
}
