/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.models.SensorReadoutsFragmentFilter;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
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
public class SensorReadoutsDeleteServiceImpl implements SensorReadoutsDeleteService {
    private SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public SensorReadoutsDeleteServiceImpl(SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory,
                                           ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.sensorReadoutsSnapshotFactory = sensorReadoutsSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the readouts from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the readouts fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    @Override
    public DeleteStoredDataResult deleteSelectedSensorReadoutsFragment(SensorReadoutsFragmentFilter filter,
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
            conditions.put(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME, new LinkedHashSet<>(filter.getColumnNames()));
        }

        DeleteStoredDataResult deleteStoredDataResult = new DeleteStoredDataResult();

        FileStorageSettings fileStorageSettings = SensorReadoutsSnapshot.createSensorReadoutsStorageSettings();
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

            Collection<SensorReadoutsSnapshot> sensorReadoutsSnapshots = tables.stream()
                    .filter(schemaTableName ->
                    {
                        SearchPattern schemaNameSearchPattern = filter.getTableSearchFilters().getSchemaNameSearchPattern();
                        SearchPattern tableNameSearchPattern = filter.getTableSearchFilters().getTableNameSearchPattern();

                        return (schemaNameSearchPattern == null || schemaNameSearchPattern.match(schemaTableName.getSchemaName())) &&
                                (tableNameSearchPattern == null || tableNameSearchPattern.match(schemaTableName.getTableName()));
                    })
                    .map(tableName -> this.sensorReadoutsSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnection(),
                            tableName,
                            userIdentity
                    ))
                    .collect(Collectors.toList());

            for (SensorReadoutsSnapshot currentSnapshot: sensorReadoutsSnapshots) {
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
