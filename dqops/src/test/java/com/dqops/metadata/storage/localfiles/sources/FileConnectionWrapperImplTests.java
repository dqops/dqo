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
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableList;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileConnectionWrapperImplTests extends BaseTest {
    private FileConnectionWrapperImpl sut;
    private UserHomeContext userHomeContext;
    private FileConnectionListImpl connectionList;
    private FolderTreeNode connectionFolder;
    private YamlSerializer yamlSerializer;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.connectionList = (FileConnectionListImpl) userHomeContext.getUserHome().getConnections();
		this.connectionFolder = this.connectionList.getSourcesFolder().getOrAddDirectFolder("conn");
		this.yamlSerializer = YamlSerializerObjectMother.createNew();
		this.sut = new FileConnectionWrapperImpl(connectionFolder, yamlSerializer, false);
    }

    @Test
    void getTables_whenRetrieved_thenReturnsFileBasedTablesList() {
        TableList tables = this.sut.getTables();
        Assertions.assertInstanceOf(FileTableListImpl.class, tables);
    }

    @Test
    void flush_whenNew_thenSavesSpec() {
        ConnectionSpec spec = new ConnectionSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setDatabase("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();

		userHomeContext.flush();

        Assertions.assertFalse(this.sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer, false);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("dbname1", spec2.getPostgresql().getDatabase());
    }

    @Test
    void flush_whenModified_thenSavesSpec() {
        ConnectionSpec spec = new ConnectionSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setDatabase("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		this.sut.getSpec().getPostgresql().setDatabase("dbname2");
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer, false);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("dbname2", spec2.getPostgresql().getDatabase());
    }

    @Test
    void flush_whenExistingWasMarkedForDeletion_thenDeletesConnectionFromDisk() {
        ConnectionSpec spec = new ConnectionSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setDatabase("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		this.sut.markForDeletion();
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.DELETED, this.sut.getStatus());
        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper sut2 = homeContext2.getUserHome().getConnections().getByObjectName("conn", true);
        Assertions.assertNull(sut2);
    }

    @Test
    void getSpec_whenSpecFilePresentInFolder_thenReturnsSpec() {
        ConnectionSpec spec = new ConnectionSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setDatabase("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer, false);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
    }

    @Test
    void getSpec_whenCalledTwice_thenReturnsTheSameInstance() {
        ConnectionSpec spec = new ConnectionSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setDatabase("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer, false);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
        Assertions.assertSame(spec2, sut2.getSpec());
    }
}
