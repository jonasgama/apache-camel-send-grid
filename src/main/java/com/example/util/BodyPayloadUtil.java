package com.example.util;

import com.example.model.ClientRequest;
import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class BodyPayloadUtil {

    @Autowired
    private MailAttachmentUtil mailAttachment;

    @Autowired
    private TemplateAttachmentUtil templateAttachment;

    public Mail get(File file, String from, ClientRequest mail) throws IOException {
        Content content = this.templateAttachment.get(mail);
        String subject = this.mailSubject();

        Email fromMail = this.mailAddress(from);
        Email toMail = this.mailAddress(mail.getEmail());

        Mail mailPayload = new Mail(fromMail, subject, toMail, content);
        mailPayload.setReplyTo(fromMail);

        Attachments attachments = this.mailAttachment.get(file);
        mailPayload.addAttachments(attachments);
        return mailPayload;
    }

    private String mailSubject() {
        return "Extrato";
    }

    private Email mailAddress(String address) {
        return new Email(address);
    }

}
