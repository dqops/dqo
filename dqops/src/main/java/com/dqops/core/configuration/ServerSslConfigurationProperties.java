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

/**
 * Configuration POJO with the mapping for the Spring Boot built-in configuration parameters for server.ssl.*
 */
@Configuration
@ConfigurationProperties(prefix = "server.ssl")
@EqualsAndHashCode(callSuper = false)
public class ServerSslConfigurationProperties implements Cloneable {
    private String keyStore;

    /**
     * Returns the path to the certificate key store. When this parameter is filled, Spring Boot will activate https.
     * @return Key store path.
     */
    public String getKeyStore() {
        return keyStore;
    }

    /**
     * Sets the path to the key store.
     * @param keyStore Path to the key store.
     */
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Clones the current object.
     * @return Deeply cloned instance.
     */
    @Override
    public ServerSslConfigurationProperties clone() {
        try {
            ServerSslConfigurationProperties cloned = (ServerSslConfigurationProperties) super.clone();
            return cloned;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
