/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.factory;

import tech.tablesaw.api.Table;

/**
 * Object mother for {@link StatisticsResultsTableFactory}
 */
public class StatisticsResultsTableFactoryObjectMother {
    /**
     * Returns a new factory.
     * @return Factory.
     */
    public static StatisticsResultsTableFactory createFactory() {
        return new StatisticsResultsTableFactoryImpl();
    }

    /**
     * Creates an empty normalized table.
     * @param tableName Table name.
     * @return Empty normalized table with a proper schema.
     */
    public static Table createEmptyNormalizedTable(String tableName) {
        StatisticsResultsTableFactory factory = createFactory();
        return factory.createEmptyStatisticsTable(tableName);
    }
}
