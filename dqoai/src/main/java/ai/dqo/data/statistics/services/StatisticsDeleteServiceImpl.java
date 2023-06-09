/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.statistics.services;

import ai.dqo.data.models.DataDeleteResult;
import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.models.StatisticsResultsFragmentFilter;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.data.storage.FileStorageSettings;
import ai.dqo.data.storage.ParquetPartitionMetadataService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that deletes statistics data from parquet files.
 */
@Service
public class StatisticsDeleteServiceImpl implements StatisticsDeleteService {
    private StatisticsSnapshotFactory statisticsSnapshotFactory;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public StatisticsDeleteServiceImpl(StatisticsSnapshotFactory statisticsSnapshotFactory,
                                       ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.statisticsSnapshotFactory = statisticsSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the statistics results from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the statistics results fragment that is of interest.
     * @return Data delete operation summary.
     */
    @Override
    public DataDeleteResult deleteSelectedStatisticsResultsFragment(StatisticsResultsFragmentFilter filter) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new HashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new HashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME, new HashSet<>(filter.getColumnNames()));
        }

        DataDeleteResult dataDeleteResult = new DataDeleteResult();

        FileStorageSettings fileStorageSettings = StatisticsSnapshot.createStatisticsStorageSettings();
        List<String> connections = this.parquetPartitionMetadataService.listConnections(fileStorageSettings);
        if (connections == null) {
            // No connections present.
            return dataDeleteResult;
        }

        List<String> filteredConnections = connections.stream()
                .filter(filter.getTableSearchFilters().getConnectionNameSearchPattern()::match)
                .collect(Collectors.toList());

        for (String connectionName: filteredConnections) {
            List<PhysicalTableName> tables = this.parquetPartitionMetadataService.listTablesForConnection(
                    connectionName, fileStorageSettings);

            if (tables == null) {
                // No tables present for this connection.
                continue;
            }

            Collection<StatisticsSnapshot> statisticsSnapshots = tables.stream()
                    .filter(schemaTableName ->
                            filter.getTableSearchFilters().getSchemaNameSearchPattern().match(schemaTableName.getSchemaName())
                                    && filter.getTableSearchFilters().gettableNameSearchPattern().match(schemaTableName.getTableName()))
                    .map(tableName -> this.statisticsSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnectionName(),
                            tableName
                    ))
                    .collect(Collectors.toList());

            for (StatisticsSnapshot currentSnapshot: statisticsSnapshots) {
                LocalDate startDeletionRange = filter.getDateStart();
                LocalDate endDeletionRange = filter.getDateEnd();

                currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);

                if (currentSnapshot.getLoadedMonthlyPartitions() == null) {
                    continue;
                }

                DataDeleteResult snapshotDataDeleteResult = currentSnapshot.getDeleteResults();
                dataDeleteResult.concat(snapshotDataDeleteResult);

                currentSnapshot.save();
            }
        }

        return dataDeleteResult;
    }
}
