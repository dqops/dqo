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
package com.dqops.services.check.matching;

import com.dqops.BaseTest;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.metadata.userhome.UserHomeObjectMother;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for SimilarCheckMatchingServiceImpl.
 */
@SpringBootTest
public class SimilarCheckMatchingServiceImplTests extends BaseTest {
    private SimilarCheckMatchingServiceImpl sut;
    private UserHome userHome;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;

    @BeforeEach
    void setUp() {
        this.sut = new SimilarCheckMatchingServiceImpl(SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl()));
        this.userHome = UserHomeObjectMother.createBareUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab"));
        this.tableSpec = new TableSpec();
        tableWrapper.setSpec(this.tableSpec);
        this.columnSpec = new ColumnSpec();
        this.tableSpec.getColumns().put("col1", this.columnSpec);
    }

    @Test
    void findSimilarTableChecks_whenEmptyTable_thenCreatesSimilarChecks() {
        SimilarChecksContainer similarChecks = this.sut.findSimilarTableChecks(tableSpec);
        Assertions.assertNotNull(similarChecks);
        Assertions.assertTrue(similarChecks.getSimilarCheckGroups().size() > 3);
    }

    @Test
    void findSimilarColumnChecks_whenEmptyColumn_thenCreatesSimilarChecks() {
        SimilarChecksContainer similarChecks = this.sut.findSimilarColumnChecks(tableSpec, columnSpec);
        Assertions.assertNotNull(similarChecks);
        Assertions.assertTrue(similarChecks.getSimilarCheckGroups().size() > 10);
    }
}
