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
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoJobQueueObjectMother;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobFactoryImpl;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableList;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class TableServiceImplTests extends BaseTest {
    private TableServiceImpl sut;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoJobQueue dqoJobQueue;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        this.dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();

        this.sut = new TableServiceImpl(
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

        userHomeContext.flush();
        return userHome;
    }

    @Test
    void getTable_whenConnectionAndTableExist_getsRequestedTable() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        TableWrapper tableWrapper = this.sut.getTable(userHome, connectionName, tableName);

        Assertions.assertNotNull(tableWrapper);
    }

    @Test
    void getTable_whenTableDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab3");
        TableWrapper tableWrapper = this.sut.getTable(userHome, connectionName, tableName);

        Assertions.assertNull(tableWrapper);
    }

    @Test
    void getTable_whenConnectionDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "con";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        TableWrapper tableWrapper = this.sut.getTable(userHome, connectionName, tableName);

        Assertions.assertNull(tableWrapper);
    }

    @Test
    void deleteTable_whenConnectionAndTableExist_deletesTable() {
        createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        this.sut.deleteTable(connectionName, tableName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableList tableList = connectionWrapper.getTables();
        TableWrapper deletedTable = tableList.getByObjectName(tableName, true);
        TableWrapper otherTable = tableList.getByObjectName(
                new PhysicalTableName("sch", "tab2"), true);
        Assertions.assertTrue(deletedTable == null || deletedTable.getStatus() == InstanceStatus.DELETED);
        Assertions.assertTrue(otherTable != null && otherTable.getStatus() == InstanceStatus.UNCHANGED);
    }

    @Test
    void deleteTable_whenTableDoesntExist_doNothing() {
        createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab3");
        this.sut.deleteTable(connectionName, tableName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        List<TableWrapper> tableList = this.filterDeletedTables(connectionWrapper.getTables());
        Assertions.assertEquals(2, tableList.size());
    }

    @Test
    void deleteTables_whenTablesExist_deletesTables() {
        createHierarchyTree();

        String connectionName = "conn";
        List<PhysicalTableName> tableNames = new ArrayList<>();
        tableNames.add(new PhysicalTableName("sch", "tab1"));
        tableNames.add(new PhysicalTableName("sch", "tab2"));
        Map<String, Iterable<PhysicalTableName>> connToTablesMap = new HashMap<>();
        connToTablesMap.put(connectionName, tableNames);

        this.sut.deleteTables(connToTablesMap);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        List<TableWrapper> tableList = this.filterDeletedTables(connectionWrapper.getTables());
        Assertions.assertEquals(0, tableList.size());
    }

    @Test
    void deleteTables_whenSomeTableDoesntExist_deletesTablesThatExist() {
        createHierarchyTree();

        String connectionName = "conn";
        List<PhysicalTableName> tableNames = new ArrayList<>();
        tableNames.add(new PhysicalTableName("sch", "tab1"));
        tableNames.add(new PhysicalTableName("sch", "tab3"));
        Map<String, Iterable<PhysicalTableName>> connToTablesMap = new HashMap<>();
        connToTablesMap.put(connectionName, tableNames);

        this.sut.deleteTables(connToTablesMap);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        List<TableWrapper> tableList = this.filterDeletedTables(connectionWrapper.getTables());
        Assertions.assertEquals(1, tableList.size());
        TableWrapper remainingTable = tableList.get(0);
        Assertions.assertEquals(new PhysicalTableName("sch", "tab2"), remainingTable.getPhysicalTableName());
    }

    private List<TableWrapper> filterDeletedTables(TableList tableList) {
        return tableList.toList().stream()
                .filter(tableWrapper -> tableWrapper.getStatus() != InstanceStatus.DELETED)
                .collect(Collectors.toList());
    }
}
