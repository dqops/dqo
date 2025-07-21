/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.models.IncidentsFragmentFilter;
import com.dqops.data.incidents.snapshot.IncidentsSnapshot;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactory;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionMetadataService;
import com.dqops.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidentsDeleteServiceImpl implements IncidentsDeleteService {
    private final IncidentsSnapshotFactory incidentsSnapshotFactory;
    private final ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public IncidentsDeleteServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                      ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the incidents, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the incidents fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    @Override
    public DeleteStoredDataResult deleteSelectedIncidentsFragment(IncidentsFragmentFilter filter,
                                                                  UserDomainIdentity userIdentity) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new LinkedHashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new LinkedHashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        String fullTableName = filter.getTableSearchFilters().getFullTableName();
        if (fullTableName != null) {
            PhysicalTableName tableSearchFilterParsed = PhysicalTableName.fromSchemaTableFilter(fullTableName);
            String schemaName = tableSearchFilterParsed.getSchemaName();
            String tableName = tableSearchFilterParsed.getTableName();

            conditions.put(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, new LinkedHashSet<>(Set.of(schemaName)));
            conditions.put(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, new LinkedHashSet<>(Set.of(tableName)));
        }


        DeleteStoredDataResult deleteStoredDataResult = new DeleteStoredDataResult();

        FileStorageSettings fileStorageSettings = IncidentsSnapshot.createIncidentsStorageSettings();
        List<String> connections = this.parquetPartitionMetadataService.listConnections(fileStorageSettings, userIdentity);
        if (connections == null) {
            // No connections present.
            return deleteStoredDataResult;
        }

        List<String> filteredConnections = connections.stream()
                .filter(filter.getTableSearchFilters().getConnectionNameSearchPattern()::match)
                .collect(Collectors.toList());

        for (String connectionName: filteredConnections) {
            IncidentsSnapshot currentSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userIdentity);

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

        return deleteStoredDataResult;
    }
}
