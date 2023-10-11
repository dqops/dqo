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
package com.dqops.connectors;

/**
 * Exception thrown when the max row count limit was exceeded.
 */
public class RowCountLimitExceededException extends ConnectionQueryException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RowCountLimitExceededException(String message) {
        super(message);
    }

    /**
     * Creates a row count limit exceeded exception given a limit. Creates a unified error message.
     * @param rowCountLimit Row count limit.
     */
    public RowCountLimitExceededException(int rowCountLimit) {
        super("The sensor readout limit which is the maximum number of rows that DQOps can retrieve and store from a data quality sensor was exceeded. " +
                "The current configuration is: --dqo.sensor.limit.sensor-readout-limit=" + rowCountLimit + ". " +
                "Please update the configuration of data grouping and use columns with less number of unique values to meet within the limit. " +
                "Alternatively update definition of the sensor's query, maybe it is a problem with the sensor's jinja2 template.");
    }
}
