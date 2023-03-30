/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.data.statistics.services;

import ai.dqo.core.configuration.DqoStatisticsCollectorConfigurationProperties;
import ai.dqo.data.statistics.factory.StatisticsResultDataType;
import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.services.models.StatisticsResultsForColumnModel;
import ai.dqo.data.statistics.services.models.StatisticsResultsForTableModel;
import ai.dqo.data.statistics.services.models.StatisticsMetricModel;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Service that provides access to read requested data statistics results.
 */
@Service
public class StatisticsDataServiceImpl implements StatisticsDataService {
    private final StatisticsSnapshotFactory statisticsResultsSnapshotFactory;
    private final DqoStatisticsCollectorConfigurationProperties statisticsConfigurationProperties;

    /**
     * Creates a statistics results data management service.
     * @param statisticsResultsSnapshotFactory Statistics results snapshot factory.
     * @param statisticsConfigurationProperties Statistics collectors configuration properties.
     */
    @Autowired
    public StatisticsDataServiceImpl(StatisticsSnapshotFactory statisticsResultsSnapshotFactory,
                                     DqoStatisticsCollectorConfigurationProperties statisticsConfigurationProperties) {
        this.statisticsResultsSnapshotFactory = statisticsResultsSnapshotFactory;
        this.statisticsConfigurationProperties = statisticsConfigurationProperties;
    }

    /**
     * Retrieves the most recent table statistics results for a given table.
     * @param connectionName Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param dataStreamName Data stream name.
     * @return Statistics results for the given table.
     */
    @Override
    public StatisticsResultsForTableModel getMostRecentStatisticsForTable(String connectionName,
                                                                          PhysicalTableName physicalTableName,
                                                                          String dataStreamName) {
        StatisticsResultsForTableModel tableStatisticsResults = new StatisticsResultsForTableModel();
        Table allData = loadStatisticsResultsForTable(connectionName, physicalTableName);
        if (allData == null) {
            return tableStatisticsResults; // no statistics data
        }
        Table selectedDataStreamData = allData.where(allData.stringColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).isEqualTo(dataStreamName));
        Table sortedResults = selectedDataStreamData.sortDescendingOn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME);
        StringColumn collectorNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME);
        StringColumn columnNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String collectorName = collectorNameColumn.get(i);

            if (columnNameColumn.isMissing(i)) {
                // table level

                if (tableStatisticsResults.getMetrics().stream()
                        .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getCollector(), collectorName))) {
                    tableStatisticsResults.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
            else {
                String columnName = columnNameColumn.get(i);
                StatisticsResultsForColumnModel columnModel = tableStatisticsResults.getColumns().get(columnName);
                if (columnModel == null) {
                    columnModel = new StatisticsResultsForColumnModel(connectionName, physicalTableName, columnName);
                    tableStatisticsResults.getColumns().put(columnName, columnModel);
                }

                if (columnModel.getMetrics().stream()
                        .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getCollector(), collectorName))) {
                    columnModel.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
        }

        return tableStatisticsResults;
    }

    /**
     * Retrieves the most recent table statistics results for a given column.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param columName         Column name.
     * @param dataStreamName    Data stream name.
     * @return Statistics results for the given table.
     */
    @Override
    public StatisticsResultsForColumnModel getMostRecentStatisticsForColumn(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            String columName,
                                                                            String dataStreamName) {
        StatisticsResultsForColumnModel columnStatisticsResults = new StatisticsResultsForColumnModel(connectionName, physicalTableName, columName);
        Table allData = loadStatisticsResultsForTable(connectionName, physicalTableName);
        if (allData == null) {
            return columnStatisticsResults; // no profiling data
        }
        Table selectedDataStreamData = allData.where(allData.stringColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME).isEqualTo(columName)
                .and(allData.stringColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).isEqualTo(dataStreamName)));
        Table sortedResults = selectedDataStreamData.sortDescendingOn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME);
        StringColumn collectorNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String collectorName = collectorNameColumn.get(i);
            if (columnStatisticsResults.getMetrics().stream()
                    .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getCollector(), collectorName))) {
                columnStatisticsResults.getMetrics().add(createMetricModel(sortedResults.row(i)));
            }
        }

        return columnStatisticsResults;
    }

    /**
     * Loads profiler results for a given table. Statistics results from the number of months limited by a statistics collector configuration parameter
     * {@link DqoStatisticsCollectorConfigurationProperties#getViewedStatisticsAgeMonths()} are loaded.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with results or null when no statistics results were found.
     */
    protected Table loadStatisticsResultsForTable(String connectionName, PhysicalTableName physicalTableName) {
        StatisticsSnapshot statisticsResultsSnapshot = this.statisticsResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        LocalDate todayDate = LocalDate.now();
        int monthsToLoad = this.statisticsConfigurationProperties.getViewedStatisticsAgeMonths() - 1;
        if (monthsToLoad < 0 || monthsToLoad > 36) {
            monthsToLoad = 3;
        }
        LocalDate startDate = todayDate.minus(monthsToLoad, ChronoUnit.MONTHS);
        statisticsResultsSnapshot.ensureMonthsAreLoaded(startDate, todayDate);

        Table allData = statisticsResultsSnapshot.getAllData();
        return allData;
    }

    /**
     * Creates a single metric model.
     * @param row Row with one metric result.
     * @return Metric model.
     */
    public StatisticsMetricModel createMetricModel(Row row) {
        StatisticsMetricModel result = new StatisticsMetricModel();
        result.setCategory(row.getString(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME));
        result.setCollector(row.getString(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME));
        String resultTypeString = row.getString(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME);
        StatisticsResultDataType statisticsResultDataType = StatisticsResultDataType.fromName(resultTypeString);
        result.setResultDataType(statisticsResultDataType);
        result.setCollectedAt(row.getDateTime(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME));

        switch (statisticsResultDataType) {
            case INTEGER:
                result.setResult(row.getLong(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME)); // TODO: store also typed values in typed fields
                break;
            case BOOLEAN:
                result.setResult(row.getBoolean(StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME));
                break;
            case FLOAT:
                result.setResult(row.getDouble(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME));
                break;
            case STRING:
                result.setResult(row.getString(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME));
                break;
            case INSTANT:
                result.setResult(row.getInstant(StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME));
                break;
            case DATE:
                result.setResult(row.getDate(StatisticsColumnNames.RESULT_DATE_COLUMN_NAME));
                break;
            case DATETIME:
                result.setResult(row.getDateTime(StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME));
                break;
            case TIME:
                result.setResult(row.getTime(StatisticsColumnNames.RESULT_TIME_COLUMN_NAME));
                break;
            default:
                break;
        }

        return result;
    }
}
