/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised after sensor results returned from the sensor were normalized to a standard tabular format.
 */
public class SensorResultsNormalizedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorExecutionResult sensorResult;
    private final SensorReadoutsNormalizedResult normalizedSensorResults;

    /**
     * Creates a progress event.
     *
     * @param tableSpec               Target table.
     * @param sensorRunParameters     Sensor run parameters with a direct identification of the sensor.
     * @param sensorResult            Raw sensor results (before normalization).
     * @param normalizedSensorResults Normalized sensor results.
     */
    public SensorResultsNormalizedEvent(TableSpec tableSpec,
										SensorExecutionRunParameters sensorRunParameters,
										SensorExecutionResult sensorResult,
										SensorReadoutsNormalizedResult normalizedSensorResults) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.sensorResult = sensorResult;
        this.normalizedSensorResults = normalizedSensorResults;
    }

    /**
     * Target table.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters that were used to run the sensor.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sensor results (raw).
     *
     * @return Raw sensor results.
     */
    public SensorExecutionResult getSensorResult() {
        return sensorResult;
    }

    /**
     * Normalized sensor results that will be passed to the rule evaluator.
     *
     * @return Normalized sensor results.
     */
    public SensorReadoutsNormalizedResult getNormalizedSensorResults() {
        return normalizedSensorResults;
    }
}
