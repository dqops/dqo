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
