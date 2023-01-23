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

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorNormalizedResultObjectMother;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.storage.localfiles.dqohome.LocalDqoHomeFileStorageService;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
public class ParquetPartitionStorageServiceImplTests extends BaseTest {
    private ParquetPartitionStorageServiceImpl sut;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private FileStorageSettings sensorReadoutsStorageSettings;

    @BeforeEach
    void setUp() {
        dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();

        HomeLocationFindService homeLocationFindService = new HomeLocationFindServiceImpl(dqoConfigurationProperties.getUser(), dqoConfigurationProperties);
        LocalUserHomeFileStorageService localUserHomeFileStorageService = new LocalUserHomeFileStorageServiceImpl(homeLocationFindService, newLockManager);

        this.sut = new ParquetPartitionStorageServiceImpl(localUserHomeProviderStub, newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(), localUserHomeFileStorageService);
        this.sensorReadoutsStorageSettings = SensorReadoutsSnapshot.createSensorReadoutsStorageSettings();
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderWithoutEncoding_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderConnectionEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection 1/%&\\",
                new PhysicalTableName("sch", "tab"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);

        Assertions.assertEquals("c=connection+1%2F%25%26%5C/t=sch.tab/m=2022-10-01/", partitionPath);
    }

    @Test
    void makeHivePartitionPath_whenSimpleFolderAndTableEncoded_thenReturnsFolderName() {
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                new PhysicalTableName("sch", "tab 1/%&\\"),
                LocalDate.of(2022, 10, 1));
        String partitionPath = this.sut.makeHivePartitionPath(partitionId);
        Assertions.assertEquals("c=connection/t=sch.tab+1%2F%25%26%5C/m=2022-10-01/", partitionPath);
    }

    @Test
    void savePartition_whenLoadedPartitionEmptyAndNewRowsInChanges_thenTableCouldBeLoaded() {
        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 40, 55));

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);

        LoadedMonthlyPartition loadedPartition = new LoadedMonthlyPartition(partitionId, 0L, null);
        this.sut.savePartition(loadedPartition, new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition= this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNotNull(reloadedPartition.getData());
        Assertions.assertNotEquals(0L, reloadedPartition.getLastModified());

        Table loadedTable = reloadedPartition.getData();
        Assertions.assertEquals(sourceTable.rowCount(), loadedTable.rowCount());
        Assertions.assertEquals(sourceTable.columnCount(), loadedTable.columnCount());
        Assertions.assertEquals(20.5, loadedTable.column(normalizedResults.getActualValueColumn().name()).get(0));
    }

    @Test
    void loadPartition_whenSubsetOfColumnsRequested_thenOnlyListedColumnsLoaded() {
        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 40, 55));
        normalizedResults.getTableNameColumn().set(row1.getRowNumber(), "tab1");
        normalizedResults.getConnectionNameColumn().set(row1.getRowNumber(), "conn1");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);

        LoadedMonthlyPartition loadedPartition = new LoadedMonthlyPartition(partitionId, 0L, null);
        this.sut.savePartition(loadedPartition, new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition= this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings,
                new String[] { SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME });
        Assertions.assertNotNull(reloadedPartition.getData());

        Table loadedTable = reloadedPartition.getData();
        Assertions.assertEquals(sourceTable.rowCount(), loadedTable.rowCount());
        Assertions.assertEquals(2, loadedTable.columnCount());
        Assertions.assertEquals(20.5, loadedTable.column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).get(0));
        Assertions.assertEquals("tab1", loadedTable.column(SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME).get(0));
        Assertions.assertTrue(loadedTable.containsColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
        Assertions.assertFalse(loadedTable.containsColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME));
    }

    @Test
    void savePartition_whenLoadedPartitionEmptyAndNewRowsInChangesButForDifferentMonth_thenReloadedTableIsStillEmptyAndNoParquetWritten() {
        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 5, 10, 14, 40, 55));

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);

        LoadedMonthlyPartition loadedPartition = new LoadedMonthlyPartition(partitionId, 0L, null);
        this.sut.savePartition(loadedPartition, new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNull(reloadedPartition.getData());
        Assertions.assertEquals(0L, reloadedPartition.getLastModified()); // no file
    }

    @Test
    void loadPartition_whenFileNotPresent_thenReturnsNull() {
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 3, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        Assertions.assertNotNull(reloadedPartition);
        Assertions.assertEquals(0L, reloadedPartition.getLastModified());
        Assertions.assertNull(reloadedPartition.getData());
    }

    @Test
    void loadPartitionsForMonthsRange_whenNoDataPresentForGivenRange_thenReturnsNullTable() {
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate start = LocalDate.of(2022, 1, 1);
        LocalDate end = LocalDate.of(2022, 3, 1);

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonths = this.sut.loadPartitionsForMonthsRange(
                "connection", tableName, start, end, this.sensorReadoutsStorageSettings, null);

        Assertions.assertEquals(3, loadedMonths.size());
        Assertions.assertTrue(loadedMonths.values().stream().allMatch(p -> p.getData() == null));
    }

    @Test
    void savePartition_whenChangesPresentForThreeMonths_thenSavesOnlyValuesForRequestedMonthThatMatchMonthlyPartition() {
        SensorReadoutsNormalizedResult normalizedResults = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResults.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResults.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));

        Row row2 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row2.getRowNumber(), 20.5);
        normalizedResults.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 2, 10, 14, 20, 55));

        Row row3 = sourceTable.appendRow();
        normalizedResults.getActualValueColumn().set(row3.getRowNumber(), 30.5);
        normalizedResults.getTimePeriodColumn().set(row3.getRowNumber(), LocalDateTime.of(2022, 3, 10, 14, 30, 55));

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 2, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNull(loadedPartition.getData()); // should be empty
        this.sut.savePartition(loadedPartition, new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Table table = reloadedPartition.getData();
        Assertions.assertNotNull(table);
        Assertions.assertEquals(1, table.rowCount());
        Assertions.assertEquals(20.5, table.column(normalizedResults.getActualValueColumn().name()).get(0));
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 10, 14, 20, 55),
                table.column(normalizedResults.getTimePeriodColumn().name()).get(0));
    }

    @Test
    void savePartition_whenNewRowsPresentForExistingPartition_thenRowsAreAppendedAndSaved() {
        SensorReadoutsNormalizedResult normalizedResultsCurrent = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResultsCurrent.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row1.getRowNumber(), "id1");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 1, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        this.sut.savePartition(new LoadedMonthlyPartition(partitionId), new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        SensorReadoutsNormalizedResult normalizedResultsNew = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable = normalizedResultsNew.getTable();
        Row row2 = changesTable.appendRow();
        normalizedResultsNew.getActualValueColumn().set(row2.getRowNumber(), 20.0);
        normalizedResultsNew.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 1, 20, 14, 10, 55));
        normalizedResultsNew.getIdColumn().set(row2.getRowNumber(), "id2");
        this.sut.savePartition(loadedPartition, new TableDataChanges(changesTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNotNull(reloadedPartition.getData());
        Assertions.assertNotEquals(0L, reloadedPartition.getLastModified());
        Assertions.assertNotEquals(loadedPartition.getLastModified(), reloadedPartition.getLastModified());

        Assertions.assertEquals(2, reloadedPartition.getData().rowCount());
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id2"));
    }

    @Test
    void savePartition_whenChangeForRowThatIsAlreadyPresent_thenUpdatesThatRow() {
        SensorReadoutsNormalizedResult normalizedResultsCurrent = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResultsCurrent.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row1.getRowNumber(), "id1");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 1, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        this.sut.savePartition(new LoadedMonthlyPartition(partitionId), new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        SensorReadoutsNormalizedResult normalizedResultsNew = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable = normalizedResultsNew.getTable();
        Row row2 = changesTable.appendRow();
        normalizedResultsNew.getActualValueColumn().set(row2.getRowNumber(), 20.0);
        normalizedResultsNew.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 1, 20, 14, 10, 55));
        normalizedResultsNew.getIdColumn().set(row2.getRowNumber(), "id1");
        this.sut.savePartition(loadedPartition, new TableDataChanges(changesTable), this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNotNull(reloadedPartition.getData());
        Assertions.assertNotEquals(0L, reloadedPartition.getLastModified());
        Assertions.assertNotEquals(loadedPartition.getLastModified(), reloadedPartition.getLastModified());

        Assertions.assertEquals(1, reloadedPartition.getData().rowCount());
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(reloadedPartition.getData().doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).contains(20.0));
    }

    @Test
    void savePartition_whenDeleteRequestedForRow_thenRemovesThatRow() {
        SensorReadoutsNormalizedResult normalizedResultsCurrent = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResultsCurrent.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row1.getRowNumber(), "id1");
        Row row2 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row2.getRowNumber(), 15.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row2.getRowNumber(), "id2");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 1, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        this.sut.savePartition(new LoadedMonthlyPartition(partitionId), new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        SensorReadoutsNormalizedResult normalizedResultsNew = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable = normalizedResultsNew.getTable();
        TableDataChanges tableDataChanges = new TableDataChanges(changesTable);
        tableDataChanges.getDeletedIds().add("id1");
        this.sut.savePartition(loadedPartition, tableDataChanges, this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNotNull(reloadedPartition.getData());
        Assertions.assertNotEquals(0L, reloadedPartition.getLastModified());
        Assertions.assertNotEquals(loadedPartition.getLastModified(), reloadedPartition.getLastModified());

        Assertions.assertEquals(1, reloadedPartition.getData().rowCount());
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertFalse(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(reloadedPartition.getData().doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).contains(15.5));
    }

    @Test
    void savePartition_whenDeleteRequestedForWholeTable_thenRemovesThatTableFile() {
        SensorReadoutsNormalizedResult normalizedResultsCurrent = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResultsCurrent.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row1.getRowNumber(), "id1");
        Row row2 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row2.getRowNumber(), 15.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row2.getRowNumber(), "id2");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 1, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        this.sut.savePartition(new LoadedMonthlyPartition(partitionId), new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        SensorReadoutsNormalizedResult normalizedResultsNew = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable = normalizedResultsNew.getTable();
        TableDataChanges tableDataChanges = new TableDataChanges(changesTable);
        tableDataChanges.getDeletedIds().add("id1");
        tableDataChanges.getDeletedIds().add("id2");
        this.sut.savePartition(loadedPartition, tableDataChanges, this.sensorReadoutsStorageSettings);

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNull(reloadedPartition.getData());
        Assertions.assertEquals(0L, reloadedPartition.getLastModified());
    }

    @Test
    void savePartition_whenLoadedPartitionWasModifiedAndAddingRows_thenReloadsCurrentPartitionAndAddsRow() {
        SensorReadoutsNormalizedResult normalizedResultsCurrent = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table sourceTable = normalizedResultsCurrent.getTable();
        Row row1 = sourceTable.appendRow();
        normalizedResultsCurrent.getActualValueColumn().set(row1.getRowNumber(), 10.5);
        normalizedResultsCurrent.getTimePeriodColumn().set(row1.getRowNumber(), LocalDateTime.of(2022, 1, 10, 14, 10, 55));
        normalizedResultsCurrent.getIdColumn().set(row1.getRowNumber(), "id1");

        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        LocalDate month = LocalDate.of(2022, 1, 1);
        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.sensorReadoutsStorageSettings.getTableType(),
                "connection",
                tableName,
                month);
        this.sut.savePartition(new LoadedMonthlyPartition(partitionId), new TableDataChanges(sourceTable), this.sensorReadoutsStorageSettings);
        LoadedMonthlyPartition loadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);

        SensorReadoutsNormalizedResult normalizedResultsNew1 = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable1 = normalizedResultsNew1.getTable();
        Row row2 = changesTable1.appendRow();
        normalizedResultsNew1.getActualValueColumn().set(row2.getRowNumber(), 20.0);
        normalizedResultsNew1.getTimePeriodColumn().set(row2.getRowNumber(), LocalDateTime.of(2022, 1, 20, 14, 10, 55));
        normalizedResultsNew1.getIdColumn().set(row2.getRowNumber(), "id2");
        this.sut.savePartition(loadedPartition, new TableDataChanges(changesTable1), this.sensorReadoutsStorageSettings);

        SensorReadoutsNormalizedResult normalizedResultsNew2 = SensorNormalizedResultObjectMother.createEmptyNormalizedResults();
        Table changesTable2 = normalizedResultsNew2.getTable();
        Row row3 = changesTable2.appendRow();
        normalizedResultsNew2.getActualValueColumn().set(row3.getRowNumber(), 30.0);
        normalizedResultsNew2.getTimePeriodColumn().set(row3.getRowNumber(), LocalDateTime.of(2022, 1, 20, 14, 10, 55));
        normalizedResultsNew2.getIdColumn().set(row3.getRowNumber(), "id3");
        this.sut.savePartition(loadedPartition, new TableDataChanges(changesTable2), this.sensorReadoutsStorageSettings);  // saving a new row, having an old "loadedPartition" that does not have changes from "changesTable1"

        LoadedMonthlyPartition reloadedPartition = this.sut.loadPartition(partitionId, this.sensorReadoutsStorageSettings, null);
        Assertions.assertNotNull(reloadedPartition.getData());
        Assertions.assertNotEquals(0L, reloadedPartition.getLastModified());
        Assertions.assertNotEquals(loadedPartition.getLastModified(), reloadedPartition.getLastModified());

        Assertions.assertEquals(3, reloadedPartition.getData().rowCount());
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(reloadedPartition.getData().stringColumn(SensorReadoutsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(reloadedPartition.getData().doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME).contains(20.0)); // value was not lost
    }
}
