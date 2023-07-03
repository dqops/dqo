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
