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
import ai.dqo.core.filesystem.virtual.FolderName;
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.status.FolderSynchronizationStatus;
import ai.dqo.core.synchronization.status.SynchronizationStatusTracker;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.storage.parquet.DqoTablesawParquetReader;
import ai.dqo.data.storage.parquet.DqoTablesawParquetWriteOptions;
import ai.dqo.data.storage.parquet.DqoTablesawParquetWriter;
import ai.dqo.data.storage.parquet.HadoopConfigurationProvider;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableMergeUtility;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ChecksumException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.RuntimeIOException;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Service that supports reading and writing parquet file partitions from a local file system.
 */
@Service
public class ParquetPartitionStorageServiceImpl implements ParquetPartitionStorageService {
    private final ParquetPartitionMetadataService parquetPartitionMetadataService;
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;
    private final UserHomeLockManager userHomeLockManager;
    private HadoopConfigurationProvider hadoopConfigurationProvider;
    private LocalUserHomeFileStorageService localUserHomeFileStorageService;
    private SynchronizationStatusTracker synchronizationStatusTracker;

    /**
     * Dependency injection constructor.
     * @param parquetPartitionMetadataService Metadata service for READ info about the state in the storage.
     * @param localDqoUserHomePathProvider DQO User home finder.
     * @param userHomeLockManager User home lock manager.
     * @param hadoopConfigurationProvider Hadoop configuration provider.
     * @param localUserHomeFileStorageService Local DQO_USER_HOME file storage service.
     * @param synchronizationStatusTracker File synchronization status tracker.
     */
    @Autowired
    public ParquetPartitionStorageServiceImpl(
            ParquetPartitionMetadataService parquetPartitionMetadataService,
            LocalDqoUserHomePathProvider localDqoUserHomePathProvider,
            UserHomeLockManager userHomeLockManager,
            HadoopConfigurationProvider hadoopConfigurationProvider,
            LocalUserHomeFileStorageService localUserHomeFileStorageService,
            SynchronizationStatusTracker synchronizationStatusTracker) {
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
        this.userHomeLockManager = userHomeLockManager;
        this.hadoopConfigurationProvider = hadoopConfigurationProvider;
        this.localUserHomeFileStorageService = localUserHomeFileStorageService;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
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
        Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
        Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);
        String hivePartitionFolderName = HivePartitionPathUtility.makeHivePartitionPath(partitionId);
        Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
        Path targetParquetFilePath = partitionPath.resolve(storageSettings.getParquetFileName());
        File targetParquetFile = targetParquetFilePath.toFile();

        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType())) {
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
        catch (RuntimeIOException ex) {
            if (ex.getCause() instanceof ChecksumException) {
                // Corrupted partition file -> remove the file
                deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType());
                return new LoadedMonthlyPartition(partitionId, 0L, null);
            }
            throw new DataStorageIOException(ex.getMessage(), ex);
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
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates indicating the range need to be specified");
        }

        return loadRecentPartitionsForMonthsRange(connectionName, tableName, start, end, storageSettings, columnNames, Integer.MAX_VALUE);
    }

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>,
     * limited to the <code>monthsCount</code> most recent partitions.
     * This method may read more rows than expected, because it operates on full months.
     *
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param startBoundary   Start date, that is truncated to the beginning of the first loaded month. If null, then the oldest loaded partition marks the limit.
     * @param endBoundary     End date, the whole month of the given date is loaded. If null, then the current month is taken.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param monthsCount     Limit of partitions loaded, with the preference of the most recent ones.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadRecentPartitionsForMonthsRange(String connectionName,
                                                                                              PhysicalTableName tableName,
                                                                                              LocalDate startBoundary,
                                                                                              LocalDate endBoundary,
                                                                                              FileStorageSettings storageSettings,
                                                                                              String[] columnNames,
                                                                                              int monthsCount) {
        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        LocalDate startNonNull = startBoundary;
        if (startNonNull == null) {
            startNonNull = this.parquetPartitionMetadataService.getOldestStoredPartitionMonth(
                    connectionName, tableName, storageSettings).orElse(null);
        }
        if (startNonNull == null) {
            // No data stored for this table
            return null;
        }

        LocalDate endNonNull = Objects.requireNonNullElse(endBoundary, LocalDate.now());

        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(startNonNull);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(endNonNull);

        for (LocalDate currentMonth = endMonth; !currentMonth.isBefore(startMonth) && monthsCount > 0;
             currentMonth = currentMonth.minusMonths(1L)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings, columnNames);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);

                if (currentMonthPartition.getData() != null) {
                    --monthsCount;
                }
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
        boolean hasChanges = this.savePartitionInternal(loadedPartition, tableDataChanges, storageSettings);
        if (hasChanges) {
            this.synchronizationStatusTracker.changeFolderSynchronizationStatus(storageSettings.getTableType(), FolderSynchronizationStatus.changed);
        }
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
    private boolean savePartitionInternal(LoadedMonthlyPartition loadedPartition,
                              TableDataChanges tableDataChanges,
                              FileStorageSettings storageSettings) {
        try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(storageSettings.getTableType())) {
            Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
            Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);

            String hivePartitionFolderName = HivePartitionPathUtility.makeHivePartitionPath(loadedPartition.getPartitionId());
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
                return false; // nothing to change in this partition
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
                    return false;
                }
            }

            if (dataToSave == null || dataToSave.isEmpty()) {
                // ensure the partition data is deleted
                if (targetParquetFile.exists()) {
                    boolean success = this.deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType());
                    if (success) {
                        return true;
                    }
                    // If unsuccessful, then proceed with the regular deleting method.
                }
                else {
                    // there is no file, therefore no data to delete
                    return false;
                }
            }

            DqoTablesawParquetWriteOptions writeOptions = DqoTablesawParquetWriteOptions
                    .dqoBuilder(targetParquetFile)
                    .withOverwrite(true)
                    .withCompressionCode(storageSettings.getCompressionCodec())
                    .build();

            Configuration hadoopConfiguration = this.hadoopConfigurationProvider.getHadoopConfiguration();
            new DqoTablesawParquetWriter(hadoopConfiguration).write(dataToSave, writeOptions);

            return true;
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a parquet file responsible for holding a partition, along with its .crc checksum file.
     * If there are no other files or folders in this folder, deletes the folders in a cascading pattern until it reaches the root .data folder.
     * @param targetPartitionFilePath Path to the .parquet file with the partition.
     * @param tableType Table type to delete (RuleResults, SensorReadouts, etc.)
     * @return True if deletion proceeded successfully. False otherwise.
     */
    protected boolean deleteParquetPartitionFile(Path targetPartitionFilePath, DqoRoot tableType) {
        try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(tableType)) {
            Path homeRelativePath = this.localDqoUserHomePathProvider.getLocalUserHomePath().relativize(targetPartitionFilePath);

            if (Files.isDirectory(homeRelativePath)) {
                return false;
            }

            LinkedList<FolderName> homeRelativeFoldersList = new LinkedList<>();
            for (Path fileSystemName : homeRelativePath.getParent()) {
                homeRelativeFoldersList.add(
                        FolderName.fromFileSystemName(fileSystemName.toString())
                );
            }

            if (!this.localUserHomeFileStorageService.deleteFile(
                    new HomeFilePath(
                            new HomeFolderPath(homeRelativeFoldersList.toArray(FolderName[]::new)),
                            homeRelativePath.getFileName().toString()
                    )
            )) {
                // Deleting .parquet file failed.
                return false;
            }

            if (!this.localUserHomeFileStorageService.deleteFile(
                    new HomeFilePath(
                            new HomeFolderPath(homeRelativeFoldersList.toArray(FolderName[]::new)),
                            ".%s.crc".formatted(homeRelativePath.getFileName().toString())
                    )
            )) {
                // Deleting .crc file failed.
                return false;
            }

            while (homeRelativeFoldersList.size() > 1) {
                // Delete all remaining folders, if empty, to the extent allowed by the lock.
                HomeFolderPath homeFolderPath = new HomeFolderPath(homeRelativeFoldersList.toArray(FolderName[]::new));

                int filesInFolderCount = this.localUserHomeFileStorageService.listFiles(homeFolderPath).size();
                int foldersInFolderCount = this.localUserHomeFileStorageService.listFolders(homeFolderPath).size();
                if (filesInFolderCount + foldersInFolderCount != 0) {
                    // Folder is not empty
                    break;
                }

                if (!this.localUserHomeFileStorageService.tryDeleteFolder(homeFolderPath)) {
                    // Deleting the folder failed
                    // TODO: Add a warn log message.
                    break;
                }

                homeRelativeFoldersList.removeLast();
            }

            return true;
        }
    }
}
