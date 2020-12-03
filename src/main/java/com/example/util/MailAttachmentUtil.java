package com.example.util;

import com.sendgrid.Attachments;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class MailAttachmentUtil {

    private String fileName;

    public MailAttachmentUtil(){
        fileName = "extract.pdf";
    }

    public Attachments get(File file) throws IOException {
        String imageDataString = base64(file);
        Attachments attachments = new Attachments();
        attachments.setContent(imageDataString);
        attachments.setType("application/pdf");
        attachments.setFilename(fileName);
        attachments.setDisposition("attachment");
        attachments.setContentId("Banner");

        return attachments;
    }

    public String base64(File file) throws IOException {
        byte[] filedata = IOUtils.toByteArray(new FileInputStream(file));
        return new Base64().encodeAsString(filedata);
    }

    public String getFileName() {
            return fileName;
        }
}
