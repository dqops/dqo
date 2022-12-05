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
package ai.dqo.data.storage;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.data.DataStorageIOException;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.ruleresults.filestorage.RuleResultsPartitioningKeys;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableMergeUtility;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service that supports reading and writing parquet file partitions from a local file system.
 */
@Service
public class ParquetPartitionStorageServiceImpl implements ParquetPartitionStorageService {
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;
    private final UserHomeLockManager userHomeLockManager;

    /**
     * Dependency injection constructor.
     * @param localDqoUserHomePathProvider DQO User home finder.
     * @param userHomeLockManager User home lock manager.
     */
    @Autowired
    public ParquetPartitionStorageServiceImpl(
            LocalDqoUserHomePathProvider localDqoUserHomePathProvider,
            UserHomeLockManager userHomeLockManager) {
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
        this.userHomeLockManager = userHomeLockManager;
    }

    /**
     * Creates a hive compatible partition path (folder) for the file.
     * @param partitionId Partition id that identifies the connection, table (with a schema) and the month.
     * @return Hive compatible partition folder path, followed by '/'.
     */
    public String makeHivePartitionPath(ParquetPartitionId partitionId) {
        assert partitionId.getMonth().getDayOfMonth() == 1;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(RuleResultsPartitioningKeys.CONNECTION);
        stringBuilder.append('=');
        String encodedConnection = URLEncoder.encode(partitionId.getConnectionName(), StandardCharsets.UTF_8);
        stringBuilder.append(encodedConnection);
        stringBuilder.append('/');

        stringBuilder.append(RuleResultsPartitioningKeys.TARGET);
        stringBuilder.append('=');
        String encodedTable = URLEncoder.encode(partitionId.getTableName().toString(), StandardCharsets.UTF_8);
        stringBuilder.append(encodedTable);
        stringBuilder.append('/');

        stringBuilder.append(RuleResultsPartitioningKeys.MONTH);
        stringBuilder.append('=');
        stringBuilder.append(partitionId.getMonth());
        stringBuilder.append('/');

        String hivePartitionPath = stringBuilder.toString();
        return hivePartitionPath;
    }

    /**
     * Reads the data of one monthly partition.
     * @param partitionId Partition id.
     * @param storageSettings Storage settings that identify the target table type that is loaded.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    @Override
    public LoadedMonthlyPartition loadPartition(ParquetPartitionId partitionId, FileStorageSettings storageSettings) {
        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType())) {
            Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
            Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);
            String hivePartitionFolderName = makeHivePartitionPath(partitionId);
            Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
            Path targetParquetFilePath = partitionPath.resolve(storageSettings.getParquetFileName());
            File targetParquetFile = targetParquetFilePath.toFile();

            if (!targetParquetFile.exists()) {
                return new LoadedMonthlyPartition(partitionId, 0L, null);
            }

            TablesawParquetReadOptions readOptions = TablesawParquetReadOptions
                    .builder(targetParquetFile)
                    .build();
            Table data = new TablesawParquetReader().read(readOptions);

            LoadedMonthlyPartition loadedPartition = new LoadedMonthlyPartition(partitionId, targetParquetFile.lastModified(), data);
            return loadedPartition;
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more rows than expected, because it operates on full months.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param start Start date, that is truncated to the beginning of the first loaded month.
     * @param end End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);
            }
        }

        return resultPartitions;
    }

    /**
     * Saves the data for a single monthly partition. Finds the range of data for that month in the <code>tableDataChanges</code>.
     * Also deletes rows that should be deleted. In case that the file was modified since it was loaded into the loaded partition
     * snapshot (parameter: <code>loadedPartition</code>), the partition is reloaded using an exclusive write lock and the changes
     * are applied to the most recent data.
     * @param loadedPartition Loaded partition, identifies the partition id. The loaded partition may contain no data.
     * @param tableDataChanges Table data changes to be applied.
     * @param storageSettings Storage settings to identify the target folder, file names and column names used for matching.
     */
    @Override
    public void savePartition(LoadedMonthlyPartition loadedPartition,
                              TableDataChanges tableDataChanges,
                              FileStorageSettings storageSettings) {
        try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(storageSettings.getTableType())) {
            Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
            Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);
            String hivePartitionFolderName = makeHivePartitionPath(loadedPartition.getPartitionId());
            Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
            Path targetParquetFilePath = partitionPath.resolve(storageSettings.getParquetFileName());
            File targetParquetFile = targetParquetFilePath.toFile();

            Table newOrChangedDataPartitionMonth = null;

            if (tableDataChanges.getNewOrChangedRows() != null) {
                // new or updated rows are given
                DateTimeColumn timePeriodColumn = (DateTimeColumn) tableDataChanges.getNewOrChangedRows()
                        .column(storageSettings.getTimePeriodColumnName());
                LocalDate startOfNextMonth = loadedPartition.getPartitionId().getMonth().plus(1L, ChronoUnit.MONTHS);
                Selection selectionOfRowsInPartitionMonth = timePeriodColumn.isOnOrAfter(loadedPartition.getPartitionId().getMonth())
                        .and(timePeriodColumn.isBefore(startOfNextMonth));

                if (selectionOfRowsInPartitionMonth.size() > 0) {
                    newOrChangedDataPartitionMonth = tableDataChanges.getNewOrChangedRows().where(selectionOfRowsInPartitionMonth);
                }
            }

            if (newOrChangedDataPartitionMonth == null &&
                    (tableDataChanges.getDeletedIds() == null || tableDataChanges.getDeletedIds().size() == 0)) {
                return; // nothing to change in this partition
            }

            Table partitionDataOld = loadedPartition.getData() != null ? loadedPartition.getData().copy() : null;

            if (targetParquetFile.exists() && targetParquetFile.lastModified() != loadedPartition.getLastModified()) {
                // file was modified
                TablesawParquetReadOptions readOptions = TablesawParquetReadOptions
                        .builder(targetParquetFile)
                        .build();
                partitionDataOld = new TablesawParquetReader().read(readOptions); // reloading the data, now under an exclusive write lock
            }

            Table dataToSave = partitionDataOld;

            if (newOrChangedDataPartitionMonth != null) {
                if (partitionDataOld == null) {
                    dataToSave = newOrChangedDataPartitionMonth;
                } else {
                    String[] joinColumns = {
                            storageSettings.getIdStringColumnName()
                    };
                    dataToSave = TableMergeUtility.mergeNewResults(partitionDataOld, newOrChangedDataPartitionMonth, joinColumns);
                }
            }

            if (tableDataChanges.getDeletedIds() == null && tableDataChanges.getDeletedIds().size() > 0 && dataToSave != null) {
                Selection rowsToDeleteSelection = dataToSave.stringColumn(storageSettings.getIdStringColumnName())
                        .isNotIn(tableDataChanges.getDeletedIds());
                if (rowsToDeleteSelection.size() > 0) {
                    dataToSave = dataToSave.dropWhere(rowsToDeleteSelection);
                } else if (newOrChangedDataPartitionMonth == null) {
                    // no matching deletes and no new/updated changes in this monthly partition, skipping save
                    return;
                }
            }

            TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                    .builder(targetParquetFile)
                    .withOverwrite(true)
                    .withCompressionCode(storageSettings.getCompressionCodec())
                    .build();

            new TablesawParquetWriter().write(dataToSave, writeOptions);
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }
}
