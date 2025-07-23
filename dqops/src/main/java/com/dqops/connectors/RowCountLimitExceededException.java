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
