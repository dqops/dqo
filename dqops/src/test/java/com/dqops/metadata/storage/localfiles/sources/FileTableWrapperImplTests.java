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
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTableWrapperImplTests extends BaseTest {
    private FileTableWrapperImpl sut;
    private UserHomeContext userHomeContext;
    private FileConnectionListImpl connectionList;
    private ConnectionWrapperImpl connection;
    private PhysicalTableName physicalTableName;
    private TableList tables;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.connectionList = (FileConnectionListImpl) userHomeContext.getUserHome().getConnections();
		connection = (ConnectionWrapperImpl) this.connectionList.createAndAddNew("conn");
		connection.flush();
		physicalTableName = new PhysicalTableName("s1", "tab1");
		tables = connection.getTables();
		sut = (FileTableWrapperImpl) tables.createAndAddNew(physicalTableName);
    }

    @Test
    void flush_whenNew_thenSavesSpec() {
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertFalse(this.sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper conn2 = homeContext2.getUserHome().getConnections().getByObjectName("conn", true);
        TableList tables2 = conn2.getTables();
        TableWrapper sut2 = tables2.getByObjectName(physicalTableName, true);
        Assertions.assertEquals(physicalTableName.getTableName(), sut2.getSpec().getPhysicalTableName().getTableName());
    }

    @Test
    void flush_whenModified_thenSavesSpec() {
		this.sut.flush();
		userHomeContext.flush();

		this.sut.getSpec().setLabels(new LabelSetSpec());
		this.sut.getSpec().getLabels().add("lbl");
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper conn2 = homeContext2.getUserHome().getConnections().getByObjectName("conn", true);
        TableList tables2 = conn2.getTables();
        TableWrapper sut2 = tables2.getByObjectName(physicalTableName, true);
        TableSpec spec2 = sut2.getSpec();
        Assertions.assertTrue(spec2.getLabels().contains("lbl"));
    }

    @Test
    void flush_whenExistingWasMarkedForDeletion_thenDeletesConnectionFromDisk() {
		this.sut.flush();
		userHomeContext.flush();

		this.sut.markForDeletion();
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.DELETED, this.sut.getStatus());

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper conn2 = homeContext2.getUserHome().getConnections().getByObjectName("conn", true);
        TableList tables2 = conn2.getTables();
        TableWrapper sut2 = tables2.getByObjectName(physicalTableName, true);
        Assertions.assertNull(sut2);
    }

    @Test
    void getSpec_whenSpecFilePresentInFolder_thenReturnsSpec() {
		this.sut.flush();
		userHomeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper conn2 = homeContext2.getUserHome().getConnections().getByObjectName("conn", true);
        TableList tables2 = conn2.getTables();
        TableWrapper sut2 = tables2.getByObjectName(physicalTableName, true);
        TableSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
        Assertions.assertSame(spec2, sut2.getSpec());
    }
}
