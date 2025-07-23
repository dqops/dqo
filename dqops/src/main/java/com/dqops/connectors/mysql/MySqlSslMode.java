/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.mysql;

/**
 * Enumeration of sslMode connection parameters for finer control of the SSL connection in My SQL.
 */

public enum MySqlSslMode {
    DISABLED,
    PREFERRED,
    REQUIRED,
    VERIFY_CA,
    VERIFY_IDENTITY
}
