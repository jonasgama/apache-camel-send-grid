package com.example.aggregate;

import com.example.exception.EmailSenderException;
import com.example.model.ClientRequest;
import com.example.service.SendGridEmailService;
import com.sendgrid.Response;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailAggregate implements AggregationStrategy {

    @Autowired
    private Environment env;

    @Autowired
    private SendGridEmailService emailService;

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        try {
            ClientRequest email = oldExchange.getIn().getBody(ClientRequest.class);
            File file = newExchange.getIn().getBody(File.class);
            String from = env.getProperty("from");
            Response response = emailService.sendEmail(file, from, email);
            if(response.getStatusCode()!=202){
                throw new Exception(response.getBody());
            }
        } catch (Exception err) {
            oldExchange.setException(new EmailSenderException("Sending email failed:" + err.toString(), err));
        }
        return oldExchange;
    }
}
