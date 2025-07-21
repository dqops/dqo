/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;

/**
 * Configuration class that includes netty resources in the graceful shutdown process.
 */
@Configuration
public class WebClientShutdownConfiguration {
    /**
     * Configures a netty reactor shutdown handler that will stop netty resources on graceful shutdown. See: https://docs.spring.io/spring-framework/docs/6.0.4/reference/html/web-reactive.html#webflux-client-builder
     * @return Reactor resource factory.
     */
    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        return new ReactorResourceFactory();
    }
}
