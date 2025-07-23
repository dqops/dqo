/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
