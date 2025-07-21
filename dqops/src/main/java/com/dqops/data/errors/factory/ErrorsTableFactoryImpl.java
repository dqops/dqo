/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.factory;

import com.dqops.data.readouts.factory.SensorReadoutsTableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 * Factory that creates an empty tablesaw table for storing the errors. The table schema is configured.
 */
@Component
public class ErrorsTableFactoryImpl implements ErrorsTableFactory {
    private SensorReadoutsTableFactory sensorReadoutsTableFactory;

    @Autowired
    public ErrorsTableFactoryImpl(SensorReadoutsTableFactory sensorReadoutsTableFactory) {
        this.sensorReadoutsTableFactory = sensorReadoutsTableFactory;
    }

    /**
     * Creates an empty normalized sensor execution errors table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty sensor execution errors table.
     */
    @Override
    public Table createEmptyErrorsTable(String tableName) {
        Table table = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable(tableName);
        table.addColumns(
                StringColumn.create(ErrorsColumnNames.READOUT_ID_COLUMN_NAME),
                StringColumn.create(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME),
                StringColumn.create(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME),
                DateTimeColumn.create(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME)
        );

        return table;
    }
}
