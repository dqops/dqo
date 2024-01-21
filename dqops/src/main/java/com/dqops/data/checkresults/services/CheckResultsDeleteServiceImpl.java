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
package com.dqops.data.checkresults.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.models.CheckResultsFragmentFilter;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionMetadataService;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.PhysicalTableName;
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
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    @Override
    public DeleteStoredDataResult deleteSelectedCheckResultsFragment(CheckResultsFragmentFilter filter, UserDomainIdentity userIdentity) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new LinkedHashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new LinkedHashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME, new LinkedHashSet<>(filter.getColumnNames()));
        }

        DeleteStoredDataResult deleteStoredDataResult = new DeleteStoredDataResult();

        FileStorageSettings fileStorageSettings = CheckResultsSnapshot.createCheckResultsStorageSettings();
        List<String> connections = this.parquetPartitionMetadataService.listConnections(fileStorageSettings, userIdentity);
        if (connections == null) {
            // No connections present.
            return deleteStoredDataResult;
        }

        List<String> filteredConnections = connections.stream()
                .filter(filter.getTableSearchFilters().getConnectionNameSearchPattern()::match)
                .collect(Collectors.toList());

        for (String connectionName: filteredConnections) {
            List<PhysicalTableName> tables = this.parquetPartitionMetadataService.listTablesForConnection(
                    connectionName, fileStorageSettings, userIdentity);

            if (tables == null) {
                // No tables present for this connection.
                continue;
            }

            Collection<CheckResultsSnapshot> checkResultsSnapshots = tables.stream()
                    .filter(schemaTableName ->
                            {
                                SearchPattern schemaNameSearchPattern = filter.getTableSearchFilters().getSchemaNameSearchPattern();
                                SearchPattern tableNameSearchPattern = filter.getTableSearchFilters().getTableNameSearchPattern();

                                return (schemaNameSearchPattern == null || schemaNameSearchPattern.match(schemaTableName.getSchemaName())) &&
                                       (tableNameSearchPattern == null || tableNameSearchPattern.match(schemaTableName.getTableName()));
                            })
                    .map(tableName -> this.checkResultsSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnection(),
                            tableName,
                            userIdentity
                    ))
                    .collect(Collectors.toList());

            for (CheckResultsSnapshot currentSnapshot: checkResultsSnapshots) {
                LocalDate startDeletionRange = filter.getDateStart();
                LocalDate endDeletionRange = filter.getDateEnd();

                currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);

                if (currentSnapshot.getLoadedMonthlyPartitions() == null) {
                    continue;
                }

                DeleteStoredDataResult snapshotDeleteStoredDataResult = currentSnapshot.getDeleteResults();
                deleteStoredDataResult.concat(snapshotDeleteStoredDataResult);

                currentSnapshot.save();
            }
        }

        return deleteStoredDataResult;
    }
}
