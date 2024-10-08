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
package com.dqops.data.errorsamples.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.errorsamples.models.ErrorsSamplesFragmentFilter;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshot;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactory;
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

/**
 * Service that deletes error samples data from parquet files.
 */
@Service
public class ErrorSamplesDeleteServiceImpl implements ErrorSamplesDeleteService {
    private ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public ErrorSamplesDeleteServiceImpl(ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory,
                                         ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.errorSamplesSnapshotFactory = errorSamplesSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the error samples from a table, applying specific filters to get the fragment (if necessary).
     * @param userIdentity User identity that specifies the data domain.
     * @param filter Filter for the error samples fragment that is of interest.
     * @return Data delete operation summary.
     */
    @Override
    public DeleteStoredDataResult deleteSelectedErrorSamplesFragment(
            ErrorsSamplesFragmentFilter filter,
            UserDomainIdentity userIdentity) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new LinkedHashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new LinkedHashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME, new LinkedHashSet<>(filter.getColumnNames()));
        }

        DeleteStoredDataResult deleteStoredDataResult = new DeleteStoredDataResult();

        FileStorageSettings fileStorageSettings = ErrorSamplesSnapshot.createErrorSamplesStorageSettings();
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

            Collection<ErrorSamplesSnapshot> errorSamplesSnapshots = tables.stream()
                    .filter(schemaTableName ->
                            {
                                SearchPattern schemaNameSearchPattern = filter.getTableSearchFilters().getSchemaNameSearchPattern();
                                SearchPattern tableNameSearchPattern = filter.getTableSearchFilters().getTableNameSearchPattern();

                                return (schemaNameSearchPattern == null || schemaNameSearchPattern.match(schemaTableName.getSchemaName())) &&
                                        (tableNameSearchPattern == null || tableNameSearchPattern.match(schemaTableName.getTableName()));
                            })
                    .map(tableName -> this.errorSamplesSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnection(),
                            tableName,
                            userIdentity
                    ))
                    .collect(Collectors.toList());

            for (ErrorSamplesSnapshot currentSnapshot: errorSamplesSnapshots) {
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
