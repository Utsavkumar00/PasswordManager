package com.example.passwordmanager.config;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {

    @Bean
    @Conditional(MailConfiguredCondition.class)
    public JavaMailSender javaMailSender(MailProperties properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyMailProperties(properties, sender);
        return sender;
    }

    private static void applyMailProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getProtocol() != null) {
            sender.setProtocol(properties.getProtocol());
        }
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        if (properties.getUsername() != null) {
            sender.setUsername(properties.getUsername());
        }
        if (properties.getPassword() != null) {
            sender.setPassword(properties.getPassword());
        }
        Charset encoding = properties.getDefaultEncoding();
        if (encoding != null) {
            sender.setDefaultEncoding(encoding.name());
        }
        Map<String, String> nested = properties.getProperties();
        if (nested != null && !nested.isEmpty()) {
            Properties javaMailProps = new Properties();
            javaMailProps.putAll(nested);
            sender.setJavaMailProperties(javaMailProps);
        }
    }
}
