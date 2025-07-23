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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configuration POJO with the mapping for the Spring Boot built-in configuration parameters for server.*
 */
@Configuration
@ConfigurationProperties(prefix = "server")
@EqualsAndHashCode(callSuper = false)
public class ServerConfigurationProperties implements Cloneable {
    private String address;
    private String port;

    /**
     * Returns the host name that the web server is listening.
     * @return Host name.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the host name where the server is listening.
     * @param address Host name.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the port number. It can be "-1" or "0".
     * @return Port number.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port number.
     * @param port Port number.
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public ServerConfigurationProperties clone() {
        try {
            ServerConfigurationProperties cloned = (ServerConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
