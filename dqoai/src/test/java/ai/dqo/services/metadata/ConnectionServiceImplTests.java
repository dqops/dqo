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
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobFactoryImpl;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.jobs.DqoJobQueueObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ConnectionServiceImplTests extends BaseTest {
    private ConnectionServiceImpl sut;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoJobQueue dqoJobQueue;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        this.dqoJobQueue = DqoJobQueueObjectMother.getDefault();

        this.sut = new ConnectionServiceImpl(
                this.userHomeContextFactory,
                dqoQueueJobFactory,
                this.dqoJobQueue
        );
    }

    private UserHome createHierarchyTree() {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper1 = userHome.getConnections().createAndAddNew("conn1");
        ConnectionWrapper connectionWrapper2 = userHome.getConnections().createAndAddNew("conn2");
        userHomeContext.flush();
        return userHome;
    }

    @Test
    void getConnection_whenConnectionExists_getsRequestedConnection() {
        UserHome userHome = createHierarchyTree();
        
        String connectionName = "conn1";
        ConnectionWrapper connectionWrapper = this.sut.getConnection(userHome, connectionName);

        Assertions.assertNotNull(connectionWrapper);
    }

    @Test
    void getConnection_whenConnectionDoesntExist_getsNull() {
        UserHome userHome = createHierarchyTree();
        
        String connectionName = "conn3";
        ConnectionWrapper connectionWrapper = this.sut.getConnection(userHome, connectionName);

        Assertions.assertNull(connectionWrapper);
    }

    @Test
    void deleteConnection_whenConnectionExists_deletesConnection() {
        createHierarchyTree();
        
        String connectionName = "conn1";
        this.sut.deleteConnection(connectionName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connectionList = userHome.getConnections();
        ConnectionWrapper deletedConnection = connectionList.getByObjectName(connectionName, true);
        ConnectionWrapper otherConnection = connectionList.getByObjectName("conn2", true);
        Assertions.assertTrue(deletedConnection == null || deletedConnection.getStatus() == InstanceStatus.DELETED);
        Assertions.assertTrue(otherConnection != null && otherConnection.getStatus() == InstanceStatus.UNCHANGED);
    }

    @Test
    void deleteConnection_whenConnectionDoesntExist_doNothing() {
        createHierarchyTree();
        
        String connectionName = "conn3";
        this.sut.deleteConnection(connectionName);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<ConnectionWrapper> connectionList = this.filterDeletedConnections(userHome.getConnections());
        Assertions.assertEquals(2, connectionList.size());
    }

    @Test
    void deleteConnections_whenConnectionsExist_deletesConnections() {
        createHierarchyTree();
        
        List<String> connectionNames = new ArrayList<>();
        connectionNames.add("conn1");
        connectionNames.add("conn2");

        this.sut.deleteConnections(connectionNames);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<ConnectionWrapper> connectionList = this.filterDeletedConnections(userHome.getConnections());
        Assertions.assertEquals(0, connectionList.size());
    }

    @Test
    void deleteConnections_whenSomeConnectionDoesntExist_deletesConnectionsThatExist() {
        createHierarchyTree();
        
        List<String> connectionNames = new ArrayList<>();
        connectionNames.add("conn1");
        connectionNames.add("conn3");

        this.sut.deleteConnections(connectionNames);

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<ConnectionWrapper> connectionList = this.filterDeletedConnections(userHome.getConnections());
        Assertions.assertEquals(1, connectionList.size());
        ConnectionWrapper remainingConnection = connectionList.get(0);
        Assertions.assertEquals("conn2", remainingConnection.getName());
    }

    private List<ConnectionWrapper> filterDeletedConnections(ConnectionList connectionList) {
        return connectionList.toList().stream()
                .filter(connectionWrapper -> connectionWrapper.getStatus() != InstanceStatus.DELETED)
                .collect(Collectors.toList());
    }
}
