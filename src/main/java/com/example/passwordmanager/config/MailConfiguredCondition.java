package com.example.passwordmanager.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * True when SMTP should be active: both host and username are non-blank.
 */
public class MailConfiguredCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String host = context.getEnvironment().getProperty("spring.mail.host");
        String username = context.getEnvironment().getProperty("spring.mail.username");
        return StringUtils.hasText(host) && StringUtils.hasText(username);
    }
}
