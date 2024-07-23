package com.dqops.core.incidents.email;

import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailSenderProvider {

    JavaMailSender configureJavaMailSender(SmtpServerConfigurationSpec smtpServerConfiguration);

}
