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
package ai.dqo.data.errors.services;

import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.errors.models.ErrorsFragmentFilter;
import ai.dqo.data.errors.snapshot.ErrorsSnapshot;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.data.models.DataDeleteResult;
import ai.dqo.data.models.DataDeleteResultPartition;
import ai.dqo.data.normalization.CommonColumnNames;
import ai.dqo.data.storage.LoadedMonthlyPartition;
import ai.dqo.data.storage.ParquetPartitionId;
import ai.dqo.data.storage.ParquetPartitionMetadataService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ErrorsDeleteServiceImpl implements ErrorsDeleteService {
    private ErrorsSnapshotFactory errorsSnapshotFactory;
    private ParquetPartitionMetadataService parquetPartitionMetadataService;

    @Autowired
    public ErrorsDeleteServiceImpl(ErrorsSnapshotFactory errorsSnapshotFactory,
                                   ParquetPartitionMetadataService parquetPartitionMetadataService) {
        this.errorsSnapshotFactory = errorsSnapshotFactory;
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
    }

    /**
     * Deletes the errors from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the errors fragment that is of interest.
     * @return Data delete operation summary.
     */
    @Override
    public DataDeleteResult deleteSelectedErrorsFragment(ErrorsFragmentFilter filter) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new HashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new HashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }
        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME, new HashSet<>(filter.getColumnNames()));
        }

        DataDeleteResult dataDeleteResult = new DataDeleteResult();

        Collection<PhysicalTableName> tablesToDelete;
        if (filter.getTableSearchFilters().getSchemaTableName() == null) {
            tablesToDelete = this.parquetPartitionMetadataService.listTablesForConnection(
                    filter.getTableSearchFilters().getConnectionName(),
                    ErrorsSnapshot.createErrorsStorageSettings()
            );
        } else {
            tablesToDelete = new LinkedList<>();
            tablesToDelete.add(PhysicalTableName.fromSchemaTableFilter(filter.getTableSearchFilters().getSchemaTableName()));
        }

        if (tablesToDelete == null) {
            // No matching tables specified or found.
            return dataDeleteResult;
        }

        Collection<ErrorsSnapshot> errorsSnapshots = tablesToDelete.stream()
                .map(tableName -> this.errorsSnapshotFactory.createSnapshot(
                        filter.getTableSearchFilters().getConnectionName(),
                        tableName
                )).collect(Collectors.toList());

        for (ErrorsSnapshot currentSnapshot: errorsSnapshots) {
            LocalDate startDeletionRange = filter.getDateStart();
            LocalDate endDeletionRange = filter.getDateEnd();

            currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);
            Set<String> deletedIds = currentSnapshot.getTableDataChanges().getDeletedIds();

            for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitionEntry:
                    currentSnapshot.getLoadedMonthlyPartitions().entrySet()) {
                ParquetPartitionId partitionId = loadedPartitionEntry.getKey();
                LoadedMonthlyPartition loadedPartition = loadedPartitionEntry.getValue();

                int deletedRows = loadedPartition.getData()
                        .textColumn(CommonColumnNames.ID_COLUMN_NAME)
                        .isIn(deletedIds)
                        .size();
                boolean allRowsDeleted = deletedRows == loadedPartition.getData().rowCount();
                DataDeleteResultPartition partitionResult = new DataDeleteResultPartition(deletedRows, allRowsDeleted);

                dataDeleteResult.getPartitionResults().put(partitionId, partitionResult);
            }

            currentSnapshot.save();
        }

        return dataDeleteResult;
    }
}
