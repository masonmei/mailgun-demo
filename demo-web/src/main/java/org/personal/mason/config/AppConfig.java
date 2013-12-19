package org.personal.mason.config;

import org.personal.mason.MailGunSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: domei
 * Date: 12/18/13
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(basePackages = {"org.personal.mason"})
@PropertySource(value = {"classpath:app.properties"})
public class AppConfig {
    @Value("${mailgun.url}")
    private String url;
    @Value("${mailgun.username}")
    private String username;
    @Value("${mailgun.password}")
    private String password;
    @Value("${mailgun.sender}")
    private String sender;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MailGunSender mailGunSender() {
        MailGunSender mailGunSender = new MailGunSender(username, password, null);
        if (sender != null) {
            mailGunSender.setDefaultSender(sender);
        }
        return mailGunSender;
    }
}
