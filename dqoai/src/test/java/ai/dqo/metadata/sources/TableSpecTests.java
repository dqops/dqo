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
import ai.dqo.checks.table.TableCheckCategoriesSpec;
import ai.dqo.checks.table.validity.TableValidityRowCountCheckSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableSpecTests extends BaseTest {
    private TableSpec sut;

    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = new TableSpec();
    }

    @Test
    void isDirty_whenDisableSet_thenIsDirtyIsTrue() {
		this.sut.setDisabled(true);
        Assertions.assertTrue(this.sut.isDisabled());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDisableVariableAsCurrentSet_thenIsDirtyIsTru() {
		this.sut.setDisabled(true);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setDisabled(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenLabelsSetSpecSet_thenIsDirtyIsTrue() {
        LabelSetSpec labelSetSpec = new LabelSetSpec();
        labelSetSpec.setDirty();
		this.sut.setLabels(labelSetSpec);
        Assertions.assertEquals(labelSetSpec, this.sut.getLabels());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameLabelSetSpecObjectAsCurrentSet_thenIsDirtyIsFalse() {
        LabelSetSpec labelSetSpec = new LabelSetSpec();
        labelSetSpec.setDirty();
		this.sut.setLabels(labelSetSpec);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setLabels(labelSetSpec);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenTableTargetSpecSet_thenIsDirtyIsTrue() {
        TableTargetSpec tableTargetSpec = new TableTargetSpec();
        tableTargetSpec.setTableName("other");
		this.sut.setTarget(tableTargetSpec);
        Assertions.assertEquals(tableTargetSpec, this.sut.getTarget());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameTableTargetSpecObjectAsCurrentSet_thenIsDirtyIsTru() {
        TableTargetSpec tableTargetSpec = new TableTargetSpec();
        tableTargetSpec.setTableName("other");
		this.sut.setTarget(tableTargetSpec);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setTarget(tableTargetSpec);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void mergeColumnsFrom_whenNewColumnsInSource_thenAddsNewColumns() {
        TableSpec sourceTableSpec = new TableSpec();
        ColumnSpec col1Spec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("string"));
        sourceTableSpec.getColumns().put("col1", col1Spec);
        ColumnSpec col2Spec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("int"));
        sourceTableSpec.getColumns().put("col2", col2Spec);

		this.sut.mergeColumnsFrom(sourceTableSpec);

        Assertions.assertEquals(2, this.sut.getColumns().size());
        Assertions.assertSame(col1Spec, this.sut.getColumns().get("col1"));
        Assertions.assertSame(col2Spec, this.sut.getColumns().get("col2"));
    }

    @Test
    void mergeColumnsFrom_whenExistingColumnsInSource_thenOnlyUpdatesTypeSnapshot() {
        TableSpec sourceTableSpec = new TableSpec();
        ColumnSpec col1Spec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("string"));
        sourceTableSpec.getColumns().put("col1", col1Spec);
        ColumnSpec col2Spec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("int"));
        sourceTableSpec.getColumns().put("col2", col2Spec);

		this.sut.getColumns().put("col1", new ColumnSpec(ColumnTypeSnapshotSpec.fromType("varchar(10)")));
		this.sut.getColumns().put("col2", new ColumnSpec(ColumnTypeSnapshotSpec.fromType("decimal(10,2)")));

		this.sut.mergeColumnsFrom(sourceTableSpec);

        Assertions.assertEquals(2, this.sut.getColumns().size());
        Assertions.assertNotNull(this.sut.getColumns().get("col1"));
        Assertions.assertNotNull(this.sut.getColumns().get("col2"));
        Assertions.assertNotSame(col1Spec, this.sut.getColumns().get("col1"));
        Assertions.assertNotSame(col2Spec, this.sut.getColumns().get("col2"));
        Assertions.assertSame(col1Spec.getTypeSnapshot(), this.sut.getColumns().get("col1").getTypeSnapshot());
        Assertions.assertSame(col2Spec.getTypeSnapshot(), this.sut.getColumns().get("col2").getTypeSnapshot());
    }

    @Test
    void isDirty_whenDefaultChecksSpecSet_thenIsDirtyIsTrue() {
        TableCheckCategoriesSpec defaultChecks = new TableCheckCategoriesSpec();
        defaultChecks.getValidity().setRowCount(new TableValidityRowCountCheckSpec());
		this.sut.setChecks(defaultChecks);
        Assertions.assertEquals(this.sut.getChecks(), defaultChecks);
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDefaultChecksSpecObjectAsCurrentSet_thenIsDirtyIsFalse() {
        TableCheckCategoriesSpec defaultChecks = new TableCheckCategoriesSpec();
        defaultChecks.getValidity().setRowCount(new TableValidityRowCountCheckSpec());
		this.sut.setChecks(defaultChecks);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setChecks(defaultChecks);
        Assertions.assertFalse(this.sut.isDirty());
    }
}
