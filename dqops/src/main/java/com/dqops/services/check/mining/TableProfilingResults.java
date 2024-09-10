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

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Data container object that contains profiling results for the table, and for all its columns.
 * It is used for mining checks based on previous profiling check results.
 */
public class TableProfilingResults {
    private TableDataAssetProfilingResults tableProfilingResults = new TableDataAssetProfilingResults();
    private Map<String, ColumnDataAssetProfilingResults> columns = new LinkedHashMap<>();
    private Map<String, Set<String>> dictionaries = new LinkedHashMap<>();
    private StatisticsResultsForTableModel statistics;
    private boolean missingStatisticsResults;
    private boolean missingProfilingChecksResults;
    private ZoneId timeZoneId;

    /**
     * Returns data profiling results for the table-level statistics and profiling checks.
     * @return Table level profiling results.
     */
    public TableDataAssetProfilingResults getTableProfilingResults() {
        return this.tableProfilingResults;
    }

    /**
     * Returns a map of dictionaries. The checks that map the accepted values checks will use these dictionaries.
     * @return Map of dictionaries, indexed by the dictionary name. The content are the dictionary entries.
     */
    public Map<String, Set<String>> getDictionaries() {
        return dictionaries;
    }

    /**
     * Returns true if there are no current basic statistics on the table and the rule mining engine failed to find relevant results.
     * @return True when no current statistics are available.
     */
    public boolean isMissingStatisticsResults() {
        return missingStatisticsResults;
    }

    /**
     * Returns true if there are no present results of profiling checks.
     * @return No results for profiling checks.
     */
    public boolean isMissingProfilingChecksResults() {
        return missingProfilingChecksResults;
    }

    /**
     * Sets a flag which says that the table has no current profiling check results, so the rule mining will be limited to using basic statistics, and will not be able to copy the configuration of profiling checks.
     * @param missingProfilingChecksResults Missing profiling checks results.
     */
    public void setMissingProfilingChecksResults(boolean missingProfilingChecksResults) {
        this.missingProfilingChecksResults = missingProfilingChecksResults;
    }

    /**
     * Returns the time zone of the data source. Used to convert dates and local datetimes to timestamps.
     * @return Time zone id of the data sources.
     */
    public ZoneId getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * Sets the time zone of the data source.
     * @param timeZoneId Time zone of the data source.
     */
    public void setTimeZoneId(ZoneId timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    /**
     * Retrieves statistics and recent profiling check results for a given column.
     * @param columnName Column name.
     * @return Column statistics summary or null, when there are no results for that column.
     */
    public ColumnDataAssetProfilingResults getColumnProfilingResults(String columnName) {
        ColumnDataAssetProfilingResults dataAssetProfilingResults = this.columns.get(columnName);
        if (dataAssetProfilingResults == null) {
            dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
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
        this.missingStatisticsResults = mostRecentStatisticsForTable == null || mostRecentStatisticsForTable.isEmpty();
        this.tableProfilingResults.importStatistics(mostRecentStatisticsForTable.getMetrics(), this.timeZoneId);

        Map<String, StatisticsResultsForColumnModel> columnStatistics = mostRecentStatisticsForTable.getColumns();
        for (Map.Entry<String, StatisticsResultsForColumnModel> columnModelEntry : columnStatistics.entrySet()) {
            DataAssetProfilingResults columnProfilingResults = this.getColumnProfilingResults(columnModelEntry.getKey());
            columnProfilingResults.importStatistics(columnModelEntry.getValue().getMetrics(), this.timeZoneId);
        }
    }

    /**
     * Retrieves the row count of the table from either the profiling check, or statistics. Because statistics are stored in the profiling checks, it takes the statistics.
     * @return Returns the row count.
     */
    public Long getRowCount() {
        ProfilingCheckResult rowCountCheckResults = this.getTableProfilingResults().getProfilingCheckByCheckName("profile_row_count", false);
        if (rowCountCheckResults == null || rowCountCheckResults.getActualValue() == null) {
            return null;
        }

        return rowCountCheckResults.getActualValue().longValue();
    }

    /**
     * Sets a row count in a way as it as assigned from a separate source. This method is mostly meant to be called from unit test.
     * @param rowCount Row count.
     */
    public void setRowCount(Long rowCount) {
        ProfilingCheckResult rowCountCheckResults = this.getTableProfilingResults().getProfilingCheckByCheckName("profile_row_count", true);
        if (rowCountCheckResults.getActualValue() == null) {
            rowCountCheckResults.setActualValue(rowCount == null ? null : rowCount.doubleValue());
            rowCountCheckResults.setExecutedAt(Instant.now());
        }
    }

    /**
     * Fill the value of the profile_not_nulls_count check, because it is not run by default as a profiling check, and we cannot depend on the value
     * captured from basic statistics, because it could be old. It is better to calculate it as a difference between the row count and null count that is always captured.
     */
    public void calculateMissingNotNullCounts() {
        Long rowCount = this.getRowCount();
        if (rowCount == null) {
            return;
        }

        for (DataAssetProfilingResults columnProfilingResults : this.columns.values()) {
            ProfilingCheckResult notNullsCountResult = columnProfilingResults.getProfilingCheckByCheckName("profile_not_nulls_count", true);
            ProfilingCheckResult nullsCountResult = columnProfilingResults.getProfilingCheckByCheckName("profile_nulls_count", true);

            if (notNullsCountResult.getActualValue() != null && nullsCountResult.getExecutedAt() != null && notNullsCountResult.getExecutedAt() != null &&
                    notNullsCountResult.getExecutedAt().plus(2, ChronoUnit.DAYS).isAfter(nullsCountResult.getExecutedAt())) {
                continue; // the not-nulls count is good enough to use it
            }

            if (nullsCountResult.getActualValue() == null) {
                continue; // the nulls count is unknown, no way to make the math
            }

            notNullsCountResult.setActualValue(rowCount.doubleValue() - nullsCountResult.getActualValue());
            notNullsCountResult.setExecutedAt(nullsCountResult.getExecutedAt());
        }
    }
}
