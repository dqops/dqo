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

package com.dqops.metadata.defaultchecks.column;

import com.dqops.BaseTest;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthAboveMaxLengthCheckSpec;
import com.dqops.checks.column.checkspecs.text.ColumnTextMaxLengthCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.text.ColumnTextDailyPartitionedChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnTextProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnDefaultChecksPatternSpecTests extends BaseTest {
    private ColumnDefaultChecksPatternSpec sut;
    private TableSpec targetTable;
    private ColumnSpec targetColumn;
    private ProviderDialectSettings dialectSettings;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnDefaultChecksPatternSpec();
        this.targetTable = new TableSpec();
        this.targetColumn = new ColumnSpec();
        this.targetColumn.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.targetTable.getColumns().put("col1", this.targetColumn);
        this.dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.postgresql);
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumn_thenConfiguresThemOnColumn() {
        ColumnProfilingCheckCategoriesSpec columnDefaults = new ColumnProfilingCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnNullsProfilingChecksSpec nullsDefaults = new ColumnNullsProfilingChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setProfileNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getNulls().getProfileNullsCount());
    }

    @Test
    public void applyOnColumn_whenDailyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        ColumnDailyMonitoringCheckCategoriesSpec columnDefaults = new ColumnDailyMonitoringCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnNullsDailyMonitoringChecksSpec nullsDefaults = new ColumnNullsDailyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getDaily().getNulls().getDailyNullsCount());
    }

    @Test
    public void applyOnColumn_whenMonthlyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        ColumnMonthlyMonitoringCheckCategoriesSpec columnDefaults = new ColumnMonthlyMonitoringCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnNullsMonthlyMonitoringChecksSpec nullsDefaults = new ColumnNullsMonthlyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setMonthlyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getMonthly().getNulls().getMonthlyNullsCount());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextAndTypeIsValid_thenConfiguresThemOnColumn() {
        targetColumn.getTypeSnapshot().setColumnType("varchar");

        ColumnProfilingCheckCategoriesSpec columnDefaults = new ColumnProfilingCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getText().getProfileTextLengthAboveMaxLength());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextButTypeIsNotMatchingNumeric_thenCheckNotConfigured() {
        targetColumn.getTypeSnapshot().setColumnType("numeric");

        ColumnProfilingCheckCategoriesSpec columnDefaults = new ColumnProfilingCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNull(targetColumn.getProfilingChecks().getText());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextButTypeIsMissing_thenCheckNotConfigured() {
        targetColumn.getTypeSnapshot().setColumnType(null);

        ColumnProfilingCheckCategoriesSpec columnDefaults = new ColumnProfilingCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNull(targetColumn.getProfilingChecks().getText());
    }

    @Test
    public void applyOnColumn_whenDailyPartitionChecksSetOnColumnAndTableHasPartitionByColumnConfigured_thenConfiguresThemOnColumn() {
        targetColumn.getTypeSnapshot().setColumnType("varchar");
        targetTable.getTimestampColumns().setPartitionByColumn("date");

        ColumnDailyPartitionedCheckCategoriesSpec columnDefaults = new ColumnDailyPartitionedCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnTextDailyPartitionedChecksSpec textDefault = new ColumnTextDailyPartitionedChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setDailyPartitionTextMaxLength(new ColumnTextMaxLengthCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getPartitionedChecks().getDaily().getText().getDailyPartitionTextMaxLength());
    }

    @Test
    public void applyOnColumn_whenDailyPartitionChecksSetOnColumnAndTableNotConfiguredForPartitionedChecks_thenCheckNotConfigured() {
        targetColumn.getTypeSnapshot().setColumnType("varchar");

        ColumnDailyPartitionedCheckCategoriesSpec columnDefaults = new ColumnDailyPartitionedCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnTextDailyPartitionedChecksSpec textDefault = new ColumnTextDailyPartitionedChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setDailyPartitionTextMaxLength(new ColumnTextMaxLengthCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings);

        Assertions.assertNull(targetColumn.getPartitionedChecks());
    }
}
