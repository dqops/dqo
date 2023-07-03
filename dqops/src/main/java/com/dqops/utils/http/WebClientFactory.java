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
package com.dqops.utils.http;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * Creates Spring web client objects using a shared http client and a shared connection pool in order to reuse keep-alive connections.
 */
public interface WebClientFactory {
    /**
     * Creates a web client using HTTP/1.1 protocol.
     *
     * @return HTTP/1.1 web client instance using netty.
     */
    WebClient createWebClientHttp11();

    /**
     * Creates a web client using HTTP/2 protocol.
     *
     * @return HTTP/2 web client instance using netty.
     */
    WebClient createWebClientHttp2();
}
