/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.services;

import com.dqops.core.configuration.DqoStatisticsCollectorConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.statistics.factory.StatisticsCollectorTarget;
import com.dqops.data.statistics.factory.StatisticsColumnNames;
import com.dqops.data.statistics.factory.StatisticsResultDataType;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.data.statistics.snapshot.StatisticsSnapshot;
import com.dqops.data.statistics.snapshot.StatisticsSnapshotFactory;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.tables.TableCopyUtility;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

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
     * @param dataGroup Data group name.
     * @param includeColumnLevelStatistics True when column level statistics should also be included.
     * @param userDomainIdentity User identity, with the data domain.
     * @return Statistics results for the given table.
     */
    @Override
    public StatisticsResultsForTableModel getMostRecentStatisticsForTable(String connectionName,
                                                                          PhysicalTableName physicalTableName,
                                                                          String dataGroup,
                                                                          boolean includeColumnLevelStatistics,
                                                                          UserDomainIdentity userDomainIdentity) {
        StatisticsResultsForTableModel tableStatisticsResults = new StatisticsResultsForTableModel();
        Table allData = loadStatisticsResultsForTable(connectionName, physicalTableName, userDomainIdentity);
        if (allData == null) {
            return tableStatisticsResults; // no statistics data
        }

        return parseStatisticsResults(connectionName, physicalTableName, dataGroup, includeColumnLevelStatistics, allData, tableStatisticsResults);
    }

    /**
     * Parses and filters results from statistics and returns a statistics model.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param dataGroup Data group name.
     * @param includeColumnLevelStatistics True when column level statistics should be included.
     * @param statisticsDataTable Dataset (table) with statistics to parse.
     * @param targetStatisticsResultsModel Target model to import additional results.
     * @return Target model returned.
     */
    public StatisticsResultsForTableModel parseStatisticsResults(String connectionName,
                                                                 PhysicalTableName physicalTableName,
                                                                 String dataGroup,
                                                                 boolean includeColumnLevelStatistics,
                                                                 Table statisticsDataTable,
                                                                 StatisticsResultsForTableModel targetStatisticsResultsModel) {
        Selection rowSelection = statisticsDataTable.stringColumn(StatisticsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).isEqualTo(dataGroup);
        if (!includeColumnLevelStatistics) {
            rowSelection = rowSelection.and(statisticsDataTable.stringColumn(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME)
                    .isEqualTo(StatisticsCollectorTarget.table.name()));
        }
        Table selectedDataStreamData = TableCopyUtility.copyTableFiltered(statisticsDataTable, rowSelection);

        Table sortedResults = selectedDataStreamData.sortDescendingOn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME);
        StringColumn collectorNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME);
        StringColumn columnNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME);
        DateTimeColumn collectedAtColumn = sortedResults.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);
        IntColumn sampleIndexColumn = sortedResults.intColumn(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String collectorName = collectorNameColumn.get(i);
            LocalDateTime collectedAt = collectedAtColumn.get(i);
            Integer sampleIndex = sampleIndexColumn.get(i);

            if (columnNameColumn.isMissing(i)) {
                // table level

                if (targetStatisticsResultsModel.getMetrics().stream()
                        .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getCollector(), collectorName))) {
                    targetStatisticsResultsModel.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
            else {
                String columnName = columnNameColumn.get(i);
                StatisticsResultsForColumnModel columnModel = targetStatisticsResultsModel.getColumns().get(columnName);

                if (columnModel == null) {
                    columnModel = new StatisticsResultsForColumnModel(connectionName, physicalTableName, columnName);
                    columnModel.setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters() {{
                        setConnection(connectionName);
                        setFullTableName(physicalTableName.toTableSearchFilter());
                        getColumnNames().add(columnName);
                        setEnabled(true);
                    }});
                    targetStatisticsResultsModel.getColumns().put(columnName, columnModel);
                }

                Optional<StatisticsMetricModel> firstMetric = columnModel.getMetrics()
                        .stream()
                        .filter(m -> Objects.equal(m.getCategory(), category) &&
                                Objects.equal(m.getCollector(), collectorName))
                        .findFirst();

                if (firstMetric.isEmpty() || Objects.equal(firstMetric.get().getCollectedAt(), collectedAt)) {
                    columnModel.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
        }

        return targetStatisticsResultsModel;
    }

    /**
     * Retrieves the most recent table statistics results for a given column.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param columName         Column name.
     * @param dataGroup         Data group name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Statistics results for the given table.
     */
    @Override
    public StatisticsResultsForColumnModel getMostRecentStatisticsForColumn(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            String columName,
                                                                            String dataGroup,
                                                                            UserDomainIdentity userDomainIdentity) {
        StatisticsResultsForColumnModel columnStatisticsResults = new StatisticsResultsForColumnModel(connectionName, physicalTableName, columName);
        columnStatisticsResults.setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters() {{
            setConnection(connectionName);
            setFullTableName(physicalTableName.toTableSearchFilter());
            getColumnNames().add(columName);
            setEnabled(true);
        }});

        Table allData = loadStatisticsResultsForTable(connectionName, physicalTableName, userDomainIdentity);
        if (allData == null) {
            return columnStatisticsResults; // no profiling data
        }
        Table selectedDataStreamData = TableCopyUtility.copyTableFiltered(allData, allData.stringColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME).isEqualTo(columName)
                .and(allData.stringColumn(StatisticsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).isEqualTo(dataGroup)));
        Table sortedResults = selectedDataStreamData.sortDescendingOn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME, StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME);
        StringColumn collectorNameColumn = sortedResults.stringColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME);
        DateTimeColumn collectedAtColumn = sortedResults.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);
        IntColumn sampleIndexColumn = sortedResults.intColumn(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String collectorName = collectorNameColumn.get(i);
            LocalDateTime collectedAt = collectedAtColumn.get(i);
            Integer sampleIndex = sampleIndexColumn.get(i);

            if (columnStatisticsResults.getMetrics().stream()
                    .noneMatch(m -> Objects.equal(m.getCategory(), category) &&
                            Objects.equal(m.getCollector(), collectorName) &&
                            !Objects.equal(m.getCollectedAt(), collectedAt) &&  // a newer result was already added, so we are skipping
                            Objects.equal(m.getSampleIndex(), sampleIndex))) {
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
     * @param userDomainIdentity User identity with the data domain name.
     * @return Table with results or null when no statistics results were found.
     */
    protected Table loadStatisticsResultsForTable(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity) {
        StatisticsSnapshot statisticsResultsSnapshot = this.statisticsResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName, userDomainIdentity);
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
     * Checks if there are any recent partition files with the results of basic statistics for the given table.
     * This operation is used to propose the user to collect statistics.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return True when there are any results, false when there are no results.
     */
    @Override
    public LocalDate getMostRecentStatisticsPartitionMonth(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity) {
        StatisticsSnapshot statisticsResultsSnapshot = this.statisticsResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName, userDomainIdentity);
        LocalDate todayDate = LocalDate.now();
        int monthsToLoad = this.statisticsConfigurationProperties.getViewedStatisticsAgeMonths() - 1;
        if (monthsToLoad < 0 || monthsToLoad > 36) {
            monthsToLoad = 3;
        }
        LocalDate startDate = todayDate.minus(monthsToLoad, ChronoUnit.MONTHS);
        statisticsResultsSnapshot.ensureNRecentMonthsAreLoaded(startDate, todayDate, 1);

        for (LoadedMonthlyPartition loadedMonthlyPartition : statisticsResultsSnapshot.getLoadedMonthlyPartitions().values()) {
            if (loadedMonthlyPartition != null && loadedMonthlyPartition.getData() != null && loadedMonthlyPartition.getData().rowCount() > 0) {
                return loadedMonthlyPartition.getPartitionId().getMonth();
            }
        }

        return null;
    }

    /**
     * Returns the last modification timestamp of the most recent partition with statistics.
     *
     * @param connectionName     Connection name.
     * @param physicalTableName  Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Not null timestamp when statistics are present - returns the file modification timestamp.
     */
    @Override
    public Instant getStatisticsLastModified(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity) {
        StatisticsSnapshot statisticsResultsSnapshot = this.statisticsResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName, userDomainIdentity);
        LocalDate todayDate = LocalDate.now();
        int monthsToLoad = this.statisticsConfigurationProperties.getViewedStatisticsAgeMonths() - 1;
        if (monthsToLoad < 0 || monthsToLoad > 36) {
            monthsToLoad = 3;
        }
        LocalDate startDate = todayDate.minus(monthsToLoad, ChronoUnit.MONTHS);

        Map<ParquetPartitionId, LoadedMonthlyPartition> partitionsInRange = statisticsResultsSnapshot.findPartitionsInRange(startDate, todayDate);
        if (partitionsInRange == null) {
            return null;
        }

        Instant mostRecentLastModified = null;

        for (LoadedMonthlyPartition loadedMonthlyPartition : partitionsInRange.values()) {
            if (loadedMonthlyPartition != null && loadedMonthlyPartition.getLastModified() != 0L) {
                Instant lastModified = Instant.ofEpochMilli(loadedMonthlyPartition.getLastModified());
                if (mostRecentLastModified == null || mostRecentLastModified.isBefore(lastModified)) {
                    mostRecentLastModified = lastModified;
                }
            }
        }

        return mostRecentLastModified;
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
        result.setSensorName(row.getString(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME));
        String resultTypeString = row.getString(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME);
        StatisticsResultDataType statisticsResultDataType = StatisticsResultDataType.fromName(resultTypeString);
        result.setResultDataType(statisticsResultDataType);
        result.setCollectedAt(row.getDateTime(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME));
        result.setExecutedAt(row.getInstant(StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME));

        if (!row.isMissing(StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME)) {
            result.setSampleCount(row.getLong(StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME));
        }
        if (!row.isMissing(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME)) {
            result.setSampleIndex(row.getInt(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME));
        }

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
