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
package com.dqops.metadata.id;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHomeImpl;
import com.dqops.metadata.userhome.UserHomeObjectMother;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
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
