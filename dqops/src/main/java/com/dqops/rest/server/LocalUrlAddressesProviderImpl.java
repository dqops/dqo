/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
public class LocalUrlAddressesProviderImpl implements LocalUrlAddressesProvider {
    public static final String PROTOCOL = "http://";
    public static final String DEFAULT_HOST = "localhost";
    public static final String SWAGGER_UI_PATH = "/swagger-ui";
    private ServerConfigurationProperties serverConfigurationProperties;


    /**
     * Dependency injection constructor.
     * @param serverConfigurationProperties Server configuration properties.
     */
    @Autowired
    public LocalUrlAddressesProviderImpl(ServerConfigurationProperties serverConfigurationProperties) {
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
