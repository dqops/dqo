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
package com.dqops.data.readouts.services;

import com.dqops.data.models.DataDeleteResult;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.models.SensorReadoutsFragmentFilter;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionMetadataService;
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
     * @return Data delete operation summary.
     */
    @Override
    public DataDeleteResult deleteSelectedSensorReadoutsFragment(SensorReadoutsFragmentFilter filter) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new HashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new HashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }

        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME, new HashSet<>(filter.getColumnNames()));
        }

        DataDeleteResult dataDeleteResult = new DataDeleteResult();

        FileStorageSettings fileStorageSettings = SensorReadoutsSnapshot.createSensorReadoutsStorageSettings();
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

            Collection<SensorReadoutsSnapshot> sensorReadoutsSnapshots = tables.stream()
                    .filter(schemaTableName ->
                            filter.getTableSearchFilters().getSchemaNameSearchPattern().match(schemaTableName.getSchemaName())
                                    && filter.getTableSearchFilters().getTableNameSearchPattern().match(schemaTableName.getTableName()))
                    .map(tableName -> this.sensorReadoutsSnapshotFactory.createSnapshot(
                            filter.getTableSearchFilters().getConnectionName(),
                            tableName
                    ))
                    .collect(Collectors.toList());

            for (SensorReadoutsSnapshot currentSnapshot: sensorReadoutsSnapshots) {
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
