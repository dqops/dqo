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
 * Tablesaw table factory that creates a tabular object used to store the statistics results.
 */
public interface StatisticsResultsTableFactory {
    /**
     * Creates an empty normalized statistics results (basic profiles) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty statistics results table.
     */
    Table createEmptyStatisticsTable(String tableName);
}
