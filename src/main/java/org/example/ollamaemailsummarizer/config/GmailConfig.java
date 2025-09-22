package org.example.ollamaemailsummarizer.config;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class GmailConfig {
    @Value("${APP_PASSWORD}")
    private String appPassword;
    @Value("${EMAIL}")
    private String email;
    @Bean
    public Store establishConnection() throws MessagingException{
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, appPassword);
        return store;
    }
}
