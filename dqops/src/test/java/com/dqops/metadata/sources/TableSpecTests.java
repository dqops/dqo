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
package com.dqops.metadata.sources;

import com.dqops.BaseTest;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.monitoring.TableMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.metadata.groupings.DataStreamLevelSpecObjectMother;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableSpecTests extends BaseTest {
    private TableSpec sut;

    @BeforeEach
    void setUp() {
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
        TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();
        profilingChecks.setVolume(new TableVolumeProfilingChecksSpec());
        profilingChecks.getVolume().setProfileRowCount(new TableRowCountCheckSpec());
		this.sut.setProfilingChecks(profilingChecks);
        Assertions.assertEquals(this.sut.getProfilingChecks(), profilingChecks);
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDefaultChecksSpecObjectAsCurrentSet_thenIsDirtyIsFalse() {
        TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();
        profilingChecks.setVolume(new TableVolumeProfilingChecksSpec());
        profilingChecks.getVolume().setProfileRowCount(new TableRowCountCheckSpec());
		this.sut.setProfilingChecks(profilingChecks);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setProfilingChecks(profilingChecks);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void hasAnyChecksConfigured_whenFreshObject_thenReturnsFalse() {
        Assertions.assertFalse(this.sut.hasAnyChecksConfigured());
    }

    @Test
    void hasAnyChecksConfigured_whenOneProfilingCheckConfigured_thenReturnsTrue() {
        TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();
        profilingChecks.setVolume(new TableVolumeProfilingChecksSpec());
        profilingChecks.getVolume().setProfileRowCount(new TableRowCountCheckSpec());
        this.sut.setProfilingChecks(profilingChecks);
        Assertions.assertTrue(this.sut.hasAnyChecksConfigured());
    }

    @Test
    void hasAnyChecksConfigured_whenOneDailyMonitoringCheckConfigured_thenReturnsTrue() {
        TableMonitoringChecksSpec monitoring = new TableMonitoringChecksSpec();
        TableDailyMonitoringCheckCategoriesSpec daily = new TableDailyMonitoringCheckCategoriesSpec();
        TableVolumeDailyMonitoringChecksSpec volume = new TableVolumeDailyMonitoringChecksSpec();
        volume.setDailyRowCount(new TableRowCountCheckSpec());
        daily.setVolume(volume);
        monitoring.setDaily(daily);
        this.sut.setMonitoringChecks(monitoring);
        Assertions.assertTrue(this.sut.hasAnyChecksConfigured());
    }

    @Test
    void hasAnyChecksConfigured_whenOneMonthlyMonitoringCheckConfigured_thenReturnsTrue() {
        TableMonitoringChecksSpec monitoring = new TableMonitoringChecksSpec();
        TableMonthlyMonitoringCheckCategoriesSpec daily = new TableMonthlyMonitoringCheckCategoriesSpec();
        TableVolumeMonthlyMonitoringChecksSpec volume = new TableVolumeMonthlyMonitoringChecksSpec();
        volume.setMonthlyRowCount(new TableRowCountCheckSpec());
        daily.setVolume(volume);
        monitoring.setMonthly(daily);
        this.sut.setMonitoringChecks(monitoring);
        Assertions.assertTrue(this.sut.hasAnyChecksConfigured());
    }

    @Test
    void serialize_whenHasAnyDataGroupingConfigurations_thenDataGroupingsSerialized() {
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        DataGroupingConfigurationSpec dataGroupingConfiguration = new DataGroupingConfigurationSpec();
        dataGroupingConfiguration.setLevel1(DataStreamLevelSpecObjectMother.createTag("tag1"));
        this.sut.setDefaultDataGroupingConfiguration(dataGroupingConfiguration);

        String yaml = yamlSerializer.serialize(this.sut);

        TableSpec deserialized = yamlSerializer.deserialize(yaml, TableSpec.class);
        Assertions.assertNotNull(deserialized.getGroupings());
        Assertions.assertEquals(1, deserialized.getGroupings().size());
        Assertions.assertEquals("tag1", deserialized.getGroupings().get("default").getLevel1().getTag());
    }
}
