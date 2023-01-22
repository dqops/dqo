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
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
public class FileConnectionListImplTests extends BaseTest {
    private FileConnectionListImpl sut;
    private UserHomeContext homeContext;

    @BeforeEach
    void setUp() {
		homeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sut = (FileConnectionListImpl) homeContext.getUserHome().getConnections();
    }

    @Test
    void createAndAddNew_whenNewSourceAddedAndFlushed_thenIsSaved() {
        ConnectionWrapper wrapper = this.sut.createAndAddNew("src1");
        ConnectionSpec model = wrapper.getSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        model.setPostgresql(postgresql);
        postgresql.setUser("user");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper wrapper2 = sources2.getByObjectName("src1", true);
        Assertions.assertEquals("user", wrapper2.getSpec().getPostgresql().getUser());
    }

    @Test
    void createAndAddNew_whenNewSourceAddedInSubFolderAndFlushed_thenIsSaved() {
        ConnectionWrapper wrapper = this.sut.createAndAddNew("area/src1");
        ConnectionSpec model = wrapper.getSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        model.setPostgresql(postgresql);
        postgresql.setUser("user");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper wrapper2 = sources2.getByObjectName("area/src1", true);
        Assertions.assertEquals("user", wrapper2.getSpec().getPostgresql().getUser());
    }

    @Test
    void flush_whenExistingSourceLoadedModifiedAndFlushed_thenIsSaved() {
        ConnectionWrapper wrapper = this.sut.createAndAddNew("src1");
        ConnectionSpec model = wrapper.getSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        model.setPostgresql(postgresql);
        postgresql.setUser("user");;
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources2 = homeContext2.getUserHome().getConnections();
        ConnectionWrapper wrapper2 = sources2.getByObjectName("src1", true);
        wrapper2.getSpec().getPostgresql().setUser("newuser");
        homeContext2.flush();

        UserHomeContext homeContext3 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sources3 = homeContext3.getUserHome().getConnections();
        ConnectionWrapper wrapper3 = sources3.getByObjectName("src1", true);
        Assertions.assertEquals("newuser", wrapper3.getSpec().getPostgresql().getUser());
    }

    @Test
    void iterator_whenConnectionAdded_thenReturnsConnection() {
        ConnectionWrapper wrapper = this.sut.createAndAddNew("src3");
        ConnectionSpec spec = wrapper.getSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        spec.setPostgresql(postgresql);
        postgresql.setUser("user");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionList sut2 = homeContext2.getUserHome().getConnections();
        Iterator<ConnectionWrapper> iterator = sut2.iterator();
        Assertions.assertTrue(iterator.hasNext());
        ConnectionWrapper wrapperLoaded = iterator.next();
        Assertions.assertNotNull(wrapperLoaded);
        Assertions.assertEquals("user", wrapperLoaded.getSpec().getPostgresql().getUser());
        Assertions.assertFalse(iterator.hasNext());
    }
}
