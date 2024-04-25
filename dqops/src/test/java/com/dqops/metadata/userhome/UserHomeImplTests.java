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
package com.dqops.metadata.userhome;

import com.dqops.BaseTest;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.sources.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserHomeImplTests extends BaseTest {
    private UserHomeImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
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
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);

        ConnectionWrapper result = this.sut.findConnectionFor(check.getHierarchyId());
        Assertions.assertNotNull(result);
        Assertions.assertSame(connectionWrapper, result);
    }

    @Test
    void findTableFor_whenObjectInTableSpec_thenReturnsTableWrapper() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);

        TableWrapper result = this.sut.findTableFor(check.getHierarchyId());
        Assertions.assertNotNull(result);
        Assertions.assertSame(tableWrapper, result);
    }

    @Test
    void findColumnFor_whenObjectInTableSpecButNotInColumn_thenReturnsNull() {
        ConnectionWrapper connectionWrapper = this.sut.getConnections().createAndAddNew("src");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "table"));
        TableSpec tableSpec = tableWrapper.getSpec();
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);

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
