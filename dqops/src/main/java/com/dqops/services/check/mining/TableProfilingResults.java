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

package com.dqops.services.check.mining;

import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data container object that contains profiling results for the table, and for all its columns.
 * It is used for mining checks based on previous profiling check results.
 */
public class TableProfilingResults {
    private DataAssetProfilingResults tableProfilingResults = new DataAssetProfilingResults();
    private Map<String, DataAssetProfilingResults> columns = new LinkedHashMap<>();
    private StatisticsResultsForTableModel statistics;

    /**
     * Returns data profiling results for the table-level statistics and profiling checks.
     * @return Table level profiling results.
     */
    public DataAssetProfilingResults getTableProfilingResults() {
        return this.tableProfilingResults;
    }

    /**
     * Retrieves statistics and recent profiling check results for a given column.
     * @param columnName Column name.
     * @return Column statistics summary or null, when there are no results for that column.
     */
    public DataAssetProfilingResults getColumnProfilingResults(String columnName) {
        DataAssetProfilingResults dataAssetProfilingResults = this.columns.get(columnName);
        if (dataAssetProfilingResults == null) {
            dataAssetProfilingResults = new DataAssetProfilingResults();
            this.columns.put(columnName, dataAssetProfilingResults);
        }
        return dataAssetProfilingResults;
    }

    /**
     * Return all statistics loaded for the table (the original statistics model). Used when a check wants to use results from another check.
     * @return All statistics for the table and its columns.
     */
    public StatisticsResultsForTableModel getStatistics() {
        return statistics;
    }

    /**
     * Imports statistics into the profiling results, matching check names.
     * @param mostRecentStatisticsForTable Most recent statistics for the table.
     */
    public void importStatistics(StatisticsResultsForTableModel mostRecentStatisticsForTable) {
        this.statistics = mostRecentStatisticsForTable;
        this.tableProfilingResults.importStatistics(mostRecentStatisticsForTable.getMetrics());

        Map<String, StatisticsResultsForColumnModel> columnStatistics = mostRecentStatisticsForTable.getColumns();
        for (Map.Entry<String, StatisticsResultsForColumnModel> columnModelEntry : columnStatistics.entrySet()) {
            DataAssetProfilingResults columnProfilingResults = this.getColumnProfilingResults(columnModelEntry.getKey());
            columnProfilingResults.importStatistics(columnModelEntry.getValue().getMetrics());
        }
    }
}
