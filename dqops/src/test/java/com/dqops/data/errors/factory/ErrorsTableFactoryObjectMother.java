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

import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import tech.tablesaw.api.Table;

/**
 * Object mother for {@link ErrorsTableFactory}.
 */
public class ErrorsTableFactoryObjectMother {
    /**
     * Returns a new factory.
     * @return Factory.
     */
    public static ErrorsTableFactory createFactory() {
        return new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
    }

    /**
     * Creates an empty normalized table.
     * @param tableName Table name.
     * @return Empty normalized table with a proper schema.
     */
    public static Table createEmptyNormalizedTable(String tableName) {
        ErrorsTableFactory factory = createFactory();
        return factory.createEmptyErrorsTable(tableName);
    }
}
