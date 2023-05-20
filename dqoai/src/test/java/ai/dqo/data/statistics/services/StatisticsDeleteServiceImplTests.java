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
package ai.dqo.data.statistics.services;

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
import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.factory.StatisticsResultsTableFactory;
import ai.dqo.data.statistics.factory.StatisticsResultsTableFactoryImpl;
import ai.dqo.data.statistics.models.StatisticsResultsFragmentFilter;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactoryImpl;
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
import java.util.ArrayList;

@SpringBootTest
public class StatisticsDeleteServiceImplTests extends BaseTest {
    private StatisticsDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings profilingResultsStorageSettings;
    private StatisticsResultsTableFactory statisticsResultsTableFactory;

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

        ParquetPartitionMetadataService parquetPartitionMetadataService = new ParquetPartitionMetadataServiceImpl(
                newLockManager, localUserHomeFileStorageService);

        this.parquetPartitionStorageService = new ParquetPartitionStorageServiceImpl(
                parquetPartitionMetadataService,
                localUserHomeProviderStub,
                newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(),
                localUserHomeFileStorageService,
                synchronizationStatusTracker);

        this.profilingResultsStorageSettings = StatisticsSnapshot.createStatisticsStorageSettings();
        this.statisticsResultsTableFactory = new StatisticsResultsTableFactoryImpl();

        StatisticsSnapshotFactory statisticsSnapshotFactory = new StatisticsSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.statisticsResultsTableFactory
        );

        this.sut = new StatisticsDeleteServiceImpl(statisticsSnapshotFactory,
                                                   parquetPartitionMetadataService);
    }

    private Table prepareSimplePartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table profilingResultsTable = this.statisticsResultsTableFactory.createEmptyStatisticsTable(tableName);

        Row row1 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row1.getRowNumber(), startDate);

        Row row2 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));

        Row row3 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));

        return profilingResultsTable;
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.profilingResultsStorageSettings, null);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String tableName1 = "tab1";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(tableName1, startDate, "");
        PhysicalTableName physicalTableName1 = new PhysicalTableName("sch", tableName1);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName1.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.profilingResultsStorageSettings, null);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
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
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.profilingResultsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.profilingResultsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
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
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.profilingResultsStorageSettings);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.profilingResultsStorageSettings, null);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }


    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate) {
        Table profilingResultsTable = this.statisticsResultsTableFactory.createEmptyStatisticsTable(tableName);

        Row row1 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), "id1");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "profiler1");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row1.getRowNumber(), "col1");
        profilingResultsTable.textColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row1.getRowNumber(), "ds1");
        profilingResultsTable.textColumn(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "s1");

        Row row2 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), "id2");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row2.getRowNumber(), "col2");
        profilingResultsTable.textColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row2.getRowNumber(), "ds1");
        profilingResultsTable.textColumn(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row2.getRowNumber(), "s2");

        Row row3 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), "id3");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME).set(row3.getRowNumber(), "col1");
        profilingResultsTable.textColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row3.getRowNumber(), "ds2");

        Row row4 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), "id4");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "profiler2");
        profilingResultsTable.textColumn(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "s1");

        Row row5 = profilingResultsTable.appendRow();
        profilingResultsTable.textColumn(StatisticsColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), "id5");
        profilingResultsTable.dateTimeColumn(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        profilingResultsTable.textColumn(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "profiler1");
        profilingResultsTable.textColumn(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).set(row5.getRowNumber(), "ds2");
        profilingResultsTable.textColumn(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "s1");

        return profilingResultsTable;
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterByProfilerCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCollectorCategory("cat1");
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterByProfilerNameAndProfilerType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCollectorName("profiler1");
            setCollectorTarget("type1");
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterByColumnNameAndDataStreamAndSensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setColumnNames(new ArrayList<>(){{add("col2");}});
            setDataStreamName("ds1");
            setSensorName("s2");
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterBySensorNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
            setSensorName("s1");
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedProfilingResultsFragment_whenFilterBySensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab1";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay());

        ParquetPartitionId partitionId = new ParquetPartitionId(
                this.profilingResultsStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.profilingResultsStorageSettings);

        StatisticsResultsFragmentFilter filter = new StatisticsResultsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
            }});
            setSensorName("s1");
        }};

        this.sut.deleteSelectedStatisticsResultsFragment(filter);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.profilingResultsStorageSettings, null);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(StatisticsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }
}
