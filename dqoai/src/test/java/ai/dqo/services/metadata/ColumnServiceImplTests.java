/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.metadata;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringServiceImpl;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.core.synchronization.filesystems.local.LocalFileSystemSynchronizationOperations;
import ai.dqo.core.synchronization.filesystems.local.LocalFileSystemSynchronizationOperationsImpl;
import ai.dqo.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactory;
import ai.dqo.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactoryImpl;
import ai.dqo.core.synchronization.status.FileSynchronizationChangeDetectionService;
import ai.dqo.core.synchronization.status.FileSynchronizationChangeDetectionServiceImpl;
import ai.dqo.core.synchronization.status.SynchronizationStatusTracker;
import ai.dqo.core.synchronization.status.SynchronizationStatusTrackerStub;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class ColumnServiceImplTests extends BaseTest {
    private ColumnServiceImpl sut;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoJobQueue dqoJobQueue;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());

        DqoQueueConfigurationProperties dqoQueueConfigurationProperties = new DqoQueueConfigurationProperties();
        DqoJobConcurrencyLimiter dqoJobConcurrencyLimiter = new DqoJobConcurrencyLimiterImpl();
        DqoJobIdGenerator dqoJobIdGenerator = new DqoJobIdGeneratorImpl();
        DqoJobQueueMonitoringService dqoJobQueueMonitoringService = new DqoJobQueueMonitoringServiceImpl(dqoJobIdGenerator, dqoQueueConfigurationProperties);
        LocalFileSystemSynchronizationOperations localFileSystemSynchronizationOperations = new LocalFileSystemSynchronizationOperationsImpl();
        LocalDqoUserHomePathProvider localDqoUserHomePathProvider = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(
                DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration());
        LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory = new LocalSynchronizationFileSystemFactoryImpl(
                localFileSystemSynchronizationOperations,
                localDqoUserHomePathProvider);
        SynchronizationStatusTracker synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        UserHomeLockManager userHomeLockManager = UserHomeLockManagerObjectMother.getDefaultGlobalLockManager();
        FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService = new FileSynchronizationChangeDetectionServiceImpl(
                userHomeContextFactory,
                localSynchronizationFileSystemFactory,
                userHomeLockManager,
                synchronizationStatusTracker);
        this.dqoJobQueue = new DqoJobQueueImpl(
                dqoQueueConfigurationProperties,
                dqoJobConcurrencyLimiter,
                dqoJobIdGenerator,
                dqoJobQueueMonitoringService,
                fileSynchronizationChangeDetectionService);

        this.sut = new ColumnServiceImpl(
                this.userHomeContextFactory,
                dqoQueueJobFactory,
                this.dqoJobQueue
        );
    }

    private UserHome createHierarchyTree() {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        TableWrapper table1 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab1"));
        TableWrapper table2 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab2"));

        table1.getSpec().getColumns().put("col1", new ColumnSpec());
        table2.getSpec().getColumns().put("col1", new ColumnSpec());
        table2.getSpec().getColumns().put("col2", new ColumnSpec());
        table2.getSpec().getColumns().put("col3", new ColumnSpec());

        userHomeContext.flush();
        return userHome;
    }

    @Test
    void getColumn_whenConnectionTableAndColumnExist_getsRequestedColumn() {
        UserHome userHome = createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNotNull(columnSpec);
        this.dqoJobQueue.stop();
    }

    @Test
    void getColumn_whenColumnDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col2");

        Assertions.assertNull(columnSpec);
        this.dqoJobQueue.stop();
    }

    @Test
    void getColumn_whenTableDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab3");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNull(columnSpec);
        this.dqoJobQueue.stop();
    }

    @Test
    void getColumn_whenConnectionDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "con";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNull(columnSpec);
        this.dqoJobQueue.stop();
    }

    @Test
    void deleteColumn_whenConnectionTableAndColumnExist_deletesColumn() {
        createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab2");
        String columnName = "col1";
        this.sut.deleteColumn(connectionName, tableName, columnName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(tableName, true);
        ColumnSpecMap columnSpecMap = tableWrapper.getSpec().getColumns();

        ColumnSpec deletedColumn = columnSpecMap.get(columnName);
        ColumnSpec otherColumn1 = columnSpecMap.get("col2");
        ColumnSpec otherColumn2 = columnSpecMap.get("col3");
        Assertions.assertNull(deletedColumn);
        Assertions.assertNotNull(otherColumn1);
        Assertions.assertNotNull(otherColumn2);
        this.dqoJobQueue.stop();
    }

    @Test
    void deleteColumn_whenColumnDoesntExist_doNothing() {
        createHierarchyTree();
        this.dqoJobQueue.start();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab2");
        String columnName = "col4";
        this.sut.deleteColumn(connectionName, tableName, columnName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(tableName, true);
        ColumnSpecMap columnSpecMap = tableWrapper.getSpec().getColumns();

        Assertions.assertEquals(3, columnSpecMap.size());
        this.dqoJobQueue.stop();
    }

    @Test
    void deleteColumns_whenColumnsExist_deletesColumns() {
        createHierarchyTree();
        this.dqoJobQueue.start();

        List<String> table1Columns = new ArrayList<>();
        table1Columns.add("col1");
        List<String> table2Columns = new ArrayList<>();
        table2Columns.add("col1");
        table2Columns.add("col3");

        Map<PhysicalTableName, Iterable<String>> tableToColumnsMap = new HashMap<>();
        PhysicalTableName table1Name = new PhysicalTableName("sch", "tab1");
        tableToColumnsMap.put(table1Name, table1Columns);
        PhysicalTableName table2Name = new PhysicalTableName("sch", "tab2");
        tableToColumnsMap.put(table2Name, table2Columns);

        String connectionName = "conn";
        Map<String, Map<PhysicalTableName, Iterable<String>>> connToTableToColumnsMap = new HashMap<>();
        connToTableToColumnsMap.put(connectionName, tableToColumnsMap);

        this.sut.deleteColumns(connToTableToColumnsMap);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableList tableList = connectionWrapper.getTables();
        for (TableWrapper tableWrapper: tableList) {
            Assertions.assertEquals(InstanceStatus.UNCHANGED, tableWrapper.getStatus());
        }

        TableWrapper table1PostDelete = tableList.getByObjectName(table1Name, true);
        Assertions.assertEquals(0, table1PostDelete.getSpec().getColumns().size());

        TableWrapper table2PostDelete = tableList.getByObjectName(table2Name, true);
        Assertions.assertEquals(1, table2PostDelete.getSpec().getColumns().size());
        Assertions.assertEquals("col2", table2PostDelete.getSpec().getColumns().keySet().stream().findAny().get());
        this.dqoJobQueue.stop();
    }

    @Test
    void deleteColumns_whenSomeColumnDoesntExist_deletesColumnsThatExist() {
        createHierarchyTree();
        this.dqoJobQueue.start();

        List<String> table1Columns = new ArrayList<>();
        table1Columns.add("col1");
        List<String> table2Columns = new ArrayList<>();
        table2Columns.add("col1");
        table2Columns.add("col4");

        Map<PhysicalTableName, Iterable<String>> tableToColumnsMap = new HashMap<>();
        PhysicalTableName table1Name = new PhysicalTableName("sch", "tab1");
        tableToColumnsMap.put(table1Name, table1Columns);
        PhysicalTableName table2Name = new PhysicalTableName("sch", "tab2");
        tableToColumnsMap.put(table2Name, table2Columns);

        String connectionName = "conn";
        Map<String, Map<PhysicalTableName, Iterable<String>>> connToTableToColumnsMap = new HashMap<>();
        connToTableToColumnsMap.put(connectionName, tableToColumnsMap);

        this.sut.deleteColumns(connToTableToColumnsMap);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableList tableList = connectionWrapper.getTables();
        for (TableWrapper tableWrapper: tableList) {
            Assertions.assertEquals(InstanceStatus.UNCHANGED, tableWrapper.getStatus());
        }

        TableWrapper table1PostDelete = tableList.getByObjectName(table1Name, true);
        Assertions.assertEquals(0, table1PostDelete.getSpec().getColumns().size());

        TableWrapper table2PostDelete = tableList.getByObjectName(table2Name, true);
        Assertions.assertEquals(2, table2PostDelete.getSpec().getColumns().size());

        Set<String> expectedTable2Columns = new HashSet<>();
        expectedTable2Columns.add("col2");
        expectedTable2Columns.add("col3");
        Assertions.assertIterableEquals(expectedTable2Columns, table2PostDelete.getSpec().getColumns().keySet());
        this.dqoJobQueue.stop();
    }
}
