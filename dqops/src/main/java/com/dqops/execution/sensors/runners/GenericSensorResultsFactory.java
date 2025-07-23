/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    /**
     * Creates an empty result table, used when a sensor should not return any results.
     * @return Empty result table.
     */
    public static Table createEmptyResultTable() {
        Table emptyResultTable = Table.create("empty_results",
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));

        return emptyResultTable;
    }
}
