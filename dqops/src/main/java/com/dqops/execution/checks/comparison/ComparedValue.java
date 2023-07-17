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
