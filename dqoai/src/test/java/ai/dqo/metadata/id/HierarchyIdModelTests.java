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
package ai.dqo.metadata.id;

import ai.dqo.BaseTest;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.metadata.userhome.UserHomeObjectMother;
import ai.dqo.utils.serialization.JsonSerializer;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HierarchyIdModelTests extends BaseTest {
    private JsonSerializer jsonSerializer;
    private UserHomeImpl userHome;

    @BeforeEach
    void setUp() {
        this.jsonSerializer = JsonSerializerObjectMother.createNew();
        this.userHome = UserHomeObjectMother.createBareUserHome();
    }

    @Test
    void toHierarchyId_whenTableHierarchyIdDeserialized_thenReturnsValidHierarchyIdThatEqualsOriginalHierarchyId() {
        ConnectionWrapper connectionWrapper = this.userHome.getConnections().createAndAddNew("conn1");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
        TableSpec tableSpec = new TableSpec();
        tableWrapper.setSpec(tableSpec);

        HierarchyId originalHierarchyId = tableSpec.getHierarchyId();
        Assertions.assertNotNull(originalHierarchyId);
        HierarchyIdModel sut = originalHierarchyId.toHierarchyIdModel();

        String serialized = this.jsonSerializer.serialize(sut);
        HierarchyIdModel deserializedSut = this.jsonSerializer.deserialize(serialized, HierarchyIdModel.class);

        HierarchyId recreatedHierarchyId = deserializedSut.toHierarchyId();
        Assertions.assertEquals(originalHierarchyId, recreatedHierarchyId);
    }
}
