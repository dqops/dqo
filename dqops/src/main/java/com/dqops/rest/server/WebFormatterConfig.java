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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Spring reactive (webflux) configuration for the formatters.
 */
@Configuration
public class WebFormatterConfig implements WebFluxConfigurer {
    private ObjectMapper objectMapper;

    @Autowired
    public WebFormatterConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Configures the default format for date/time as an ISO format.
     * @param registry Formatter registry.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(this.objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper));
        WebFluxConfigurer.super.configureHttpMessageCodecs(configurer);
    }
}
