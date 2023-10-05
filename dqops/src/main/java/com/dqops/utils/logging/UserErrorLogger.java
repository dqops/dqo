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
