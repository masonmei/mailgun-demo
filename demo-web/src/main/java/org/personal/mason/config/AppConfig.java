package org.personal.mason.config;

import org.personal.mason.MailGunSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
    @Value("${executor.pool.capacity}")
    private int queueCapacity;
    @Value("${executor.pool.maxsize}")
    private int maxPoolSize;
    @Value("${executor.pool.size}")
    private int corePoolSize;

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

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        return executor;
    }
}
