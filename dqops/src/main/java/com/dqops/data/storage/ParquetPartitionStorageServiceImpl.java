/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.virtual.FolderName;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.locks.AcquiredExclusiveWriteLock;
import com.dqops.core.locks.AcquiredSharedReadLock;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.status.FolderSynchronizationStatus;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.normalization.CommonColumnNames;
import com.dqops.data.storage.parquet.DqoTablesawParquetReader;
import com.dqops.data.storage.parquet.DqoTablesawParquetWriter;
import com.dqops.data.storage.parquet.HadoopConfigurationProvider;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.tables.TableColumnUtility;
import com.dqops.utils.tables.TableCompressUtility;
import com.dqops.utils.tables.TableCopyUtility;
import com.dqops.utils.tables.TableMergeUtility;
import lombok.extern.slf4j.Slf4j;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ChecksumException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.RuntimeIOException;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that supports reading and writing parquet file partitions from a local file system.
 */
@Service
@Slf4j
public class ParquetPartitionStorageServiceImpl implements ParquetPartitionStorageService {
    /**
     * The number of retries to write a parquet file when another thread was trying to write the same file.
     * When a change was detected, new changes will be merged into the most current parquet file.
     */
    public static final int MAX_WRITE_RETRY_ON_WRITE_RACE_CONDITION = 10;

    private final ParquetPartitionMetadataService parquetPartitionMetadataService;
    private final LocalDqoUserHomePathProvider localDqoUserHomePathProvider;
    private final UserHomeLockManager userHomeLockManager;
    private final HadoopConfigurationProvider hadoopConfigurationProvider;
    private final LocalUserHomeFileStorageService localUserHomeFileStorageService;
    private final SynchronizationStatusTracker synchronizationStatusTracker;
    private final LocalFileSystemCache localFileSystemCache;

    /**
     * Dependency injection constructor.
     * @param parquetPartitionMetadataService Metadata service for READ info about the state in the storage.
     * @param localDqoUserHomePathProvider DQOps User home finder.
     * @param userHomeLockManager User home lock manager.
     * @param hadoopConfigurationProvider Hadoop configuration provider.
     * @param localUserHomeFileStorageService Local DQO_USER_HOME file storage service.
     * @param synchronizationStatusTracker File synchronization status tracker.
     * @param localFileSystemCache Local file system cache.
     */
    @Autowired
    public ParquetPartitionStorageServiceImpl(
            ParquetPartitionMetadataService parquetPartitionMetadataService,
            LocalDqoUserHomePathProvider localDqoUserHomePathProvider,
            UserHomeLockManager userHomeLockManager,
            HadoopConfigurationProvider hadoopConfigurationProvider,
            LocalUserHomeFileStorageService localUserHomeFileStorageService,
            SynchronizationStatusTracker synchronizationStatusTracker,
            LocalFileSystemCache localFileSystemCache) {
        this.parquetPartitionMetadataService = parquetPartitionMetadataService;
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
        this.userHomeLockManager = userHomeLockManager;
        this.hadoopConfigurationProvider = hadoopConfigurationProvider;
        this.localUserHomeFileStorageService = localUserHomeFileStorageService;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
        this.localFileSystemCache = localFileSystemCache;
    }


    /**
     * Reads the data of one monthly partition.
     * @param partitionId Partition id.
     * @param storageSettings Storage settings that identify the target table type that is loaded.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    @Override
    public LoadedMonthlyPartition loadPartition(ParquetPartitionId partitionId,
                                                FileStorageSettings storageSettings,
                                                String[] columnNames,
                                                UserDomainIdentity userIdentity) {
        Path targetParquetFilePath = makeParquetTargetFilePath(partitionId, storageSettings, userIdentity);
        File targetParquetFile = targetParquetFilePath.toFile();
        String[] effectiveColumnNames = columnNames != null ? columnNames :
                storageSettings.getTableSchemaSample().columnNames().toArray(String[]::new);

        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            LoadedMonthlyPartition cachedParquetFile = this.localFileSystemCache.getParquetFile(targetParquetFilePath);
            if (cachedParquetFile != null) {
                if (cachedParquetFile.getData() == null) {
                    return cachedParquetFile;
                }

                List<String> columnsInTable = cachedParquetFile.getData().columnNames();
                Set<String> columnNamesHashSet = new LinkedHashSet<>(columnsInTable);
                boolean allRequiredColumnsPresent = Arrays.stream(effectiveColumnNames).allMatch(columnNamesHashSet::contains);
                if (allRequiredColumnsPresent) {
                    if (effectiveColumnNames.length == columnsInTable.size()) {
                        return cachedParquetFile;
                    } else {
                        List<Column<?>> requestedColumns = cachedParquetFile.getData().columns().stream()
                                .filter(column -> ArrayUtils.contains(effectiveColumnNames, column.name()))
                                .collect(Collectors.toList());

                        Table tableWithRequestedColumns = Table.create(cachedParquetFile.getData().name(), requestedColumns);
                        LoadedMonthlyPartition smallerPartition = new LoadedMonthlyPartition(partitionId, cachedParquetFile.getLastModified(), tableWithRequestedColumns);
                        return smallerPartition;
                    }
                }
            }

            if (!targetParquetFile.exists()) {
                LoadedMonthlyPartition loadedMonthlyPartitionEmpty = new LoadedMonthlyPartition(partitionId, 0L, null);
                this.localFileSystemCache.storeParquetFile(targetParquetFilePath, loadedMonthlyPartitionEmpty);
                return loadedMonthlyPartitionEmpty;
            }

            TablesawParquetReadOptions.Builder optionsBuilder = TablesawParquetReadOptions
                    .builder(targetParquetFile);
            if (effectiveColumnNames != null) {
                optionsBuilder = optionsBuilder.withOnlyTheseColumns(effectiveColumnNames);
            }
            TablesawParquetReadOptions readOptions = optionsBuilder.build();

            try (DqoTablesawParquetReader dqoTablesawParquetReader = new DqoTablesawParquetReader(this.hadoopConfigurationProvider.getHadoopConfiguration())) {
                Table data = dqoTablesawParquetReader.read(readOptions);
//                TableCompressUtility.internStrings(data); // not necessary when using the StringColumn type

                LoadedMonthlyPartition loadedPartition = new LoadedMonthlyPartition(partitionId, targetParquetFile.lastModified(), data);
                this.localFileSystemCache.storeParquetFile(targetParquetFilePath, loadedPartition);
                return loadedPartition;
            }
        }
        catch (RuntimeIOException ex) {
            if (ex.getCause() instanceof ChecksumException) {
                // Corrupted partition file -> remove the file
                deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType(), userIdentity);
                return new LoadedMonthlyPartition(partitionId, 0L, null);
            }
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
        catch (Exception ex) {
            if (ex.getCause() instanceof RuntimeException && ex.getCause().getMessage() != null &&
                    ex.getCause().getMessage().contains("is not a Parquet file")) {
                // Corrupted partition file -> remove the file
                deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType(), userIdentity);
                return new LoadedMonthlyPartition(partitionId, 0L, null);
            }
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Makes a path to the parquet file.
     * @param partitionId Partition id.
     * @param storageSettings Storage settings.
     * @param userIdentity User identity, also identifies the data domain.
     * @return Parquet file path.
     */
    @NotNull
    protected Path makeParquetTargetFilePath(ParquetPartitionId partitionId, FileStorageSettings storageSettings, UserDomainIdentity userIdentity) {
        Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
        Path localUserHomePath = this.localDqoUserHomePathProvider.getLocalUserHomePath(userIdentity);
        Path storeRootPath = localUserHomePath.resolve(configuredStoragePath);
        String hivePartitionFolderName = HivePartitionPathUtility.makeHivePartitionPath(partitionId);
        Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
        Path targetParquetFilePath = partitionPath.resolve(storageSettings.getParquetFileName()).toAbsolutePath().normalize();
        return targetParquetFilePath;
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
     * @param userIdentity User identity, specifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings,
            String[] columnNames,
            UserDomainIdentity userIdentity) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates indicating the range need to be specified");
        }

        return loadRecentPartitionsForMonthsRange(connectionName, tableName, start, end, storageSettings, columnNames, Integer.MAX_VALUE, userIdentity);
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
     * @param maxRecentMonthsToLoad     Limit of partitions loaded, with the preference of the most recent ones.
     * @param userIdentity    User identity that also identifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadRecentPartitionsForMonthsRange(String connectionName,
                                                                                              PhysicalTableName tableName,
                                                                                              LocalDate startBoundary,
                                                                                              LocalDate endBoundary,
                                                                                              FileStorageSettings storageSettings,
                                                                                              String[] columnNames,
                                                                                              int maxRecentMonthsToLoad,
                                                                                              UserDomainIdentity userIdentity) {
        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        LocalDate startNonNull = startBoundary;
        if (startNonNull == null) {
            startNonNull = this.parquetPartitionMetadataService.getOldestStoredPartitionMonth(
                    connectionName, tableName, storageSettings, userIdentity).orElse(null);
        }
        if (startNonNull == null) {
            // No data stored for this table
            return null;
        }

        LocalDate endNonNull = Objects.requireNonNullElse(endBoundary, LocalDate.now());

        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(startNonNull);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(endNonNull);

        for (LocalDate currentMonth = endMonth; !currentMonth.isBefore(startMonth) && maxRecentMonthsToLoad > 0;
             currentMonth = currentMonth.minusMonths(1L)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(userIdentity.getDataDomainFolder(), storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings, columnNames, userIdentity);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);

                if (currentMonthPartition.getData() != null) {
                    --maxRecentMonthsToLoad;
                }
            }
        }

        return resultPartitions;
    }

    /**
     * Looks up multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method does not read the partitions. It only returns empty partitions for the time range when data was found.
     *
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param start           Start date, that is truncated to the beginning of the first loaded month.
     * @param end             End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> lookupPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings,
            UserDomainIdentity userIdentity) {
        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        List<ParquetPartitionId> storedPartitionsIds;
        if (tableName != null) {
            storedPartitionsIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(connectionName, tableName, storageSettings, userIdentity);
        } else {
            storedPartitionsIds = this.parquetPartitionMetadataService.getStoredPartitionsIds(connectionName, storageSettings, userIdentity);
        }

        if (storedPartitionsIds == null || storedPartitionsIds.isEmpty()) {
            // No data stored for this table
            return null;
        }

        for (ParquetPartitionId partitionId : storedPartitionsIds) {
            if (partitionId.getMonth().isBefore(start) || partitionId.getMonth().isAfter(end)) {
                continue;
            }

            Path targetParquetFilePath = makeParquetTargetFilePath(partitionId, storageSettings, userIdentity);
            File targetParquetFile = targetParquetFilePath.toFile();
            long lastModified = targetParquetFile.lastModified();

            LoadedMonthlyPartition foundMonthlyPartition = new LoadedMonthlyPartition(partitionId, lastModified, null);
            resultPartitions.put(partitionId, foundMonthlyPartition);
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
     * @param userIdentity User identity that also identifies the data domain.
     */
    @Override
    public void savePartition(LoadedMonthlyPartition loadedPartition,
                              TableDataChanges tableDataChanges,
                              FileStorageSettings storageSettings,
                              UserDomainIdentity userIdentity) {
        boolean hasChanges = this.savePartitionInternal(loadedPartition, tableDataChanges, storageSettings, userIdentity);
        if (hasChanges) {
            this.synchronizationStatusTracker.changeFolderSynchronizationStatus(storageSettings.getTableType(),
                    userIdentity.getDataDomainFolder(), FolderSynchronizationStatus.changed);
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
     * @param userIdentity User identity that also specifies the data domain.
     * @return True when the file was saved, false when it was empty and as removed.
     */
    private boolean savePartitionInternal(LoadedMonthlyPartition loadedPartition,
                                          TableDataChanges tableDataChanges,
                                          FileStorageSettings storageSettings,
                                          UserDomainIdentity userIdentity) {
        try {
            Path targetParquetFilePath = makeParquetTargetFilePath(loadedPartition.getPartitionId(), storageSettings, userIdentity);
            File targetParquetFile = targetParquetFilePath.toFile();

            Table newOrChangedDataPartitionMonth = null;

            if (tableDataChanges.getNewOrChangedRows() != null) {
                // new or updated rows are given
                Column<?> datePartitioningColumnOriginal = tableDataChanges.getNewOrChangedRows()
                        .column(storageSettings.getTimePeriodColumnName());
                DateTimeColumn timePeriodColumn = null;
                if (datePartitioningColumnOriginal instanceof DateTimeColumn) {
                    timePeriodColumn = (DateTimeColumn) datePartitioningColumnOriginal;
                }
                else if (datePartitioningColumnOriginal instanceof InstantColumn) {
                    timePeriodColumn = ((InstantColumn)datePartitioningColumnOriginal).asLocalDateTimeColumn(ZoneOffset.UTC);
                }
                else if (datePartitioningColumnOriginal instanceof DateColumn) {
                    timePeriodColumn = ((DateColumn)datePartitioningColumnOriginal).atTime(LocalTime.MIDNIGHT);
                }
                else {
                    throw new DqoRuntimeException("Time partitioning column " + storageSettings.getTimePeriodColumnName() + " is not a date/datetime/instant column.");
                }

                LocalDate startOfNextMonth = loadedPartition.getPartitionId().getMonth().plus(1L, ChronoUnit.MONTHS);
                Selection selectionOfRowsInPartitionMonth = timePeriodColumn.isOnOrAfter(loadedPartition.getPartitionId().getMonth())
                        .and(timePeriodColumn.isBefore(startOfNextMonth));

                if (selectionOfRowsInPartitionMonth.size() > 0) {
                    newOrChangedDataPartitionMonth = TableCopyUtility.copyTableFiltered(tableDataChanges.getNewOrChangedRows(), selectionOfRowsInPartitionMonth);
                }
            }

            if (newOrChangedDataPartitionMonth == null &&
                    (tableDataChanges.getDeletedIds() == null || tableDataChanges.getDeletedIds().size() == 0)) {
                return false; // nothing to change in this partition
            }

            for (int i = 0; i < MAX_WRITE_RETRY_ON_WRITE_RACE_CONDITION ; i++) {
                if (i > 0) {
                    Thread.sleep(i * 1000L); // add a sleep, in case of a race condition
                }

                Table partitionDataOld;
                Long beforeReadFileLastModifiedTimestamp = null;

                try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
                    if (targetParquetFile.exists()) {
                        beforeReadFileLastModifiedTimestamp = targetParquetFile.lastModified();
                    } else {
                        beforeReadFileLastModifiedTimestamp = null;
                    }

                    if (targetParquetFile.exists() && targetParquetFile.lastModified() != loadedPartition.getLastModified()) {
                        // file was modified
                        TablesawParquetReadOptions readOptions = TablesawParquetReadOptions
                                .builder(targetParquetFile)
                                .build();
                        partitionDataOld = new TablesawParquetReader().read(readOptions); // load the data
                    } else {
                        partitionDataOld = loadedPartition.getData() != null ? TableCopyUtility.fastTableCopy(loadedPartition.getData()) : null;
                    }
                }

                Table dataToSave = partitionDataOld;

                if (newOrChangedDataPartitionMonth != null) {
                    if (partitionDataOld == null) {
                        dataToSave = newOrChangedDataPartitionMonth;
                        InstantColumn createdAtColumn = dataToSave.instantColumn(CommonColumnNames.CREATED_AT_COLUMN_NAME);
                        createdAtColumn.setMissingTo(Instant.now());
                        StringColumn createdByColumn = dataToSave.stringColumn(CommonColumnNames.CREATED_BY_COLUMN_NAME);
                        createdByColumn.setMissingTo(userIdentity.getUserName());
                    } else {
                        String[] joinColumns = {
                                storageSettings.getIdStringColumnName()
                        };
                        dataToSave = TableMergeUtility.mergeNewResults(partitionDataOld, newOrChangedDataPartitionMonth, joinColumns,
                                userIdentity.getUserName(),
                                storageSettings.getCopiedColumnNames());
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
                        boolean success = this.deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType(), userIdentity);

                        if (success) {
                            return true;
                        }
                        // If unsuccessful, then proceed with the regular deleting method.
                    } else {
                        // there is no file, therefore no data to delete
                        return false;
                    }
                }

                // fix some invalid records
                Column<?> idColumn = dataToSave.column(storageSettings.getIdStringColumnName());
                Selection idValueMissing = idColumn.isMissing();
                if (!idValueMissing.isEmpty()) {
                    dataToSave = dataToSave.dropWhere(idValueMissing);

                    log.warn("Missing ID column values found when saving a partition, ID column name: " +
                            storageSettings.getIdStringColumnName() + ". Table: " + storageSettings.getTableType() + ", partition: " + loadedPartition.getPartitionId());
                }

                // fix some invalid records
                LongColumn connectionHashColumn = (LongColumn) TableColumnUtility.findColumn(dataToSave, CommonColumnNames.CONNECTION_HASH_COLUMN_NAME);
                if (connectionHashColumn != null) {
                    Selection connectionHashMissing = connectionHashColumn.isMissing();
                    if (!connectionHashMissing.isEmpty()) {
                        dataToSave = dataToSave.dropWhere(connectionHashMissing);

                        log.warn("Missing connection_hash column values found when saving a partition. Table: " +
                                storageSettings.getTableType() + ", partition: " + loadedPartition.getPartitionId());
                    }
                }


                Configuration hadoopConfiguration = this.hadoopConfigurationProvider.getHadoopConfiguration();
                byte[] parquetFileContent = new DqoTablesawParquetWriter(hadoopConfiguration).writeToByteArray(dataToSave);

                try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
                    Long currentLastModifiedTimestamp;
                    if (targetParquetFile.exists()) {
                        currentLastModifiedTimestamp = targetParquetFile.lastModified();
                    } else {
                        currentLastModifiedTimestamp = null;
                    }

                    if (!Objects.equals(currentLastModifiedTimestamp, beforeReadFileLastModifiedTimestamp)) {
                        continue; // retry
                    }

                    Path parentDirectory = targetParquetFilePath.getParent();
                    if (!Files.exists(parentDirectory)) {
                        Files.createDirectories(parentDirectory);
                    }

                    try {
                        Files.write(targetParquetFilePath, parquetFileContent);
                    } catch (Exception ex) {
                        this.localFileSystemCache.removeFile(targetParquetFilePath);
                        if (targetParquetFile.exists()) {
                            targetParquetFile.delete();
                        }
                        throw ex;
                    } finally {
                        long lastSavedTimestamp = targetParquetFile.lastModified();
                        LoadedMonthlyPartition newPartitionObject = new LoadedMonthlyPartition(loadedPartition.getPartitionId(), lastSavedTimestamp, dataToSave);
                        this.localFileSystemCache.storeParquetFile(targetParquetFilePath, newPartitionObject);
                    }

                    return true;
                }
            }

            throw new DataStorageIOException("Too many concurrent writes to the parquet file " + targetParquetFilePath + ". " +
                    "Cancelling write after " + MAX_WRITE_RETRY_ON_WRITE_RACE_CONDITION + " retries.");
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a partition file.
     * @param loadedPartitionId Partition id to delete.
     * @param storageSettings Storage settings.
     * @param userIdentity User identity that also specifies the data domain.
     * @return True when the file was removed, false otherwise.
     */
    @Override
    public boolean deletePartitionFile(ParquetPartitionId loadedPartitionId,
                                       FileStorageSettings storageSettings,
                                       UserDomainIdentity userIdentity) {
        try {
            Path targetParquetFilePath = makeParquetTargetFilePath(loadedPartitionId, storageSettings, userIdentity);
            File targetParquetFile = targetParquetFilePath.toFile();

            if (!targetParquetFile.exists()) {
                return false;
            }

            boolean success = this.deleteParquetPartitionFile(targetParquetFilePath, storageSettings.getTableType(), userIdentity);
            return success;
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
     * @param userIdentity User identity that identifies the data domain.
     * @return True if deletion proceeded successfully. False otherwise.
     */
    protected boolean deleteParquetPartitionFile(Path targetPartitionFilePath, DqoRoot tableType, UserDomainIdentity userIdentity) {
        try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(tableType, userIdentity.getDataDomainFolder())) {
            Path homeRelativePath = this.localDqoUserHomePathProvider.getLocalUserHomePath(userIdentity)
                    .relativize(targetPartitionFilePath);

            if (Files.isDirectory(targetPartitionFilePath)) {
                return false;
            }

            LinkedList<FolderName> homeRelativeFoldersList = new LinkedList<>();
            for (Path fileSystemName : homeRelativePath.getParent()) {
                homeRelativeFoldersList.add(
                        FolderName.fromFileSystemName(fileSystemName.toString())
                );
            }

            HomeFilePath homeFilePath = HomeFilePath.fromFilePath(userIdentity.getDataDomainFolder(), homeRelativePath.toString());
            new HomeFilePath(
                    new HomeFolderPath(userIdentity.getDataDomainFolder(), homeRelativeFoldersList.toArray(FolderName[]::new)),
                    homeRelativePath.getFileName().toString()
            );

            if (!this.localUserHomeFileStorageService.deleteFile(homeFilePath, false)) {
                // Deleting .parquet file failed.
                return false;
            }

            this.localFileSystemCache.removeFile(targetPartitionFilePath);


            while (homeRelativeFoldersList.size() > 1) {
                // Delete all remaining folders, if empty, to the extent allowed by the lock.
                HomeFolderPath homeFolderPath = new HomeFolderPath(userIdentity.getDataDomainFolder(), homeRelativeFoldersList.toArray(FolderName[]::new));

                int filesInFolderCount = this.localUserHomeFileStorageService.listFiles(homeFolderPath, false).size();
                int foldersInFolderCount = this.localUserHomeFileStorageService.listFolders(homeFolderPath, false).size();
                if (filesInFolderCount + foldersInFolderCount != 0) {
                    // Folder is not empty
                    break;
                }

                if (!this.localUserHomeFileStorageService.tryDeleteFolder(homeFolderPath, false)) {
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
