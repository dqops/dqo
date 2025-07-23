/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks.comparison;

import java.time.LocalDateTime;

/**
 * A single value that is compared.
 */
public class ComparedValue {
    private final LocalDateTime timePeriod;
    private final long dataGroupHash;
    private final Double value;
    private final int rowIndex;

    /**
     * Creates a compared value.
     * @param timePeriod Time period or null when not comparing partitioned data.
     * @param dataGroupHash Data group hash.
     * @param value Value from the sensor.
     * @param rowIndex Row index.
     */
    public ComparedValue(LocalDateTime timePeriod, long dataGroupHash, Double value, int rowIndex) {
        this.timePeriod = timePeriod;
        this.dataGroupHash = dataGroupHash;
        this.value = value;
        this.rowIndex = rowIndex;
    }

    /**
     * Returns the time period for comparison.
     * @return Time period or null when the data is not date partitioned.
     */
    public LocalDateTime getTimePeriod() {
        return timePeriod;
    }

    /**
     * Returns the data group hash.
     * @return Data group hash.
     */
    public long getDataGroupHash() {
        return dataGroupHash;
    }

    /**
     * Returns the value.
     * @return Value that was retrieved from the sensor.
     */
    public Double getValue() {
        return value;
    }

    /**
     * Returns the row index.
     * @return Row index.
     */
    public int getRowIndex() {
        return rowIndex;
    }
}
