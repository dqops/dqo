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
package com.dqops.metadata.storage.localfiles.sources;

import com.dqops.BaseTest;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
public class FileTableListImplTests extends BaseTest {
    private FileTableListImpl sut;
    private UserHomeContext homeContext;
    private ConnectionWrapper connection;
    private PhysicalTableName physicalTableName;

    @BeforeEach
    void setUp() {
		homeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        ConnectionList connections = homeContext.getUserHome().getConnections();
		connection = connections.createAndAddNew("newConnection");
		homeContext.flush();
		this.sut = (FileTableListImpl) connection.getTables();
		physicalTableName = new PhysicalTableName("s1", "tab1");
    }


    @Test
    void createAndAddNew_whenNewTableAddedAndFlushed_thenIsSaved() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec model = wrapper.getSpec();
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        TableWrapper wrapper2 = sut2.getByObjectName(physicalTableName, true);
        Assertions.assertNotNull(wrapper2);
        Assertions.assertEquals(physicalTableName.getTableName(), wrapper2.getSpec().getPhysicalTableName().getTableName());
    }

    @Test
    void createAndAddNew_whenNewTableAddedUsingPhysicalTableNameAndFlushed_thenIsSaved() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec model = wrapper.getSpec();
		homeContext.flush();
        Assertions.assertEquals(physicalTableName.getSchemaName(), model.getPhysicalTableName().getSchemaName());
        Assertions.assertEquals(physicalTableName.getTableName(), model.getPhysicalTableName().getTableName());

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        TableWrapper wrapper2 = sut2.getByObjectName(physicalTableName, true);
        Assertions.assertNotNull(wrapper2);
        Assertions.assertEquals(physicalTableName.getTableName(), wrapper2.getSpec().getPhysicalTableName().getTableName());
    }

    @Test
    void createAndAddNew_whenNewTableAddedUWithEmptyListOfLabels_thenDeserializedCopyHasNullLabels() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec model = wrapper.getSpec();
        model.setLabels(new LabelSetSpec());
		homeContext.flush();
        Assertions.assertEquals(physicalTableName.getSchemaName(), model.getPhysicalTableName().getSchemaName());
        Assertions.assertEquals(physicalTableName.getTableName(), model.getPhysicalTableName().getTableName());

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        TableWrapper wrapper2 = sut2.getByObjectName(physicalTableName, true);
        Assertions.assertNotNull(wrapper2);
        Assertions.assertNull(wrapper2.getSpec().getLabels());
    }

    @Test
    void createAndAddNew_whenNewTableAddedUWithEmptyListOfComments_thenDeserializedCopyHasNullComments() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec model = wrapper.getSpec();
        model.setComments(new CommentsListSpec());
		homeContext.flush();
        Assertions.assertEquals(physicalTableName.getSchemaName(), model.getPhysicalTableName().getSchemaName());
        Assertions.assertEquals(physicalTableName.getTableName(), model.getPhysicalTableName().getTableName());

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        TableWrapper wrapper2 = sut2.getByObjectName(physicalTableName, true);
        Assertions.assertNotNull(wrapper2);
        Assertions.assertNull(wrapper2.getSpec().getComments());
    }

    @Test
    void flush_whenExistingTableLoadedModifiedAndFlushed_thenIsSaved() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec model = wrapper.getSpec();
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        TableWrapper wrapper2 = sut2.getByObjectName(physicalTableName, true);
        wrapper2.getSpec().setLabels(new LabelSetSpec());
        wrapper2.getSpec().getLabels().add("label");
        homeContext2.flush();

        UserHomeContext homeContext3 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources3 = homeContext3.getUserHome().getConnections();
        ConnectionWrapper conn3 = sources3.getByObjectName("newConnection", true);
        TableList sut3 = conn3.getTables();
        TableWrapper wrapper3 = sut3.getByObjectName(physicalTableName, true);
        Assertions.assertTrue(wrapper3.getSpec().getLabels().contains("label"));
    }

    @Test
    void iterator_whenTableAdded_thenReturnsConnection() {
        TableWrapper wrapper = this.sut.createAndAddNew(this.physicalTableName);
        TableSpec spec = wrapper.getSpec();
        spec.setLabels(new LabelSetSpec());
        spec.getLabels().add("label2");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper conn2 = sources2.getByObjectName("newConnection", true);
        TableList sut2 = conn2.getTables();
        Iterator<TableWrapper> iterator = sut2.iterator();
        Assertions.assertTrue(iterator.hasNext());
        TableWrapper wrapperLoaded = iterator.next();
        Assertions.assertNotNull(wrapperLoaded);
        Assertions.assertTrue(wrapperLoaded.getSpec().getLabels().contains("label2"));
        Assertions.assertFalse(iterator.hasNext());
    }
}
