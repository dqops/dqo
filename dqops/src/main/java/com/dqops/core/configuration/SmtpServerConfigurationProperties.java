/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the mapping for the Spring Boot built-in configuration parameters for dqo.smtp-server.*
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.smtp-server")
@EqualsAndHashCode(callSuper = false)
public class SmtpServerConfigurationProperties implements Cloneable {
    /**
     * SMTP server host
     *
     * @param host Sets the SMTP server host
     * @return The SMTP server host
     */
    @Getter
    @Setter
    private String host;

    /**
     * SMTP server port
     *
     * @param host Sets the SMTP server port
     * @return The SMTP server port
     */
    @Getter
    @Setter
    private String port;

    /**
     * SMTP server use SSL
     *
     * @param host Sets the SMTP to use SSL
     * @return Whether the SMTP server uses SSL
     */
    @Getter
    @Setter
    private Boolean useSsl;

    /**
     * SMTP server username
     *
     * @param host Sets the SMTP server username
     * @return The SMTP server username
     */
    @Getter
    @Setter
    private String username;

    /**
     * SMTP server password
     *
     * @param host Sets the SMTP server password
     * @return The SMTP server password
     */
    @Getter
    @Setter
    private String password;

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public SmtpServerConfigurationProperties clone() {
        try {
            SmtpServerConfigurationProperties cloned = (SmtpServerConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
