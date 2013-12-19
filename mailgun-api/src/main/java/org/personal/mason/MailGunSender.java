package org.personal.mason;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: domei
 * Date: 12/19/13
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailGunSender {

    private String defaultSender = "dummy@dummy.com";
    private Client client;
    private String resourceUri = "https://api.mailgun.net/v2/" + "sandbox9118.mailgun.org" + "/messages";

    public MailGunSender(String username, String password, String customDomain) {
        client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        if (customDomain != null) {
            resourceUri = String.format("https://api.mailgun.net/v2/%s/messages", customDomain);
        }
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public void setDefaultSender(String sender){
        this.defaultSender = sender;
    }
    /**
     * @param from
     * @param receipts
     * @param cces
     * @param bcces
     * @param subject
     * @param content
     * @param attachments
     */
    public ClientResponse sendTextEmail(String from, List<String> receipts, List<String> cces, List<String> bcces, String subject, String content, List<File> attachments) {
        if(from == null){
            from = defaultSender;
        }

        Client client = getClient();
        WebResource resource = client.resource(resourceUri);
        FormDataMultiPart formData = new FormDataMultiPart();
        formData.field("from", from);

        for (String to : receipts) {
            formData.field("to", to);
        }

        if (cces != null) {
            for (String cc : cces) {
                formData.field("cc", cc);
            }
        }

        if (cces != null) {
            for (String bcc : bcces) {
                formData.field("bcc", bcc);
            }
        }

        formData.field("subject", subject);

        formData.field("text", content);

        if (attachments != null) {
            for (File attachment : attachments) {
                formData.bodyPart(new FileDataBodyPart("attachment", attachment));
            }
        }
        WebResource.Builder builder = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        return builder.post(ClientResponse.class, formData);
    }

    public ClientResponse sendHtmlEmail(String from, List<String> receipts, List<String> cces, List<String> bcces, String subject, String content, List<File> attachments) {
        if(from == null){
            from = defaultSender;
        }

        Client client = getClient();
        WebResource resource = client.resource(resourceUri);
        FormDataMultiPart formData = new FormDataMultiPart();
        formData.field("from", from);

        for (String to : receipts) {
            formData.field("to", to);
        }

        if (cces != null) {
            for (String cc : cces) {
                formData.field("cc", cc);
            }
        }

        if (cces != null) {
            for (String bcc : bcces) {
                formData.field("bcc", bcc);
            }
        }

        formData.field("subject", subject);

        formData.field("html", content);

        if (attachments != null) {
            for (File attachment : attachments) {
                formData.bodyPart(new FileDataBodyPart("attachment", attachment));
            }
        }
        WebResource.Builder builder = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        return builder.post(ClientResponse.class, formData);
    }

    public Client getClient() {
        return client;
    }


}
