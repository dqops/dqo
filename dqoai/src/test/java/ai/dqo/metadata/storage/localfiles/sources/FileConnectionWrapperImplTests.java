/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.storage.localfiles.sources;

import ai.dqo.BaseTest;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableList;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.utils.serialization.YamlSerializer;
import ai.dqo.utils.serialization.YamlSerializerObjectMother;
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

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.connectionList = (FileConnectionListImpl) userHomeContext.getUserHome().getConnections();
		this.connectionFolder = this.connectionList.getSourcesFolder().getOrAddDirectFolder("conn");
		this.yamlSerializer = YamlSerializerObjectMother.createNew();
		this.sut = new FileConnectionWrapperImpl(connectionFolder, yamlSerializer);
    }

    @Test
    void getTables_whenRetrieved_thenReturnsFileBasedTablesList() {
        TableList tables = this.sut.getTables();
        Assertions.assertInstanceOf(FileTableListImpl.class, tables);
    }

    @Test
    void flush_whenNew_thenSavesSpec() {
        ConnectionSpec spec = new ConnectionSpec();
        spec.setDatabaseName("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();

		userHomeContext.flush();

        Assertions.assertFalse(this.sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("dbname1", spec2.getDatabaseName());
    }

    @Test
    void flush_whenModified_thenSavesSpec() {
        ConnectionSpec spec = new ConnectionSpec();
        spec.setDatabaseName("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		this.sut.getSpec().setDatabaseName("dbname2");
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("dbname2", spec2.getDatabaseName());
    }

    @Test
    void flush_whenExistingWasMarkedForDeletion_thenDeletesConnectionFromDisk() {
        ConnectionSpec spec = new ConnectionSpec();
        spec.setDatabaseName("dbname1");
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
        spec.setDatabaseName("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
    }

    @Test
    void getSpec_whenCalledTwice_thenReturnsTheSameInstance() {
        ConnectionSpec spec = new ConnectionSpec();
        spec.setDatabaseName("dbname1");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileConnectionWrapperImpl sut2 = new FileConnectionWrapperImpl(connectionFolder, this.yamlSerializer);
        ConnectionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
        Assertions.assertSame(spec2, sut2.getSpec());
    }
}
