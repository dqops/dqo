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
