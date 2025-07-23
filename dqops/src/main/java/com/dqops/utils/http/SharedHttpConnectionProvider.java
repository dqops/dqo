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

import reactor.netty.resources.ConnectionProvider;

/**
 * Returns a shared, preconfigured HTTP Connection provider that maintains a pool of HTTP connections
 * used by a WebFlux web client in DQOps.
 */
public interface SharedHttpConnectionProvider {
    /**
     * Returns a shared HTTP connection provider (connection pool).
     *
     * @return HTTP connection provider (connection pool).
     */
    ConnectionProvider getConnectionProvider();
}
