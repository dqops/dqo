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
import org.springframework.mail.javamail.JavaMailSender;

/**
 * The provider class for the JavaEmailSender.
 */
public interface EmailSenderProvider {

    /**
     * Sets JavaMailSender with the SmtpServerConfigurationSpec values.
     * @param smtpServerConfiguration SMTP server configuration.
     * @return JavaMailSender with incident notifications' configuration.
     */
    JavaMailSender configureJavaMailSender(SmtpServerConfigurationSpec smtpServerConfiguration);

}
