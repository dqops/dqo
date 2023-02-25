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
package ai.dqo.data.ruleresults.services;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.configuration.DqoUserConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import ai.dqo.core.synchronization.status.SynchronizationStatusTrackerStub;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import ai.dqo.data.ruleresults.factory.RuleResultsColumnNames;
import ai.dqo.data.ruleresults.factory.RuleResultsTableFactory;
import ai.dqo.data.ruleresults.factory.RuleResultsTableFactoryImpl;
import ai.dqo.data.ruleresults.models.RuleResultsFragmentFilter;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactory;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactoryImpl;
import ai.dqo.data.storage.*;
import ai.dqo.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import ai.dqo.metadata.search.TableSearchFilters;
import ai.dqo.metadata.sources.PhysicalTableName;
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

@SpringBootTest
public class RuleResultsDeleteServiceImplTests extends BaseTest {
    private RuleResultsDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings ruleResultsStorageSettings;
    private RuleResultsTableFactory ruleResultsTableFactory;

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

        HomeLocationFindService homeLocationFindService = new HomeLocationFindServiceImpl(dqoUserConfigurationProperties, dqoConfigurationProperties);
        SynchronizationStatusTrackerStub synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        LocalUserHomeFileStorageService localUserHomeFileStorageService = new LocalUserHomeFileStorageServiceImpl(
                homeLocationFindService, newLockManager, synchronizationStatusTracker);

        this.parquetPartitionStorageService = new ParquetPartitionStorageServiceImpl(localUserHomeProviderStub, newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(), localUserHomeFileStorageService, synchronizationStatusTracker);

        this.ruleResultsStorageSettings = RuleResultsSnapshot.createRuleResultsStorageSettings();
        this.ruleResultsTableFactory = new RuleResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());

        RuleResultsSnapshotFactory ruleResultsSnapshotFactory = new RuleResultsSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.ruleResultsTableFactory);

        this.sut = new RuleResultsDeleteServiceImpl(ruleResultsSnapshotFactory);
    }

    private Table prepareSimplePartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table ruleResultsTable = this.ruleResultsTableFactory.createEmptyRuleResultsTable(tableName);

        Row row1 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);

        Row row2 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));

        Row row3 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));

        return ruleResultsTable;
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.ruleResultsStorageSettings, null);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.ruleResultsStorageSettings, null);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
        String connectionName = "connection";
        String tableName = "tab";
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
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.ruleResultsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.ruleResultsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
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
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.ruleResultsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.ruleResultsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }


    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate) {
        Table ruleResultsTable = this.ruleResultsTableFactory.createEmptyRuleResultsTable(tableName);

        Row row1 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), "id1");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row1.getRowNumber(), 1);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row1.getRowNumber(), "check1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row1.getRowNumber(), "col1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row1.getRowNumber(), "ds1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "s1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row1.getRowNumber(), "qd1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row1.getRowNumber(), "tg1");

        Row row2 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), "id2");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row2.getRowNumber(), 10);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row2.getRowNumber(), "col2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row2.getRowNumber(), "ds1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row2.getRowNumber(), "s2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row2.getRowNumber(), "tg1");

        Row row3 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), "id3");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row3.getRowNumber(), 100);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row3.getRowNumber(), "col1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row3.getRowNumber(), "ds2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row3.getRowNumber(), "qd2");

        Row row4 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), "id4");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row4.getRowNumber(), 1000);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row4.getRowNumber(), "check2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "s1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row4.getRowNumber(), "qd2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row4.getRowNumber(), "tg1");

        Row row5 = ruleResultsTable.appendRow();
        ruleResultsTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), "id5");
        ruleResultsTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME).set(row5.getRowNumber(), 10000);
        ruleResultsTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.CHECK_NAME_COLUMN_NAME).set(row5.getRowNumber(), "check1");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row5.getRowNumber(), "ds2");
        ruleResultsTable.stringColumn(RuleResultsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "s1");

        return ruleResultsTable;
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterByCheckCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterByCheckNameAndCheckType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckName("check1");
            setCheckType("type1");
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterByColumnNameAndDataStreamAndSensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setColumnName("col2");
            setDataStreamName("ds1");
            setSensorName("s2");
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterByQualityDimensionAndTimeGradient_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setQualityDimension("qd2");
            setTimeGradient("tg1");
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedRuleResultsFragment_whenFilterBySensorNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.ruleResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.ruleResultsStorageSettings);

        RuleResultsFragmentFilter filter = new RuleResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
            setSensorName("s1");
        }};

        this.sut.deleteSelectedRuleResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.ruleResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }
}
