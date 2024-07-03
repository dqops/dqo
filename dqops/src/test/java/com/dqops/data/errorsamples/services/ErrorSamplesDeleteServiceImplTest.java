package com.dqops.data.errorsamples.services;

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
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactory;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactoryImpl;
import com.dqops.data.errorsamples.models.ErrorsSamplesFragmentFilter;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshot;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactory;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactoryImpl;
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

@SpringBootTest
public class ErrorSamplesDeleteServiceImplTest extends BaseTest {

    private ErrorSamplesDeleteServiceImpl sut;
    private ParquetPartitionStorageService parquetPartitionStorageService;
    private FileStorageSettings errorSamplesStorageSettings;
    private ErrorSamplesTableFactory errorSamplesTableFactory;

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

        this.errorSamplesStorageSettings = ErrorSamplesSnapshot.createErrorSamplesStorageSettings();
        this.errorSamplesTableFactory = new ErrorSamplesTableFactoryImpl();

        ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory = new ErrorSamplesSnapshotFactoryImpl(
                this.parquetPartitionStorageService,
                this.errorSamplesTableFactory
        );

        this.sut = new ErrorSamplesDeleteServiceImpl(errorSamplesSnapshotFactory,
                parquetPartitionMetadataService);

    }

    private Table prepareSimplePartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table errorSamplesTable = this.errorSamplesTableFactory.createEmptyErrorSamplesTable(tableName);

        Row row1 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row1.getRowNumber(), 1);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row1.getRowNumber(), startDate);

        Row row2 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row2.getRowNumber(), 10);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));

        Row row3 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row3.getRowNumber(), 100);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));

        return errorSamplesTable;
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterCapturesCertainRows_thenDeleteTheseRows() {
        String connectionName = "connection";
        String tableName = "tab";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        Table table1 = prepareSimplePartitionTable(tableName, startDate, "");
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate.plusDays(1).toLocalDate());
            setDateEnd(startDate.plusDays(1).toLocalDate());
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);

        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }


    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterCapturesAllRows_thenDeleteWholeFile() {
        String connectionName = "connection";
        String tableName = "tab";
        LocalDate month = LocalDate.of(2023, 1, 1);
        LocalDateTime startDate = month.atStartOfDay().plusDays(14);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        Table table = prepareSimplePartitionTable(tableName, startDate, tableName);
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);

        ParquetPartitionId partitionId1 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);

        Assertions.assertNull(partitionAfterDelete.getData());
        Assertions.assertEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterCapturesAllRowsOfOnePartition_thenDeleteOnlyThisPartition() {
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
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorSamplesStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.withDayOfMonth(1).toLocalDate());
            setDateEnd(startDate1.withDayOfMonth(1).toLocalDate().plusMonths(1).minusDays(1));
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterCapturesSpanOfTwoPartitions_thenDeleteCapturedRows() {
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
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorSamplesStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(startDate1.toLocalDate());
            setDateEnd(startDate2.toLocalDate());
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertFalse(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenDataAlreadyEmpty_thenPassWithSuccess() {
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
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorSamplesStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("sch.nonexistent_table");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);
        Assertions.assertTrue(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition1AfterDelete.getData());
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id1"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id2"));
        Assertions.assertTrue(partition1AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix1 + "id3"));
        Assertions.assertNotEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorsColumnNames.ID_COLUMN_NAME).contains(id_prefix2 + "id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenSearchPatternOnSchemaTable_thenDeleteResultsForMatchingTables() {
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
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName1,
                month1);
        ParquetPartitionId partitionId2 = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName2,
                month2);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId1),
                new TableDataChanges(table1),
                this.errorSamplesStorageSettings,
                userIdentity);
        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId2),
                new TableDataChanges(table2),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName("*ch.*tab1");
            }});
        }};

        DeleteStoredDataResult result = this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);
        Assertions.assertFalse(result.getPartitionResults().isEmpty());

        LoadedMonthlyPartition partition1AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId1, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNull(partition1AfterDelete.getData());
        Assertions.assertEquals(0L, partition1AfterDelete.getLastModified());

        LoadedMonthlyPartition partition2AfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId2, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partition2AfterDelete.getData());
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partition2AfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertNotEquals(0L, partition2AfterDelete.getLastModified());
    }


    private Table prepareComplexPartitionTable(String tableName, LocalDateTime startDate, String id_prefix) {
        Table errorSamplesTable = this.errorSamplesTableFactory.createEmptyErrorSamplesTable(tableName);

        Row row1 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row1.getRowNumber(), id_prefix + "id1");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row1.getRowNumber(), 1);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row1.getRowNumber(), startDate);
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row1.getRowNumber(), "cat1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME).set(row1.getRowNumber(), "check1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME).set(row1.getRowNumber(), "type1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME).set(row1.getRowNumber(), "col1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row1.getRowNumber(), "ds1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME).set(row1.getRowNumber(), "s1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row1.getRowNumber(), "qd1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row1.getRowNumber(), "tg1");

        Row row2 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row2.getRowNumber(), id_prefix + "id2");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row2.getRowNumber(), 10);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row2.getRowNumber(), startDate.plusDays(1));
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row2.getRowNumber(), "cat2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME).set(row2.getRowNumber(), "type1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME).set(row2.getRowNumber(), "col2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row2.getRowNumber(), "ds1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME).set(row2.getRowNumber(), "s2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row2.getRowNumber(), "tg1");

        Row row3 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row3.getRowNumber(), id_prefix + "id3");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row3.getRowNumber(), 100);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row3.getRowNumber(), startDate.plusDays(2));
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME).set(row3.getRowNumber(), "type2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME).set(row3.getRowNumber(), "col1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row3.getRowNumber(), "ds2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row3.getRowNumber(), "qd2");

        Row row4 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row4.getRowNumber(), id_prefix + "id4");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row4.getRowNumber(), 1000);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row4.getRowNumber(), startDate.plusDays(3));
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME).set(row4.getRowNumber(), "check2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME).set(row4.getRowNumber(), "s1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME).set(row4.getRowNumber(), "qd2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME).set(row4.getRowNumber(), "tg1");

        Row row5 = errorSamplesTable.appendRow();
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).set(row5.getRowNumber(), id_prefix + "id5");
        errorSamplesTable.longColumn(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME).set(row5.getRowNumber(), 10000);
        errorSamplesTable.dateTimeColumn(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME).set(row5.getRowNumber(), startDate.plusDays(4));
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME).set(row5.getRowNumber(), "cat1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME).set(row5.getRowNumber(), "check1");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME).set(row5.getRowNumber(), "ds2");
        errorSamplesTable.textColumn(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME).set(row5.getRowNumber(), "s1");

        return errorSamplesTable;
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterByCheckCategory_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckCategory("cat1");
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterByCheckNameAndCheckType_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusMonths(1).minusDays(1));
            setCheckName("check1");
            setCheckType("type1");
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterBySensorNameAndTimePeriod_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setDateStart(month);
            setDateEnd(month.plusDays(3));
            setSensorName("s1");
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

    @Test
    void deleteSelectedErrorSamplesFragment_whenFilterBySensorName_thenDeleteCapturedRows() {
        String connectionName = "connection";
        String tableName = "tab";
        PhysicalTableName physicalTableName = new PhysicalTableName("sch", tableName);
        UserDomainIdentity userIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        LocalDate month = LocalDate.of(2023, 1, 1);
        Table table = prepareComplexPartitionTable(tableName, month.atStartOfDay(), "");

        ParquetPartitionId partitionId = new ParquetPartitionId(
                userIdentity.getDataDomainFolder(),
                this.errorSamplesStorageSettings.getTableType(),
                connectionName,
                physicalTableName,
                month);

        this.parquetPartitionStorageService.savePartition(
                new LoadedMonthlyPartition(partitionId),
                new TableDataChanges(table),
                this.errorSamplesStorageSettings,
                userIdentity);

        ErrorsSamplesFragmentFilter filter = new ErrorsSamplesFragmentFilter(){{
            setTableSearchFilters(new TableSearchFilters(){{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
            }});
            setSensorName("s1");
        }};

        this.sut.deleteSelectedErrorSamplesFragment(filter, userIdentity);

        LoadedMonthlyPartition partitionAfterDelete = this.parquetPartitionStorageService.loadPartition(
                partitionId, this.errorSamplesStorageSettings, null, userIdentity);
        Assertions.assertNotNull(partitionAfterDelete.getData());
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id1"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id2"));
        Assertions.assertTrue(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id3"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id4"));
        Assertions.assertFalse(partitionAfterDelete.getData().textColumn(ErrorSamplesColumnNames.ID_COLUMN_NAME).contains("id5"));
        Assertions.assertNotEquals(0L, partitionAfterDelete.getLastModified());
    }

}