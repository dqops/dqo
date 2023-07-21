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

import reactor.netty.http.client.HttpClient;

/**
 * Provides a shared HTTP client instance for a requested protocol.
 */
public interface SharedHttpClientProvider {
    /**
     * Returns a shared HTTP client used to download and upload files from/to a GCP storage bucket.
     *
     * @return Http client.
     */
    HttpClient getHttpClientGcpStorage();

    /**
     * Returns a shared HTTP client for HTTP/2 protocol.
     *
     * @return Http client for HTTP/2.
     */
    HttpClient getHttp2SharedClient();

    /**
     * Returns a shared HTTP client for HTTP/1.1 protocol.
     *
     * @return Http client for HTTP/1.1.
     */
    HttpClient getHttp11SharedClient();
}
