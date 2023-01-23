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
package ai.dqo.metadata.sources;

import ai.dqo.BaseTest;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableListImplTests extends BaseTest {
    private TableListImpl sut;
    private UserHomeContext inMemoryFileHomeContext;
    private ConnectionWrapper connection;

    @BeforeEach
    void setUp() {
		inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
		connection = inMemoryFileHomeContext.getUserHome().getConnections().createAndAddNew("src");
		this.sut = (TableListImpl) connection.getTables();
    }

    @Test
    void createAndAddNew_whenPhysicalTableNameGiven_thenCopiesToTarget() {
        PhysicalTableName physicalTableName = new PhysicalTableName("s2", "tab2");
        TableWrapper tableWrapper = this.sut.createAndAddNew(physicalTableName);
        Assertions.assertEquals(physicalTableName.getSchemaName(), tableWrapper.getSpec().getTarget().getSchemaName());
        Assertions.assertEquals(physicalTableName.getTableName(), tableWrapper.getSpec().getTarget().getTableName());
    }
}
