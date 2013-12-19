package org.personal.mason;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: domei
 * Date: 12/19/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mail {
    private String from;
    @NotNull
    private String to;
    @NotNull
    private String subject;
    @NotNull
    private String content;
    private MultipartFile attachment;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }
}
