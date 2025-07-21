/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.factory;

import com.dqops.data.readouts.factory.SensorReadoutsTableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Factory that creates an empty tablesaw table for storing the check evaluation results. The table schema is configured.
 */
@Component
public class CheckResultsTableFactoryImpl implements CheckResultsTableFactory {
    private final SensorReadoutsTableFactory sensorReadoutsTableFactory;

    /**
     * Default injection constructor.
     *
     * @param sensorReadoutsTableFactory Sensor readouts table factory, called to create shared columns.
     */
    @Autowired
    public CheckResultsTableFactoryImpl(SensorReadoutsTableFactory sensorReadoutsTableFactory) {
        this.sensorReadoutsTableFactory = sensorReadoutsTableFactory;
    }

    /**
     * Creates an empty normalized rule result (alerts) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty rule evaluation results (alerts) table.
     */
    public Table createEmptyCheckResultsTable(String tableName) {
        Table table = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable(tableName);
        table.addColumns(
                IntColumn.create(CheckResultsColumnNames.SEVERITY_COLUMN_NAME),
                LongColumn.create(CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME),
                StringColumn.create(CheckResultsColumnNames.REFERENCE_CONNECTION_COLUMN_NAME),
                StringColumn.create(CheckResultsColumnNames.REFERENCE_SCHEMA_COLUMN_NAME),
                StringColumn.create(CheckResultsColumnNames.REFERENCE_TABLE_COLUMN_NAME),
                StringColumn.create(CheckResultsColumnNames.REFERENCE_COLUMN_COLUMN_NAME),
                BooleanColumn.create(CheckResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME),
                BooleanColumn.create(CheckResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(CheckResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME)
        );

        return table;
    }
}
