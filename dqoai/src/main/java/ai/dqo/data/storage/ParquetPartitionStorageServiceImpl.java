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
import ai.dqo.core.filesystem.localfiles.LocalFileStorageService;
import ai.dqo.core.filesystem.virtual.FolderName;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.storage.parquet.*;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableMergeUtility;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import org.apache.hadoop.conf.Configuration;
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service that supports reading and writing parquet file partitions from a local file system.
 */
@Service
public class ParquetPartitionStorageServiceImpl implements ParquetPartitionStorageService {
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;
    private final UserHomeLockManager userHomeLockManager;
    private HadoopConfigurationProvider hadoopConfigurationProvider;
    private LocalUserHomeFileStorageService localUserHomeFileStorageService;

    /**
     * Dependency injection constructor.
     * @param localDqoUserHomePathProvider DQO User home finder.
     * @param userHomeLockManager User home lock manager.
     * @param hadoopConfigurationProvider Hadoop configuration provider.
     * @param localUserHomeFileStorageService Local DQO_USER_HOME file storage service.
     */
    @Autowired
    public ParquetPartitionStorageServiceImpl(
            LocalDqoUserHomePathProvider localDqoUserHomePathProvider,
            UserHomeLockManager userHomeLockManager,
            HadoopConfigurationProvider hadoopConfigurationProvider,
            LocalUserHomeFileStorageService localUserHomeFileStorageService) {
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
        this.userHomeLockManager = userHomeLockManager;
        this.hadoopConfigurationProvider = hadoopConfigurationProvider;
        this.localUserHomeFileStorageService = localUserHomeFileStorageService;
    }

    /**
     * Creates a hive compatible partition path (folder) for the file.
     * @param partitionId Partition id that identifies the connection, table (with a schema) and the month.
     * @return Hive compatible partition folder path, followed by '/'.
     */
    public String makeHivePartitionPath(ParquetPartitionId partitionId) {
        assert partitionId.getMonth().getDayOfMonth() == 1;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ParquetPartitioningKeys.CONNECTION);
        stringBuilder.append('=');
        String encodedConnection = URLEncoder.encode(partitionId.getConnectionName(), StandardCharsets.UTF_8);
        stringBuilder.append(encodedConnection);
        stringBuilder.append('/');

        stringBuilder.append(ParquetPartitioningKeys.TARGET);
        stringBuilder.append('=');
        String encodedTable = URLEncoder.encode(partitionId.getTableName().toString(), StandardCharsets.UTF_8);
        stringBuilder.append(encodedTable);
        stringBuilder.append('/');

        stringBuilder.append(ParquetPartitioningKeys.MONTH);
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
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    @Override
    public LoadedMonthlyPartition loadPartition(ParquetPartitionId partitionId, FileStorageSettings storageSettings, String[] columnNames) {
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

            TablesawParquetReadOptions.Builder optionsBuilder = TablesawParquetReadOptions
                    .builder(targetParquetFile);
            if (columnNames != null) {
                optionsBuilder = optionsBuilder.withOnlyTheseColumns(columnNames);
            }
            TablesawParquetReadOptions readOptions = optionsBuilder.build();

            Table data = new DqoTablesawParquetReader(this.hadoopConfigurationProvider.getHadoopConfiguration()).read(readOptions);

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
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings,
            String[] columnNames) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings, columnNames);
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

            Table partitionDataOld;
            if (targetParquetFile.exists() && targetParquetFile.lastModified() != loadedPartition.getLastModified()) {
                // file was modified
                TablesawParquetReadOptions readOptions = TablesawParquetReadOptions
                        .builder(targetParquetFile)
                        .build();
                partitionDataOld = new TablesawParquetReader().read(readOptions); // reloading the data, now under an exclusive write lock
            } else {
                partitionDataOld = loadedPartition.getData() != null ? loadedPartition.getData().copy() : null;
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

            if (tableDataChanges.getDeletedIds() != null && tableDataChanges.getDeletedIds().size() > 0 && dataToSave != null) {
                Selection rowsToDeleteSelection = dataToSave.stringColumn(storageSettings.getIdStringColumnName())
                        .isIn(tableDataChanges.getDeletedIds());
                if (rowsToDeleteSelection.size() > 0) {
                    dataToSave = dataToSave.dropWhere(rowsToDeleteSelection);
                } else if (newOrChangedDataPartitionMonth == null) {
                    // no matching deletes and no new/updated changes in this monthly partition, skipping save
                    return;
                }
            }

            if (dataToSave == null || dataToSave.isEmpty()) {
                // ensure the partition data is deleted
                if (targetParquetFile.exists()) {
                    // TODO: Change the splitting for god's sake.
                    FolderName[] partitionPathFolders = Arrays.stream(partitionPath.toString().split("\\\\"))
                            .map(FolderName::fromFileSystemName)
                            .collect(Collectors.toList())
                            .toArray(FolderName[]::new);
                    boolean success = this.localUserHomeFileStorageService.tryDeleteFolder(
                            new HomeFolderPath(partitionPathFolders));
                    if (success) {
                        return;
                    }
                    // If unsuccessful, then proceed with the regular deleting method.
                }
                else {
                    return;
                }
            }

            DqoTablesawParquetWriteOptions writeOptions = DqoTablesawParquetWriteOptions
                    .dqoBuilder(targetParquetFile)
                    .withOverwrite(true)
                    .withCompressionCode(storageSettings.getCompressionCodec())
                    .build();

            Configuration hadoopConfiguration = this.hadoopConfigurationProvider.getHadoopConfiguration();
            new DqoTablesawParquetWriter(hadoopConfiguration).write(dataToSave, writeOptions);
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }
}
