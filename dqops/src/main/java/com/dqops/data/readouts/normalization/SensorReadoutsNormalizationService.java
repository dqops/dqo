/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.normalization;

import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;

/**
 * Service that parses datasets with results returned by a sensor query.
 * Detects column types (data grouping level columns), describes the metadata of the result. Also fixes missing information, adds a data_group_hash column with a hash of all data stream levels.
 */
public interface SensorReadoutsNormalizationService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for using in a sensor. Returns a new table with fixed column types.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    SensorReadoutsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                    SensorExecutionRunParameters sensorRunParameters);
}
