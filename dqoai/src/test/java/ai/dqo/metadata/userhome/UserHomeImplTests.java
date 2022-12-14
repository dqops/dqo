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
package ai.dqo.metadata.userhome;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.adhoc.TableAdHocStandardChecksSpec;
import ai.dqo.checks.table.checkspecs.standard.TableMinRowCountCheckSpec;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserHomeImplTests extends BaseTest {
    private UserHomeImpl sut;

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
		this.sut = new UserHomeImpl();
    }

    @Test
    void getSources_whenNewObject_thenIsNotNull() {
        Assertions.assertNotNull(this.sut.getConnections());
    }

    @Test
    void flush_whenCalledAndChangesPresent_thenFlushesChanges() {
        ConnectionWrapper src = this.sut.getConnections().createAndAddNew("src");
        Assertions.assertEquals(InstanceStatus.ADDED, src.getStatus());
		this.sut.flush();
        Assertions.assertEquals(InstanceStatus.UNCHANGED, src.getStatus());
    }

    @Test
    void findConnectionFor_whenObjectInConnection_thenReturnsConnection() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        TableAdHocStandardChecksSpec standard = new TableAdHocStandardChecksSpec();
        tableSpec.getChecks().setStandard(standard);
        TableMinRowCountCheckSpec check = new TableMinRowCountCheckSpec();
        standard.setMinRowCount(check);

        ConnectionWrapper result = this.sut.findConnectionFor(check.getHierarchyId());
        Assertions.assertNotNull(result);
        Assertions.assertSame(connectionWrapper, result);
    }

    @Test
    void findTableFor_whenObjectInTableSpec_thenReturnsTableWrapper() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        TableAdHocStandardChecksSpec standard = new TableAdHocStandardChecksSpec();
        tableSpec.getChecks().setStandard(standard);
        TableMinRowCountCheckSpec check = new TableMinRowCountCheckSpec();
        standard.setMinRowCount(check);

        TableWrapper result = this.sut.findTableFor(check.getHierarchyId());
        Assertions.assertNotNull(result);
        Assertions.assertSame(tableWrapper, result);
    }

    @Test
    void findColumnFor_whenObjectInTableSpecButNotInColumn_thenReturnsNull() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        TableAdHocStandardChecksSpec standard = new TableAdHocStandardChecksSpec();
        tableSpec.getChecks().setStandard(standard);
        TableMinRowCountCheckSpec check = new TableMinRowCountCheckSpec();
        standard.setMinRowCount(check);

        ColumnSpec result = this.sut.findColumnFor(check.getHierarchyId());
        Assertions.assertNull(result);
    }

    @Test
    void findColumnFor_whenObjectInColumnSpec_thenReturnsParentColumnSpec() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = new ColumnSpec();
        ColumnTypeSnapshotSpec typeSnapshot = new ColumnTypeSnapshotSpec();
        columnSpec.setTypeSnapshot(typeSnapshot);
        columns.put("col1", columnSpec);

        ColumnSpec result = this.sut.findColumnFor(typeSnapshot.getHierarchyId());
        Assertions.assertNotNull(result);
        Assertions.assertSame(columnSpec, result);
    }
}
