/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.logging;

/**
 * Console logging mode configuration. Decides how DQOps sends logging events to the console.
 */
public enum DqoConsoleLoggingMode {
    /**
     * Console logging is disabled (default).
     */
    OFF,

    /**
     * Logging to console uses a JSON message that could be processed by Docker log engines, such as fluent bit and forwarded as structured logging messages to the cloud logging platforms.
     */
    JSON,

    /**
     * Publishes standard single line entries to the console.
     */
    PATTERN
}
