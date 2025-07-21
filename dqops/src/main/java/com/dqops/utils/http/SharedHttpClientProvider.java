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
