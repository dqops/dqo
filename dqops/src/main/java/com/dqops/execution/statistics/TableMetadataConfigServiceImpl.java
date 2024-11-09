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

package com.dqops.execution.statistics;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.statistics.column.nulls.ColumnNullsNullsCountStatisticsCollectorSpec;
import com.dqops.statistics.column.range.ColumnRangeMaxValueStatisticsCollectorSpec;
import com.dqops.statistics.column.uniqueness.ColumnUniquenessDuplicateCountStatisticsCollectorSpec;
import com.dqops.utils.conversion.DateTypesConverter;
import com.dqops.utils.conversion.NumericTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Special service that is used during statistics collection to use statistics to configure the table.
 * This service configures the timestamp columns and the ID columns.
 */
@Component
public class TableMetadataConfigServiceImpl implements TableMetadataConfigService {
    /**
     * Column names that are probably the data ingestion time columns, containing the time of load.
     */
    public static final List<String> INGESTION_COLUMN_NAMES = List.of("LOADED", "INSERT", "INGEST");

    private final UserHomeContextFactory userHomeContextFactory;
    private final StatisticsDataService statisticsDataService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Configures the constructor by passing dependencies.
     * @param userHomeContextFactory User context factory to open a read-write user context to configure the table.
     * @param statisticsDataService Statistics data service to parse results.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public TableMetadataConfigServiceImpl(
            UserHomeContextFactory userHomeContextFactory,
            StatisticsDataService statisticsDataService,
            DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.statisticsDataService = statisticsDataService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Configures the table metadata by analyzing the table schema.
     * @param userDomainIdentity User domain identity to load the correct user home for a correct user domain.
     * @param connectionName Connection name.
     * @param physicalTableName Target table.
     * @param allNormalizedStatisticsTable The results recently captured from statistics.
     */
    @Override
    public void configureTableMetadata(UserDomainIdentity userDomainIdentity,
                                       String connectionName,
                                       PhysicalTableName physicalTableName,
                                       Table allNormalizedStatisticsTable) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, false);
        ConnectionWrapper connectionWrapper = userHomeContext.getUserHome().getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            return;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        Instant now = Instant.now();

        boolean hasTimestampColumnsConfigured = !tableSpec.getTimestampColumns().isDefault();
        boolean hasIdColumnsConfigured = tableSpec.getColumns().values().stream().anyMatch(columnSpec -> columnSpec.isId());
        if (hasTimestampColumnsConfigured && hasIdColumnsConfigured) {
            return; // already configured
        }

        StatisticsResultsForTableModel statisticsResultsForTableModel = this.statisticsDataService.parseStatisticsResults(
                connectionName, physicalTableName,
                CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true,
                allNormalizedStatisticsTable, new StatisticsResultsForTableModel());

        if (!hasTimestampColumnsConfigured) {
            // configure the timestamp columns

            Map<String, Instant> mostRecentDates = new LinkedHashMap<>();

            for (StatisticsResultsForColumnModel columnStatistics : statisticsResultsForTableModel.getColumns().values()) {
                StatisticsMetricModel maxValueMetric = columnStatistics.getMetricByCollectorName(ColumnRangeMaxValueStatisticsCollectorSpec.COLLECTOR_NAME);
                if (maxValueMetric == null) {
                    continue;
                }

                Instant maxDate = DateTypesConverter.toInstant(maxValueMetric.getResult(), defaultTimeZoneId);
                if (maxDate == null) {
                    continue;
                }

                if (maxDate.isAfter(now) || maxDate.isBefore(now.minus(7L, ChronoUnit.DAYS))) {
                    continue;
                }

                mostRecentDates.put(columnStatistics.getColumnName(), maxDate);
            }

            if (!mostRecentDates.isEmpty()) {
                if (mostRecentDates.size() == 1) {
                    String columnName = mostRecentDates.keySet().stream().findFirst().get();
                    tableSpec.getTimestampColumns().setPartitionByColumn(columnName);
                    tableSpec.getTimestampColumns().setEventTimestampColumn(columnName);
                } else {
                    List<Map.Entry<String, Instant>> columnsSortedAsc = mostRecentDates.entrySet().stream()
                            .sorted(Comparator.comparing(kv -> kv.getValue()))
                            .collect(Collectors.toList());

                    String oldestColumnName = columnsSortedAsc.get(0).getKey();
                    tableSpec.getTimestampColumns().setPartitionByColumn(oldestColumnName);
                    tableSpec.getTimestampColumns().setEventTimestampColumn(oldestColumnName);

                    for (int i = 1; i < columnsSortedAsc.size(); i++) {
                        String columnName = columnsSortedAsc.get(i).getKey();
                        String upperCaseColumnName = columnName.toUpperCase(Locale.ROOT);

                        for (String ingestionColumnNamePart : INGESTION_COLUMN_NAMES) {
                            if (upperCaseColumnName.contains(ingestionColumnNamePart)) {
                                tableSpec.getTimestampColumns().setIngestionTimestampColumn(columnName);
                            }
                        }

                        if (tableSpec.getTimestampColumns().getIngestionTimestampColumn() != null) {
                            break;
                        }
                    }
                }
            }
        }

        if (!hasIdColumnsConfigured) {
            // configure the ID columns

            for (StatisticsResultsForColumnModel columnStatistics : statisticsResultsForTableModel.getColumns().values()) {
                StatisticsMetricModel nullsCountMetric = columnStatistics.getMetricByCollectorName(ColumnNullsNullsCountStatisticsCollectorSpec.COLLECTOR_NAME);
                StatisticsMetricModel duplicateCountMetric = columnStatistics.getMetricByCollectorName(ColumnUniquenessDuplicateCountStatisticsCollectorSpec.COLLECTOR_NAME);

                if (nullsCountMetric != null && !Objects.equals(NumericTypeConverter.toLong(nullsCountMetric.getResult()), 0L)) {
                    continue; // has nulls, can't be an identifier
                }

                if (duplicateCountMetric != null && !Objects.equals(NumericTypeConverter.toLong(duplicateCountMetric.getResult()), 0L)) {
                    continue; // has duplicate values, can't be an identifier
                }

                String columnName = columnStatistics.getColumnName();
                ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
                if (columnSpec != null) {
                    columnSpec.setId(true);
                }
            }
        }

        userHomeContext.flush();
    }
}
