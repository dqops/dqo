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
package ai.dqo.data.readouts.filestorage;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.data.DataStorageIOException;
import ai.dqo.data.ChangeDeltaMode;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.index.LongIndex;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Service that provides access to sensor readouts.
 */
@Service
public class SensorReadoutsFileStorageServiceImpl implements SensorReadoutsFileStorageService {
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;
    private final UserHomeLockManager userHomeLockManager;

    /**
     * Dependency injection constructor.
     * @param localDqoUserHomePathProvider Local DQO User Home path finder.
     * @param userHomeLockManager User home lock manager.
     */
    @Autowired
    public SensorReadoutsFileStorageServiceImpl(LocalDqoUserHomePathProvider localDqoUserHomePathProvider,
                                                UserHomeLockManager userHomeLockManager) {
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
        this.userHomeLockManager = userHomeLockManager;
    }

    /**
     * Returns a supported delta mode.
     *
     * @return Delta mode that is supported.
     */
    @Override
    public ChangeDeltaMode getDeltaMode() {
        return ChangeDeltaMode.REPLACE_ALL;
    }

    /**
     * Creates a hive compatible partition path (folder) for the file.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param month Date of the month (the first day of the month).
     * @return Hive compatible partition folder path, followed by '/'.
     */
    public String makeHivePartitionPath(String connectionName, PhysicalTableName tableName, LocalDate month) {
        assert month.getDayOfMonth() == 1;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SensorReadoutsPartitioningKeys.CONNECTION);
        stringBuilder.append('=');
        String encodedConnection = URLEncoder.encode(connectionName, StandardCharsets.UTF_8);
        stringBuilder.append(encodedConnection);
        stringBuilder.append('/');

        stringBuilder.append(SensorReadoutsPartitioningKeys.TARGET);
        stringBuilder.append('=');
        String encodedTable = URLEncoder.encode(tableName.toString(), StandardCharsets.UTF_8);
        stringBuilder.append(encodedTable);
        stringBuilder.append('/');

        stringBuilder.append(SensorReadoutsPartitioningKeys.MONTH);
        stringBuilder.append('=');
        stringBuilder.append(month);
        stringBuilder.append('/');

        String hivePartitionPath = stringBuilder.toString();
        return hivePartitionPath;
    }

    /**
     * Saves sensor readout results for a connection, table and month.
     * @param data Data for the given month.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param month The date of the first date of the month.
     */
    public void saveTableMonth(Table data, String connectionName, PhysicalTableName tableName, LocalDate month) {
        try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(DqoRoot.DATA_SENSOR_READOUTS)) {
            Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, BuiltInFolderNames.SENSOR_READOUTS);
            Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);
            String hivePartitionFolderName = makeHivePartitionPath(connectionName, tableName, month);
            Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
            Path targetParquetFilePath = partitionPath.resolve(SensorReadoutsFileStorageService.PARQUET_FILE_NAME);
            File targetParquetFile = targetParquetFilePath.toFile();

            TablesawParquetWriteOptions writeOptions = TablesawParquetWriteOptions
                    .builder(targetParquetFile)
                    .withOverwrite(true)
                    .withCompressionCode(TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED)  // TODO: add configuration for the compression
                    .build();

            new TablesawParquetWriter().write(data, writeOptions);
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Reads data for a single month.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param month The date of the first date of the month.
     * @return Returns a dataset table with the results. Returns null if the data is not present (missing file).
     */
    public Table loadForTableAndMonth(String connectionName, PhysicalTableName tableName, LocalDate month) {
        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(DqoRoot.DATA_SENSOR_READOUTS)) {
            Path configuredStoragePath = Path.of(BuiltInFolderNames.DATA, BuiltInFolderNames.SENSOR_READOUTS);
            Path storeRootPath = this.localDqoUserHomePathProvider.getLocalUserHomePath().resolve(configuredStoragePath);
            String hivePartitionFolderName = makeHivePartitionPath(connectionName, tableName, month);
            Path partitionPath = storeRootPath.resolve(hivePartitionFolderName);
            Path targetParquetFilePath = partitionPath.resolve(SensorReadoutsFileStorageService.PARQUET_FILE_NAME);
            File targetParquetFile = targetParquetFilePath.toFile();

            if (!targetParquetFile.exists()) {
                return null;
            }

            TablesawParquetReadOptions readOptions = TablesawParquetReadOptions
                    .builder(targetParquetFile)
                    .build();
            Table table = new TablesawParquetReader().read(readOptions);
            return table;
        }
        catch (Exception ex) {
            throw new DataStorageIOException(ex.getMessage(), ex);
        }
    }

    /**
     * Loads sensor readouts that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more readouts than expected, because it operates on full months.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param start Start date, that is truncated to the beginning of the first loaded month.
     * @param end End date, the whole month of the given date is loaded.
     * @return Table with results.
     */
    public Table loadForTableAndMonthsRange(String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);

        Table resultTable = null;

        for( LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth); currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            Table currentMonthData = loadForTableAndMonth(connectionName, tableName, currentMonth);
            if (resultTable == null) {
                resultTable = currentMonthData;
            }
            else {
                if (currentMonthData != null) {
                    resultTable.append(currentMonthData);
                }
            }
        }

        return resultTable;
    }

    /**
     * Saves all months that cover the range between <code>start</code> and <code>end</code>.
     * @param table Table with full months for the given period.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param start Start date (a first day of the month is best).
     * @param end End date (the first day of the month is enough because the full month until the last day is saved).
     */
    public void saveTableInMonthsRange(Table table, String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);
        DateTimeColumn timePeriodColumn = (DateTimeColumn) table.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME);
        LongIndex timePeriodIndex = new LongIndex(timePeriodColumn);

        for( LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth); currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            LocalDateTime startDateTime = LocalDateTime.of(currentMonth, LocalTime.MIDNIGHT);
            Selection selectionStart = timePeriodIndex.atLeast(startDateTime);
            LocalDateTime endDateTime = LocalDateTime.of(currentMonth.plus(1L, ChronoUnit.MONTHS), LocalTime.MIDNIGHT);
            Selection selectionEnd = timePeriodIndex.lessThan(endDateTime);
            Selection selectionInMonth = selectionStart.and(selectionEnd);
            Table dataInMonth = table.where(selectionInMonth);

			this.saveTableMonth(dataInMonth, connectionName, tableName, currentMonth);
        }
    }
}
