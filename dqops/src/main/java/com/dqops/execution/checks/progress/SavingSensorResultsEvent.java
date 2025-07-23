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

import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before the sensor results are saved for later use (they may be used later for time series calculation).
 */
public class SavingSensorResultsEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorReadoutsSnapshot sensorReadoutsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec                       Target table.
     * @param sensorReadoutsSnapshot          Sensor results for the given table.
     */
    public SavingSensorResultsEvent(TableSpec tableSpec, SensorReadoutsSnapshot sensorReadoutsSnapshot) {
        this.tableSpec = tableSpec;
        this.sensorReadoutsSnapshot = sensorReadoutsSnapshot;
    }

    /**
     * Target table specification.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor results for the given table.
     *
     * @return Sensor results for the given table.
     */
    public SensorReadoutsSnapshot getSensorReadouts() {
        return sensorReadoutsSnapshot;
    }
}
