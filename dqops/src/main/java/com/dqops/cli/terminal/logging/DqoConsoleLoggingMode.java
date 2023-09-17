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

package com.dqops.cli.terminal.logging;

/**
 * Console logging mode configuration. Decides how DQO sends logging events to the console.
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
