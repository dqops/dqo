/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.http;

import ai.dqo.core.configuration.DqoStorageGcpConfigurationProperties;
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
