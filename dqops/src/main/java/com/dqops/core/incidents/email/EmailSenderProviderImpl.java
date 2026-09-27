/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents.email;

import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import com.google.common.base.Strings;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * The provider class for the JavaEmailSender.
 */
@Component
public class EmailSenderProviderImpl implements EmailSenderProvider {

    /**
     * Sets JavaMailSender with the SmtpServerConfigurationSpec values.
     * @param smtpServerConfiguration SMTP server configuration.
     * @return Configured JavaMailSender
     */
    public JavaMailSender configureJavaMailSender(SmtpServerConfigurationSpec smtpServerConfiguration) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpServerConfiguration.getHost());
        mailSender.setPort(Integer.parseInt(smtpServerConfiguration.getPort()));

        // SMTP authentication is used only when both the user name and the password are configured, otherwise the messages are sent without authentication (for example to an internal mail relay listening on port 25)
        boolean useAuthentication = !Strings.isNullOrEmpty(smtpServerConfiguration.getUsername()) &&
                !Strings.isNullOrEmpty(smtpServerConfiguration.getPassword());
        if (useAuthentication) {
            mailSender.setUsername(smtpServerConfiguration.getUsername());
            mailSender.setPassword(smtpServerConfiguration.getPassword());
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(useAuthentication));

        if (Boolean.TRUE.equals(smtpServerConfiguration.getUseSsl())) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
//        props.put("mail.debug", "true");

        return mailSender;
    }

}
