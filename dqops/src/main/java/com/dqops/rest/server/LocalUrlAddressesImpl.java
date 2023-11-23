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
package com.dqops.rest.server;

import com.dqops.core.configuration.ServerConfigurationProperties;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns the configuration of the default web server url.
 */
@Component
public class LocalUrlAddressesImpl implements LocalUrlAddresses {
    public static final String PROTOCOL = "http://";
    public static final String DEFAULT_HOST = "localhost";
    public static final String SWAGGER_UI_PATH = "/swagger-ui";
    private ServerConfigurationProperties serverConfigurationProperties;


    /**
     * Dependency injection constructor.
     * @param serverConfigurationProperties Server configuration properties.
     */
    @Autowired
    public LocalUrlAddressesImpl(ServerConfigurationProperties serverConfigurationProperties) {
        this.serverConfigurationProperties = serverConfigurationProperties;
    }

    /**
     * Returns the url of the DQOps user interface.
     * @return URL of the DQOps user interface.
     */
    @Override
    public String getDqopsUiUrl() {
        String address = this.serverConfigurationProperties.getAddress();
        if (Strings.isNullOrEmpty(address)) {
            address = DEFAULT_HOST;
        }

        Integer portNumber = Integer.valueOf(Strings.isNullOrEmpty(this.serverConfigurationProperties.getPort()) ? "80" : this.serverConfigurationProperties.getPort());
        String portSuffix = portNumber == 80 ? "" : ":" + portNumber;

        return PROTOCOL + address + portSuffix;
    }

    /**
     * Returns the url to the swagger UI frontend.
     * @return Swagger ui frontend url.
     */
    @Override
    public String getSwaggerUiUrl() {
        return getDqopsUiUrl() + SWAGGER_UI_PATH + "/";
    }
}
