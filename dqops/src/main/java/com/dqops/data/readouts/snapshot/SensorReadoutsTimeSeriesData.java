/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.snapshot;

import tech.tablesaw.api.Table;

/**
 * Time series data extracted from historic sensor readouts for a single time series.
 * A single time series is a subset of sensor readouts for a single check (which maps 1-to-1 to a sensor) and a data stream id.
 */
public class SensorReadoutsTimeSeriesData {
    private final SensorReadoutTimeSeriesKey key;
    private final Table table;

    /**
     * Creates a sensor readouts time series data object.
     * @param key Time series key to identify a single time series.
     * @param table Table with the results for the time series.
     */
    public SensorReadoutsTimeSeriesData(SensorReadoutTimeSeriesKey key, Table table) {
        this.key = key;
        this.table = table;
    }

    /**
     * Returns the time series key that identifies a single time series.
     * @return Key.
     */
    public SensorReadoutTimeSeriesKey getKey() {
        return key;
    }

    /**
     * Returns the table with historic sensor readouts only for that time series.
     * @return Table with historic results.
     */
    public Table getTable() {
        return table;
    }
}
