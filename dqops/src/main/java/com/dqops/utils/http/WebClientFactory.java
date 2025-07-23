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
