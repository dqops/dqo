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
package ai.dqo.data.checkresults.services;

import ai.dqo.data.checkresults.factory.CheckResultsColumnNames;
import ai.dqo.data.checkresults.models.CheckResultsFragmentFilter;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshot;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import ai.dqo.data.models.DataDeleteResult;
import ai.dqo.data.storage.FileStorageSettings;
import ai.dqo.data.storage.ParquetPartitionMetadataService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckResultsDeleteServiceImpl implements CheckResultsDeleteService {
    private CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public CheckResultsDeleteServiceImpl(CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                         ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the results from a table, applying specific filters to get the fragment.
     * @param filter Filter for the result fragment that is of interest.
     * @return Data delete operation summary.
     */
    @Override
    public DataDeleteResult deleteSelectedCheckResultsFragment(CheckResultsFragmentFilter filter) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new HashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new HashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME, new HashSet<>(filter.getColumnNames()));
        }

        DataDeleteResult dataDeleteResult = new DataDeleteResult();

        FileStorageSettings fileStorageSettings = CheckResultsSnapshot.createCheckResultsStorageSettings();
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

            Collection<CheckResultsSnapshot> checkResultsSnapshots = tables.stream()
                    .filter(tableName -> filter.getTableSearchFilters().getSchemaTableNameSearchPattern()
                            .match(tableName.toTableSearchFilter()))
                    .map(tableName -> this.checkResultsSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnectionName(),
                            tableName
                    ))
                    .collect(Collectors.toList());

            for (CheckResultsSnapshot currentSnapshot: checkResultsSnapshots) {
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
