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
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableList;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.check.CheckFlatConfigurationFactory;
import com.dqops.services.check.CheckFlatConfigurationFactoryImpl;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.AllChecksModelFactoryImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;
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
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        this.dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        AllChecksModelFactory allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToUiCheckMappingService);
        CheckFlatConfigurationFactory checkFlatConfigurationFactory = new CheckFlatConfigurationFactoryImpl(allChecksModelFactory);

        this.sut = new TableServiceImpl(
                this.userHomeContextFactory,
                dqoQueueJobFactory,
                this.dqoJobQueue,
                allChecksModelFactory,
                checkFlatConfigurationFactory
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
        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteTable(connectionName, tableName, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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
        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteTable(connectionName, tableName, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteTables(connToTablesMap, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        this.sut.deleteTables(connToTablesMap, principal);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
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
