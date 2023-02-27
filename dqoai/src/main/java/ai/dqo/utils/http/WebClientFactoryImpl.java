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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Creates Spring web client objects using a shared http client and a shared connection pool in order to reuse keep-alive connections.
 */
@Configuration
public class WebClientFactoryImpl implements WebClientFactory {
    private final SharedHttpClientProvider sharedHttpClientProvider;

    @Autowired
    public WebClientFactoryImpl(SharedHttpClientProvider sharedHttpClientProvider) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
    }

    /**
     * Creates a web client using HTTP/1.1 protocol.
     * @return HTTP/1.1 web client instance using netty.
     */
    @Override
    public WebClient createWebClientHttp11() {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(this.sharedHttpClientProvider.getHttp11SharedClient()))
                .build();

        return webClient;
    }

    /**
     * Creates a web client using HTTP/2 protocol.
     * @return HTTP/2 web client instance using netty.
     */
    @Override
    public WebClient createWebClientHttp2() {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(this.sharedHttpClientProvider.getHttp2SharedClient()))
                .build();

        return webClient;
    }
}
