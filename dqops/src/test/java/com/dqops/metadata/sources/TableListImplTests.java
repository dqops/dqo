/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.BaseTest;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
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
        Assertions.assertEquals(physicalTableName.getSchemaName(), tableWrapper.getSpec().getPhysicalTableName().getSchemaName());
        Assertions.assertEquals(physicalTableName.getTableName(), tableWrapper.getSpec().getPhysicalTableName().getTableName());
    }
}
