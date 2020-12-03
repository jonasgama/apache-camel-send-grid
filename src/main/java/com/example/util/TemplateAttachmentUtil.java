package com.example.util;

import com.example.model.ClientRequest;
import com.sendgrid.Content;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;

@Component
public class TemplateAttachmentUtil {

    public Content get(ClientRequest mail) throws IOException {
        String parsedTemplate = this.parseTemplate(mail);
        return new Content("text/html", parsedTemplate);
    }

    private String parseTemplate(ClientRequest mail) throws IOException {
        String template = this.mailTemplate();
        return template
                .replace("#CLIENT_NAME#", mail.getClientName())
                .replace("#CLIENT_DOCUMENT#", mail.getDocNumber())
                .replace("#DATE#", mail.getRequestDate());
    }
    private String mailTemplate() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("template/emailTemplate.html").getInputStream(), defaultCharset());
    }
}
