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
