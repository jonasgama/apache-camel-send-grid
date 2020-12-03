package com.example.service;

import com.example.model.ClientRequest;
import com.example.util.BodyPayloadUtil;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class SendGridEmailService {

    @Autowired
    private SendGrid sendGridClient;

    @Autowired
    private BodyPayloadUtil bodyPayload;

    public Response sendEmail(File file, String from, ClientRequest mail) throws Exception {
        if (file == null) {
            throw new Exception("File has not been found " +mail.getFileName());
        }
        Mail mailPayload = this.bodyPayload.get(file, from, mail);
        return this.request(mailPayload);
    }

    private Response request(Mail mail) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return this.sendGridClient.api(request);
    }
}
