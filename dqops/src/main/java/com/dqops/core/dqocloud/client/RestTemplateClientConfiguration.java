/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.client;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Spring configuration that creates a shared rest template.
 */
@Configuration
@EnableScheduling
public class RestTemplateClientConfiguration {
    /**
     * Creates a http connection pool manager.
     * @return Http connection pool manager.
     */
    @Bean
    public PoolingHttpClientConnectionManager createHttpClientPoolManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        return connectionManager;
    }

    /**
     * Creates a connection factory.
     * @return Connection factory.
     */
    @Bean
    public ClientHttpRequestFactory createRequestFactory(PoolingHttpClientConnectionManager connectionManager) {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(30, TimeUnit.SECONDS))
                .setContentCompressionEnabled(true)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    /**
     * Creates a shared rest template.
     * @return Shared rest template.
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
