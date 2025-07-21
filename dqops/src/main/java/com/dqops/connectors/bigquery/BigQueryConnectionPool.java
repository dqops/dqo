/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * BigQuery connection pool that supports multiple connections.
 */
public interface BigQueryConnectionPool {
    /**
     * Returns or creates a BigQuery service for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return BigQuery service.
     */
    BigQueryInternalConnection getBigQueryService(ConnectionSpec connectionSpec, SecretValueLookupContext secretValueLookupContext);
}
