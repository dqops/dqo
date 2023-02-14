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
package ai.dqo.core.remotestorage.gcp;

import ai.dqo.core.configuration.DqoStorageGcpConfigurationProperties;
import ai.dqo.utils.http.SharedHttpConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * Provides a shared HTTP client instance used to download and upload files to a Google storage bucket.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GcpHttpClientProviderImpl implements GcpHttpClientProvider {
    private SharedHttpConnectionProvider sharedHttpConnectionProvider;
    private DqoStorageGcpConfigurationProperties dqoStorageGcpConfigurationProperties;
    private HttpClient httpClient;

    @Autowired
    public GcpHttpClientProviderImpl(SharedHttpConnectionProvider sharedHttpConnectionProvider,
                                     DqoStorageGcpConfigurationProperties dqoStorageGcpConfigurationProperties) {
        this.sharedHttpConnectionProvider = sharedHttpConnectionProvider;
        this.dqoStorageGcpConfigurationProperties = dqoStorageGcpConfigurationProperties;

        ConnectionProvider connectionProvider = this.sharedHttpConnectionProvider.getConnectionProvider();

        HttpClient httpClientBuilder = HttpClient.create(connectionProvider)
                .keepAlive(true)
                .protocol(dqoStorageGcpConfigurationProperties.isHttp2() ? HttpProtocol.H2 : HttpProtocol.HTTP11)
                .secure();

        if (dqoStorageGcpConfigurationProperties.isHttp2() &&
                dqoStorageGcpConfigurationProperties.getHttp2MaxConcurrentStreams() != null) {
            httpClientBuilder = httpClientBuilder.http2Settings(http2 -> {
                http2.maxConcurrentStreams(dqoStorageGcpConfigurationProperties.getHttp2MaxConcurrentStreams());
            });
        }

        this.httpClient = httpClientBuilder;
    }

    /**
     * Returns a shared HTTP client used to download and upload files from/to a GCP storage bucket.
     * @return Http client.
     */
    @Override
    public HttpClient getHttpClient() {
        return this.httpClient;
    }
}
