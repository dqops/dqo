/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
