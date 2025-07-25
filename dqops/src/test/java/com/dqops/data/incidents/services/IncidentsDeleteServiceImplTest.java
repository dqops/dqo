/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.incidents.services;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.factory.IncidentsTableFactory;
import com.dqops.data.incidents.factory.IncidentsTableFactoryImpl;
import com.dqops.data.incidents.models.IncidentsFragmentFilter;
import com.dqops.data.incidents.snapshot.IncidentsSnapshot;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactory;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactoryImpl;
import com.dqops.data.local.LocalDqoUserHomePathProvider;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.data.models.DeleteStoredDataResult;
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
import java.time.ZoneOffset;

@SpringBootTest
public class IncidentsDeleteServiceImplTest extends BaseTest {

    private IncidentsDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings incidentsStorageSettings;
    private IncidentsTableFactory incidentsTableFactory;

    @BeforeEach
    protected void setUp() throws Throwable {
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        DqoUserConfigurationProperties dqoUserConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
        LocalDqoUserHomePathProvider localUserHomeProviderStub = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(dqoUserConfigurationProperties);
        UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        LocalFileSystemCache fileSystemCache = LocalFileSystemCacheObjectMother.createNewCache();

        HomeLocationFindService homeLocationFindService = new HomeLocationFindServiceImpl(dqoUserConfigurationProperties, dqoConfigurationProperties);
        SynchronizationStatusTrackerStub synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        LocalUserHomeFileStorageService localUserHomeFileStorageService = new LocalUserHomeFileStorageServiceImpl(
                homeLocationFindService, newLockManager, synchronizationStatusTracker, LocalFileSystemCacheObjectMother.createNewCache());

        ParquetPartitionMetadataService parquetPartitionMetadataService = new ParquetPartitionMetadataServiceImpl(newLockManager, localUserHomeFileStorageService);

        this.parquetPartitionStorageService = new ParquetPartitionStorageServiceImpl(
                parquetPartitionMetadataService,
                localUserHomeProviderStub,
                newLockManager,
                HadoopConfigurationProviderObjectMother.getDefault(),
                localUserHomeFileStorageService,
                synchronizationStatusTracker,
                fileSystemCache);

        this.incidentsStorageSettings = IncidentsSnapshot.createIncidentsStorageSettings();
        this.incidentsTableFactory = new IncidentsTableFactoryImpl();

        IncidentsSnapshotFactory incidentsSnapshotFactory = new IncidentsSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.incidentsTableFactory
        );

        this.sut = new IncidentsDeleteServiceImpl(incidentsSnapshotFactory, parquetPartitionMetadataService);
    }

    private Table prepareSimplePartitionTable(String schemaName, String tableName, LocalDateTime startDate, String id_prefix) {
        Table incidentsTable = this.incidentsTableFactory.createEmptyIncidentsTable(tableName);

        Row row1 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row1.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row1.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row1.getRowNumber(), 1);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row1.getRowNumber(), startDate.toInstant(ZoneOffset.UTC));

        Row row2 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row2.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row2.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row2.getRowNumber(), 10);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1).toInstant(ZoneOffset.UTC));

        Row row3 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row3.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row3.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row3.getRowNumber(), 100);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2).toInstant(ZoneOffset.UTC));

        return incidentsTable;
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName = "tab";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        Table table1 = prepareSimplePartitionTable(schemaName, tableName, startDate, "");
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }


    @Test
    void deleteSelectedIncidentsFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName = "tab";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        Table table = prepareSimplePartitionTable(schemaName, tableName, startDate, tableName);
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName = "tab";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(schemaName, tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(schemaName, tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.incidentsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName = "tab";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(schemaName, tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(schemaName, tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.incidentsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenDataAlreadyEmpty_thenPassWithSuccess() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName = "tab1";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(schemaName, tableName, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(schemaName, tableName, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.incidentsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("sch.nonexistent_table");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);
//        Assertions.assertTrue(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition1AfterDelete.getData());
        Assertions.assertTrue(partition1AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id1"));
        Assertions.assertTrue(partition1AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id2"));
        Assertions.assertTrue(partition1AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id3"));
        Assertions.assertNotEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenSearchPatternOnSchemaTable_thenDeleteResultsForMatchingTables() {
        String connectionName = "connection";
        String schemaName = "sch";
        String tableName1 = "tab1";
        String tableName2 = "tab2";
        String id_prefix1 = "1";
        String id_prefix2 = "2";
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month1 = LocalDate.of(2023, 1, 1);
        LocalDate month2 = LocalDate.of(2023, 2, 1);
        LocalDateTime startDate1 = month1.atStartOfDay().plusDays(14);
        LocalDateTime startDate2 = month2.atStartOfDay().plusDays(14);

        Table table1 = prepareSimplePartitionTable(schemaName, tableName1, startDate1, id_prefix1);
        Table table2 = prepareSimplePartitionTable(schemaName, tableName2, startDate2, id_prefix2);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.incidentsStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("*ch.*tab1");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);
        Assertions.assertFalse(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table incidentsTable = this.incidentsTableFactory.createEmptyIncidentsTable(tableName);

        String schemaName = "sch";

        Row row1 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row1.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row1.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row1.getRowNumber(), 1);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row1.getRowNumber(), startDate.toInstant(ZoneOffset.UTC));
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME).set(row1.getRowNumber(), "check1");
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        incidentsTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row1.getRowNumber(), "ds1");
        incidentsTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row1.getRowNumber(), "qd1");

        Row row2 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row2.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row2.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row2.getRowNumber(), 10);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1).toInstant(ZoneOffset.UTC));
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        incidentsTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row2.getRowNumber(), "ds1");

        Row row3 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row3.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row3.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row3.getRowNumber(), 100);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2).toInstant(ZoneOffset.UTC));
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        incidentsTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row3.getRowNumber(), "ds2");
        incidentsTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row3.getRowNumber(), "qd2");

        Row row4 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row4.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row4.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), id_prefix + "id4");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row4.getRowNumber(), 1000);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3).toInstant(ZoneOffset.UTC));
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME).set(row4.getRowNumber(), "check2");
        incidentsTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row4.getRowNumber(), "qd2");

        Row row5 = incidentsTable.appendRow();
        incidentsTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME).set(row5.getRowNumber(), schemaName);
        incidentsTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME).set(row5.getRowNumber(), tableName);
        incidentsTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), id_prefix + "id5");
        incidentsTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME).set(row5.getRowNumber(), 10000);
        incidentsTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4).toInstant(ZoneOffset.UTC));
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        incidentsTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME).set(row5.getRowNumber(), "check1");
        incidentsTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row5.getRowNumber(), "ds2");

        return incidentsTable;
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterByCheckCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterByCheckNameAndCheckType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckName("check1");
            setCheckType("type1");
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterBySchemaNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedIncidentsFragment_whenFilterByTableName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.incidentsStorageSettings.getTableType(),
                connectionName,
                null,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.incidentsStorageSettings,
                userIdentity);

        IncidentsFragmentFilter filter = new IncidentsFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedIncidentsFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.incidentsStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().stringColumn(IncidentsColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }


}