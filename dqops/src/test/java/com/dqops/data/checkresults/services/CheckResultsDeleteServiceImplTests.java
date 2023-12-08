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
package com.dqops.data.checkresults.services;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.factory.CheckResultsTableFactory;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.checkresults.models.CheckResultsFragmentFilter;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactoryImpl;
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

@SpringBootTest
public class CheckResultsDeleteServiceImplTests extends BaseTest {
    private CheckResultsDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings checkResultsStorageSettings;
    private CheckResultsTableFactory checkResultsTableFactory;
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
        DqoUserConfigurationProperties dqoUserConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoUserConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        LocalFileSystemCache fileSystemCache = LocalFileSystemCacheObjectMother.createNewCache();

        HomeLocationFindService homeLocationFindService = new HomeLocationFindServiceImpl(dqoUserConfigurationProperties, dqoConfigurationProperties);
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

        this.checkResultsStorageSettings = CheckResultsSnapshot.createCheckResultsStorageSettings();
        this.checkResultsTableFactory = new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());

        CheckResultsSnapshotFactory checkResultsSnapshotFactory = new CheckResultsSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.checkResultsTableFactory);

        this.sut = new CheckResultsDeleteServiceImpl(checkResultsSnapshotFactory,
                                                    parquetPartitionMetadataService);
    }

    private Table prepareSimplePartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table checkResultsTable = this.checkResultsTableFactory.createEmptyCheckResultsTable(tableName);

        Row row1 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);

        Row row2 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));

        Row row3 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));

        return checkResultsTable;
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(connectionName);
                setFullTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
        String connectionName = "connection";
        String tableName = "tab";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorsFragment_whenDataAlreadyEmpty_thenPassWithSuccess() {
        String connectionName = "connection";
        String tableName = "tab1";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("sch.nonexistent_table");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);
        Assertions.assertTrue(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition1AfterDelete.getData());
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id1"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id2"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id3"));
        Assertions.assertNotEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenSearchPatternOnSchemaTable_thenDeleteResultsForMatchingTables() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        String tableName2 = "tab2";
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);
        PhysicalTableName physicalTableName2 = new PhysicalTableName("sch", tableName2);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate1, "");
        Table table2 = prepareSimplePartitionTable(tableName2, startDate2, "");

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName2,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.checkResultsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("*ch.*tab1");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);
        Assertions.assertFalse(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }


    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate) {
        Table checkResultsTable = this.checkResultsTableFactory.createEmptyCheckResultsTable(tableName);

        Row row1 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), "id1");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row1.getRowNumber(), "check1");
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        checkResultsTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row1.getRowNumber(), "col1");
        checkResultsTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row1.getRowNumber(), "ds1");
        checkResultsTable.textColumn(CheckResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "s1");
        checkResultsTable.textColumn(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row1.getRowNumber(), "qd1");
        checkResultsTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row1.getRowNumber(), "tg1");

        Row row2 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), "id2");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        checkResultsTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row2.getRowNumber(), "col2");
        checkResultsTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row2.getRowNumber(), "ds1");
        checkResultsTable.textColumn(CheckResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row2.getRowNumber(), "s2");
        checkResultsTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row2.getRowNumber(), "tg1");

        Row row3 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), "id3");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        checkResultsTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row3.getRowNumber(), "col1");
        checkResultsTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row3.getRowNumber(), "ds2");
        checkResultsTable.textColumn(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row3.getRowNumber(), "qd2");

        Row row4 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), "id4");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row4.getRowNumber(), 1000);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row4.getRowNumber(), "check2");
        checkResultsTable.textColumn(CheckResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "s1");
        checkResultsTable.textColumn(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row4.getRowNumber(), "qd2");
        checkResultsTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row4.getRowNumber(), "tg1");

        Row row5 = checkResultsTable.appendRow();
        checkResultsTable.textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), "id5");
        checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row5.getRowNumber(), 10000);
        checkResultsTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row5.getRowNumber(), "check1");
        checkResultsTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row5.getRowNumber(), "ds2");
        checkResultsTable.textColumn(CheckResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "s1");

        return checkResultsTable;
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterByCheckCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterByCheckNameAndCheckType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckName("check1");
            setCheckType("type1");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterByColumnNameAndDataStreamAndSensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setColumnNames(new ArrayList<>(){{add("col2");}});
            setDataStreamName("ds1");
            setSensorName("s2");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterByQualityDimensionAndTimeGradient_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setQualityDimension("qd2");
            setTimeGradient("tg1");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterBySensorNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
            setSensorName("s1");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedCheckResultsFragment_whenFilterBySensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomain(),
                this.checkResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.checkResultsStorageSettings,
                userIdentity);

        CheckResultsFragmentFilter filter = new CheckResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setSensorName("s1");
        }};

        this.sut.deleteSelectedCheckResultsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.checkResultsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(CheckResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }
}
