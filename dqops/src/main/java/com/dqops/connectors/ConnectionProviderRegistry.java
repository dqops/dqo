/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;


/**
 * Connection registry that returns connection providers for dialects.
 */
public interface ConnectionProviderRegistry {
    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    ConnectionProvider getConnectionProvider(String dialect);

    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    ConnectionProvider getConnectionProvider(ProviderType dialect);
}
