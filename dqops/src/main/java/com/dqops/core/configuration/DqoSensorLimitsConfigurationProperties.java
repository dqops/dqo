/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration parameters for sensor query limits.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.sensor.limits")
@EqualsAndHashCode(callSuper = false)
public class DqoSensorLimitsConfigurationProperties implements Cloneable {
    /**
     * Default row count limit retrieved from a single sensor for non-partitioned checks (profiling, monitoring). This is the row count limit applied when querying the data source.
     */
    public static final int DEFAULT_SENSOR_READOUT_LIMIT = 1000;

    /**
     * Default row count limit retrieved from a single sensor for partitioned checks. This is the row count limit applied when querying the data source by a partitioned check.
     */
    public static final int DEFAULT_SENSOR_READOUT_LIMIT_PARTITIONED = 7000;

    /**
     * The maximum number of queries that are merged into a bigger query, to calculate multiple sensors on the same table and to analyze multiple columns from the same table.
     */
    public static final int DEFAULT_MAX_MERGED_QUERIES = 100;

    private int sensorReadoutLimit = DEFAULT_SENSOR_READOUT_LIMIT;
    private int sensorReadoutLimitPartitioned = DEFAULT_SENSOR_READOUT_LIMIT_PARTITIONED;
    private boolean failOnSensorReadoutLimitExceeded = true;
    private int maxMergedQueries = DEFAULT_MAX_MERGED_QUERIES;

    /**
     * Returns the row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source.
     * @return The row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source.
     */
    public int getSensorReadoutLimit() {
        return sensorReadoutLimit;
    }

    /**
     * Sets the row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source.
     * @param sensorReadoutLimit The row count limit retrieved from a single sensor.
     */
    public void setSensorReadoutLimit(int sensorReadoutLimit) {
        this.sensorReadoutLimit = sensorReadoutLimit;
    }

    /**
     * Returns the row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source by partitioned checks.
     * @return The row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source by partitioned checks.
     */
    public int getSensorReadoutLimitPartitioned() {
        return sensorReadoutLimitPartitioned;
    }

    /**
     * Sets the row count limit retrieved from a single sensor. This is the row count limit applied when querying the data source by partitioned checks.
     * @param sensorReadoutLimitPartitioned The row count limit retrieved from a single sensor by partitioned checks.
     */
    public void setSensorReadoutLimitPartitioned(int sensorReadoutLimitPartitioned) {
        this.sensorReadoutLimitPartitioned = sensorReadoutLimitPartitioned;
    }

    /**
     * Returns true if the sensor execution should fail if the data source returned too many results.
     * @return True - fail, false - truncate the results and process only up to the limit.
     */
    public boolean isFailOnSensorReadoutLimitExceeded() {
        return failOnSensorReadoutLimitExceeded;
    }

    /**
     * Sets the flag to fail the sensor execution when the result count limit was exceeded.
     * @param failOnSensorReadoutLimitExceeded Fail when result count exceeded.
     */
    public void setFailOnSensorReadoutLimitExceeded(boolean failOnSensorReadoutLimitExceeded) {
        this.failOnSensorReadoutLimitExceeded = failOnSensorReadoutLimitExceeded;
    }

    /**
     * Returns the maximum number of queries that are merged into a bigger query, to calculate multiple sensors on the same table and to analyze multiple columns from the same table.
     * @return The limit of merged queries.
     */
    public int getMaxMergedQueries() {
        return maxMergedQueries;
    }

    /**
     * Sets the maximum number of queries that are merged into a bigger query, to calculate multiple sensors on the same table and to analyze multiple columns from the same table.
     * @param maxMergedQueries The limit of merged queries.
     */
    public void setMaxMergedQueries(int maxMergedQueries) {
        this.maxMergedQueries = maxMergedQueries;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoSensorLimitsConfigurationProperties clone() {
        try {
            return (DqoSensorLimitsConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
