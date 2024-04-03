/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.services.metadata;

import com.dqops.BaseTest;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoJobQueueObjectMother;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.DqoQueueJobFactoryImpl;
import com.dqops.core.principal.*;
import com.dqops.data.storage.DummyParquetPartitionStorageService;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class ColumnServiceImplTests extends BaseTest {
    private ColumnServiceImpl sut;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoJobQueue dqoJobQueue;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        this.dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();

        this.sut = new ColumnServiceImpl(
                this.userHomeContextFactory,
                dqoQueueJobFactory,
                this.dqoJobQueue
        );
    }

    private UserHome createHierarchyTree() {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNotNull(columnSpec);
    }

    @Test
    void getColumn_whenColumnDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col2");

        Assertions.assertNull(columnSpec);
    }

    @Test
    void getColumn_whenTableDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab3");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNull(columnSpec);
    }

    @Test
    void getColumn_whenConnectionDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();

        String connectionName = "con";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab1");
        ColumnSpec columnSpec = this.sut.getColumn(userHome, connectionName, tableName, "col1");

        Assertions.assertNull(columnSpec);
    }

    @Test
    void deleteColumn_whenConnectionTableAndColumnExist_deletesColumn() {
        createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab2");
        String columnName = "col1";
        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteColumn(connectionName, tableName, columnName, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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
    }

    @Test
    void deleteColumn_whenColumnDoesntExist_doNothing() {
        createHierarchyTree();

        String connectionName = "conn";
        PhysicalTableName tableName = new PhysicalTableName("sch", "tab2");
        String columnName = "col4";
        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteColumn(connectionName, tableName, columnName, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(tableName, true);
        ColumnSpecMap columnSpecMap = tableWrapper.getSpec().getColumns();

        Assertions.assertEquals(3, columnSpecMap.size());
    }

    @Test
    void deleteColumns_whenColumnsExist_deletesColumns() {
        createHierarchyTree();

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

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteColumns(connToTableToColumnsMap, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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
    }

    @Test
    void deleteColumns_whenSomeColumnDoesntExist_deletesColumnsThatExist() {
        createHierarchyTree();

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

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteColumns(connToTableToColumnsMap, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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
    }
}
