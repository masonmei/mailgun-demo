package org.personal.mason;

import com.sun.jersey.api.client.ClientResponse;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: domei
 * Date: 12/19/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailGunTest {
    static File file;

    static {
        try {
            URL url = MailGunTest.class.getClassLoader().getResource("siginal.gif");
            file = new File(url.toURI());
        } catch (Exception e) {

        }
    }

    public static void main(String... args) {
        String username = "api";
        String password = "key-9o1g4unoizjzeo9m28vv13r4zvrw32q9";

        System.out.println(file.getAbsoluteFile());
        MailGunSender sender = new MailGunSender(username, password, null);
        // Use this to send text email
        ClientResponse
                response = sender.sendTextEmail(
                "wsppike@163.com",
                Arrays.asList("mason4mei@gmail.com"),
                null,
                null,
                "Test Subject",
                "Test Content",
                null);
        System.out.print(response.getStatus());

        // used to send html email
                response = sender.sendHtmlEmail(
                "wsppike@163.com",
                Arrays.asList("mason4mei@gmail.com"),
                null,
                null,
                "Test Subject",
                "<!Doctype html><html><head>Test Content</head><body><h2>Header</h2><hr/><div>The Content of html body, if you want to use html version , you'd better create a email template for this.</div></body></html>",
                null);
        System.out.print(response.getStatus());

        //use to send html email with attachment images
                response = sender.sendHtmlEmail(
                "wsppike@163.com",
                Arrays.asList("mason4mei@gmail.com"),
                null,
                null,
                "Test Subject",
                "<!Doctype html><html><head>Test Content</head><body><h2>Header</h2><hr/><div>The Content of html body, if you want to use html version , you'd better create a email template for this.</div></body></html>",
                Arrays.asList(file));
        System.out.print(response.getStatus());
    }
}
