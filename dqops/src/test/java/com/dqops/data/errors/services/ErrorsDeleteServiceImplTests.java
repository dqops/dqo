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
package com.dqops.data.errors.services;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.factory.ErrorsTableFactory;
import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.errors.models.ErrorsFragmentFilter;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactory;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactoryImpl;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.storage.*;
import com.dqops.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ErrorsDeleteServiceImplTests extends BaseTest {
    private ErrorsDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings errorsStorageSettings;
    private ErrorsTableFactory errorsTableFactory;
    private LocalFileSystemCache localFileSystemCache;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @BeforeEach
    protected void setUp() throws Throwable {
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        DqoUserConfigurationProperties userConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(userConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        LocalFileSystemCache fileSystemCache = LocalFileSystemCacheObjectMother.createNewCache();

        HomeLocationFindService homeLocationFindService = new HomeLocationFindServiceImpl(userConfigurationProperties, dqoConfigurationProperties);
        SynchronizationStatusTrackerStub synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        localFileSystemCache = LocalFileSystemCacheObjectMother.createNewCache();
        LocalUserHomeFileStorageService localUserHomeFileStorageService = new LocalUserHomeFileStorageServiceImpl(
                homeLocationFindService, newLockManager, synchronizationStatusTracker, localFileSystemCache);

        ParquetPartitionMetadataService parquetPartitionMetadataService = new ParquetPartitionMetadataServiceImpl(newLockManager, localUserHomeFileStorageService);

        this.parquetPartitionStorageService = new ParquetPartitionStorageServiceImpl(
                parquetPartitionMetadataService,
                localUserHomeProviderStub,
                newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(),
                localUserHomeFileStorageService,
                synchronizationStatusTracker,
                fileSystemCache);

        this.errorsStorageSettings = ErrorsSnapshot.createErrorsStorageSettings();
        this.errorsTableFactory = new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());

        ErrorsSnapshotFactory errorsSnapshotFactory = new ErrorsSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.errorsTableFactory
        );

        this.sut = new ErrorsDeleteServiceImpl(errorsSnapshotFactory, parquetPartitionMetadataService);
    }

    private Table prepareSimplePartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table errorsTable = this.errorsTableFactory.createEmptyErrorsTable(tableName);

        Row row1 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row1.getRowNumber(), startDate);

        Row row2 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));

        Row row3 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));

        return errorsTable;
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        List<LocalDateTime> inspect = table1.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).asList();

        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
        String connectionName = "connection";
        String tableName = "tab1";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenDataAlreadyEmpty_thenPassWithSuccess() {
        String connectionName = "connection";
        String tableName = "tab1";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("sch.nonexistent_table");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedErrorsFragment(filter);
        Assertions.assertTrue(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partition1AfterDelete.getData());
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id1"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id2"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id3"));
        Assertions.assertNotEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenSearchPatternOnSchemaTable_thenDeleteResultsForMatchingTables() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        String tableName2 = "tab2";
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);
        PhysicalTableName physicalTableName2 = new PhysicalTableName("sch", tableName2);

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate1, "");
        Table table2 = prepareSimplePartitionTable(tableName2, startDate2, "");

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName2,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("*ch.*tab1");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedErrorsFragment(filter);
        Assertions.assertFalse(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate) {
        Table errorsTable = this.errorsTableFactory.createEmptyErrorsTable(tableName);

        Row row1 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), "id1");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        errorsTable.textColumn(ErrorsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        errorsTable.textColumn(ErrorsColumnNames.CHECK_NAME_COLUMN_NAME).set(row1.getRowNumber(), "check1");
        errorsTable.textColumn(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        errorsTable.textColumn(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row1.getRowNumber(), "col1");
        errorsTable.textColumn(ErrorsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "s1");
        errorsTable.textColumn(ErrorsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row1.getRowNumber(), "qd1");
        errorsTable.textColumn(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row1.getRowNumber(), "tg1");

        Row row2 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), "id2");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        errorsTable.textColumn(ErrorsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        errorsTable.textColumn(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        errorsTable.textColumn(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row2.getRowNumber(), "col2");
        errorsTable.textColumn(ErrorsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row2.getRowNumber(), "s2");
        errorsTable.textColumn(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row2.getRowNumber(), "tg1");

        Row row3 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), "id3");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        errorsTable.textColumn(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        errorsTable.textColumn(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row3.getRowNumber(), "col1");
        errorsTable.textColumn(ErrorsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row3.getRowNumber(), "qd2");

        Row row4 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), "id4");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row4.getRowNumber(), 1000);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        errorsTable.textColumn(ErrorsColumnNames.CHECK_NAME_COLUMN_NAME).set(row4.getRowNumber(), "check2");
        errorsTable.textColumn(ErrorsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "s1");
        errorsTable.textColumn(ErrorsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row4.getRowNumber(), "qd2");
        errorsTable.textColumn(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row4.getRowNumber(), "tg1");

        Row row5 = errorsTable.appendRow();
        errorsTable.textColumn(ErrorsColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), "id5");
        errorsTable.doubleColumn(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row5.getRowNumber(), 10000);
        errorsTable.dateTimeColumn(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        errorsTable.dateTimeColumn(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        errorsTable.textColumn(ErrorsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        errorsTable.textColumn(ErrorsColumnNames.CHECK_NAME_COLUMN_NAME).set(row5.getRowNumber(), "check1");
        errorsTable.textColumn(ErrorsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "s1");

        return errorsTable;
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterByCheckCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterByCheckNameAndCheckType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckName("check1");
            setCheckType("type1");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterByColumnNameAndSensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setColumnNames(new ArrayList<>(){{add("col2");}});
            setSensorName("s2");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterByQualityDimensionAndTimeGradient_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setQualityDimension("qd2");
            setTimeGradient("tg1");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterBySensorNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
            setSensorName("s1");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenFilterBySensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.errorsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorsStorageSettings);

        ErrorsFragmentFilter filter = new ErrorsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setSensorName("s1");
        }};

        this.sut.deleteSelectedErrorsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }
}
