/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.progress;

import com.dqops.execution.checks.progress.CheckExecutionProgressEvent;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised after a sensor was executed and returned raw (not normalized) results.
 */
public class SensorExecutedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorExecutionResult sensorResult;

    /**
     * Creates an event.
     *
     * @param tableSpec           Target table.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorResult        Sensor results (raw, not normalized).
     */
    public SensorExecutedEvent(TableSpec tableSpec, SensorExecutionRunParameters sensorRunParameters, SensorExecutionResult sensorResult) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.sensorResult = sensorResult;
    }

    /**
     * Table specification.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters that were given to the sensor.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Raw sensor results that were returned from the sensor, probably columns use different data types.
     *
     * @return Raw sensor results.
     */
    public SensorExecutionResult getSensorResult() {
        return sensorResult;
    }
}
