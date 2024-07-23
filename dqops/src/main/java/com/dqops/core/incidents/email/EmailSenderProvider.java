package com.dqops.core.incidents.email;

import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * The provider class for the JavaEmailSender.
 */
public interface EmailSenderProvider {

    /**
     * Sets JavaMailSender with the SmtpServerConfigurationSpec values.
     * @param smtpServerConfiguration SMTP server configuration.
     * @return
     */
    JavaMailSender configureJavaMailSender(SmtpServerConfigurationSpec smtpServerConfiguration);

}
