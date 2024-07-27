package com.dqops.core.incidents.email;

import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
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
        mailSender.setUsername(smtpServerConfiguration.getUsername());
        mailSender.setPassword(smtpServerConfiguration.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");

        if(smtpServerConfiguration.getUseSSL()){
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
//        props.put("mail.debug", "true");

        return mailSender;
    }

}
