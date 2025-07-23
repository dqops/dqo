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

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Configures remaining Spring WebFlux configuration parameters.
 */
@Configuration
@EnableWebFlux
public class WebFluxServerWebConfiguration implements WebFluxConfigurer {
    private Environment environment;

    public WebFluxServerWebConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Overrides the configuration of a maximum payload size for rest api, for some versions of Spring Boot that do not use the default configuration parameter.
     * @param configurer Server codec configurer.
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        String maxMemorySizeString = environment.getProperty("spring.codec.max-in-memory-size", "5MB");
        DataSize maxMemorySize = DataSize.parse(maxMemorySizeString, DataUnit.BYTES);
        configurer.defaultCodecs().maxInMemorySize((int)maxMemorySize.toBytes());
    }
}
