/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.progress;

import com.dqops.data.statistics.snapshot.StatisticsSnapshot;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before the statistics results are saved to parquet files.
 */
public class SavingStatisticsResultsEvent extends StatisticsCollectorsExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final StatisticsSnapshot statisticsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec          Target table.
     * @param statisticsSnapshot Statistics result (snapshot) for the given table.
     */
    public SavingStatisticsResultsEvent(TableSpec tableSpec, StatisticsSnapshot statisticsSnapshot) {
        this.tableSpec = tableSpec;
        this.statisticsSnapshot = statisticsSnapshot;
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
     * Statistics results for the given table.
     *
     * @return Statistics results for the given table.
     */
    public StatisticsSnapshot getStatisticsReadouts() {
        return statisticsSnapshot;
    }
}
