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
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before a sensor is prepared for execution for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
 */
public class PreparingSensorEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;

    /**
     * Creates an event.
     *
     * @param tableSpec           Target table.
     * @param sensorRunParameters Sensor run parameters.
     */
    public PreparingSensorEvent(TableSpec tableSpec, SensorExecutionRunParameters sensorRunParameters) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
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
     * Sensor execution parameters that will be passed to the sensor.
     *
     * @return Sensor execution parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }
}
