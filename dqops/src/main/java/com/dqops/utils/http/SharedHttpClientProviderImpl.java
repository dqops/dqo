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

import com.dqops.core.configuration.DqoStorageGcpConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * Provides a shared HTTP client instance for a requested protocol.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SharedHttpClientProviderImpl implements SharedHttpClientProvider {
    private SharedHttpConnectionProvider sharedHttpConnectionProvider;
    private DqoStorageGcpConfigurationProperties dqoStorageGcpConfigurationProperties;
    private HttpClient httpClientGcpStorage;
    private HttpClient httpClient11;
    private HttpClient httpClient2;

    @Autowired
    public SharedHttpClientProviderImpl(SharedHttpConnectionProvider sharedHttpConnectionProvider,
                                        DqoStorageGcpConfigurationProperties dqoStorageGcpConfigurationProperties) {
        this.sharedHttpConnectionProvider = sharedHttpConnectionProvider;
        this.dqoStorageGcpConfigurationProperties = dqoStorageGcpConfigurationProperties;

        ConnectionProvider connectionProvider = this.sharedHttpConnectionProvider.getConnectionProvider();

        HttpClient httpClientBuilderGcpStorage = HttpClient.create(connectionProvider)
                .keepAlive(true)
                .protocol(dqoStorageGcpConfigurationProperties.isHttp2() ? HttpProtocol.H2 : HttpProtocol.HTTP11)
                .secure();

        if (dqoStorageGcpConfigurationProperties.isHttp2() &&
                dqoStorageGcpConfigurationProperties.getHttp2MaxConcurrentStreams() != null) {
            httpClientBuilderGcpStorage = httpClientBuilderGcpStorage.http2Settings(http2 -> {
                http2.maxConcurrentStreams(dqoStorageGcpConfigurationProperties.getHttp2MaxConcurrentStreams());
            });
        }

        this.httpClientGcpStorage = httpClientBuilderGcpStorage;

        this.httpClient11 = HttpClient.create(connectionProvider)
                .keepAlive(true)
                .protocol(HttpProtocol.HTTP11)
                .secure();

        this.httpClient2 = HttpClient.create(connectionProvider)
                .keepAlive(true)
                .protocol(HttpProtocol.H2)
                .http2Settings(http2 -> {
                    http2.maxConcurrentStreams(1000); // TODO: support configuration
                })
                .secure();
    }

    /**
     * Returns a shared HTTP client used to download and upload files from/to a GCP storage bucket.
     *
     * @return Http client.
     */
    @Override
    public HttpClient getHttpClientGcpStorage() {
        return this.httpClientGcpStorage;
    }

    /**
     * Returns a shared HTTP client for HTTP/2 protocol.
     *
     * @return Http client for HTTP/2.
     */
    @Override
    public HttpClient getHttp2SharedClient() {
        return this.httpClient2;
    }

    /**
     * Returns a shared HTTP client for HTTP/1.1 protocol.
     *
     * @return Http client for HTTP/1.1.
     */
    @Override
    public HttpClient getHttp11SharedClient() {
        return this.httpClient11;
    }
}
