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
 * Logger used for logging check, sensor and rule execution issues, selecting the logger name and severity.
 */
public interface UserErrorLogger {
    /**
     * Logs a sensor issue.
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    void logSensor(String message, Throwable cause);

    /**
     * Logs a rule issue.
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    void logRule(String message, Throwable cause);

    /**
     * Logs a check issue.
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    void logCheck(String message, Throwable cause);

    /**
     * Logs a statistics collection issue.
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    void logStatistics(String message, Throwable cause);

    /**
     * Logs a yaml schema issues (invalid YAML files).
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    void logYaml(String message, Throwable cause);
}
