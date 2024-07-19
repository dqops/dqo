/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the mapping for the Spring Boot built-in configuration parameters for server.*
 */
@Configuration
@ConfigurationProperties(prefix = "smtp-server")
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
