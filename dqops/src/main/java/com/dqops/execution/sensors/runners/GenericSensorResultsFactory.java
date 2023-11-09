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

package com.dqops.execution.sensors.runners;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import tech.tablesaw.api.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Helper class that creates fake generic sensor results.
 */
public class GenericSensorResultsFactory {
    /**
     * Creates a local date time of NOW, using the default configured time zone.
     * @return The local date time of NOW, based on the configured default time zone.
     */
    private static LocalDateTime getNowAtDefaultTimeZone(ZoneId defaultTimeZoneId, TimePeriodGradient timePeriodGradient) {
        LocalDateTime localDateTime = Instant.now().atZone(defaultTimeZoneId).toLocalDateTime();
        LocalDateTime truncatedDateTime = LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTime, timePeriodGradient);
        return truncatedDateTime;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @param defaultTimeZoneId Default time zone id.
     * @param timePeriodGradient Time period gradient used to truncate the time.
     * @return Dummy result table.
     */
    public static Table createResultTableWithResult(Integer actualValue,
                                                    ZoneId defaultTimeZoneId,
                                                    TimePeriodGradient timePeriodGradient) {
        Table dummyResultTable = Table.create("dummy_results",
                IntColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        if (actualValue != null) {
            row.setInt(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        }
        LocalDateTime nowAtDefaultTimeZone = getNowAtDefaultTimeZone(defaultTimeZoneId, timePeriodGradient);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, nowAtDefaultTimeZone);
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, nowAtDefaultTimeZone.toInstant(ZoneOffset.UTC));

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public static Table createResultTableWithResult(Long actualValue,
                                                    ZoneId defaultTimeZoneId,
                                                    TimePeriodGradient timePeriodGradient) {
        Table dummyResultTable = Table.create("dummy_results",
                LongColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        if (actualValue != null) {
            row.setLong(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        }
        LocalDateTime nowAtDefaultTimeZone = getNowAtDefaultTimeZone(defaultTimeZoneId, timePeriodGradient);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, nowAtDefaultTimeZone);
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, nowAtDefaultTimeZone.toInstant(ZoneOffset.UTC));

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public static Table createResultTableWithResult(Double actualValue,
                                                    ZoneId defaultTimeZoneId,
                                                    TimePeriodGradient timePeriodGradient) {
        Table dummyResultTable = Table.create("dummy_results",
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        if (actualValue != null) {
            row.setDouble(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        }
        LocalDateTime nowAtDefaultTimeZone = getNowAtDefaultTimeZone(defaultTimeZoneId, timePeriodGradient);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, nowAtDefaultTimeZone);
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, nowAtDefaultTimeZone.toInstant(ZoneOffset.UTC));

        return dummyResultTable;
    }

    /**
     * Creates a one row dummy result table, used when a dummy sensor run is requested (without running the query on the data source).
     * @param sensorRunParameters Sensor execution run parameters.
     * @return Dummy result table.
     */
    public static Table createDummyResultTable(SensorExecutionRunParameters sensorRunParameters,
                                               ZoneId defaultTimeZoneId) {
        Table dummyResultTable = Table.create("dummy_results",
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setDouble(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, 10.0);

        TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getTimeSeries();
        if (effectiveTimeSeries != null && effectiveTimeSeries.getMode() != null) {
            LocalDateTime nowAtDefaultTimeZone = getNowAtDefaultTimeZone(defaultTimeZoneId, effectiveTimeSeries.getTimeGradient());
            row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, nowAtDefaultTimeZone);
            row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, nowAtDefaultTimeZone.toInstant(ZoneOffset.UTC));
        }

        return dummyResultTable;
    }
}
