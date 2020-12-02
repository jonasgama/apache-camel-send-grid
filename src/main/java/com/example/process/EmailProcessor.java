package com.example.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;

public class EmailProcessor implements Processor {

    @Autowired
    private Environment env;

    @Override
    public void process(Exchange exchange) throws Exception {

        File file = exchange.getIn().getBody(File.class);
        try {
            String from = env.getProperty("from");
            Response response = emailService.sendEmail(file, from, mail);
            if(response.getStatusCode()!=202){
                throw new Exception(response.getBody());
            }
        } catch (Exception err) {
            exchange.getIn().setBody(String.valueOf(file));
            log.error(err.getMessage(), err);
            throw new MailSenderException("Falha em enviar o email para o destinatário:" + err.getMessage(), err);
        }

    }
}
