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

package com.dqops.metadata.defaultchecks.table;

import com.dqops.BaseTest;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthAboveMaxLengthCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsMonthlyMonitoringChecksSpec;
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
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableDefaultChecksPatternSpecTests extends BaseTest {
    private TableDefaultChecksPatternSpec sut;
    private TableSpec targetTable;
    private ColumnSpec targetColumn;
    private ProviderDialectSettings dialectSettings;

    @BeforeEach
    void setUp() {
        this.sut = new TableDefaultChecksPatternSpec();
        this.targetTable = new TableSpec();
        this.targetColumn = new ColumnSpec();
        this.targetColumn.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.targetTable.getColumns().put("col1", this.targetColumn);
        this.dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.postgresql);
    }

    @Test
    public void applyOnTable_whenNoDefaultChecks_thenDoesNothing() {
        this.sut.applyOnTable(targetTable, this.dialectSettings);
    }

    @Test
    public void applyOnTable_whenProfilingChecksSet_thenConfiguresThemOnTable() {
        TableProfilingCheckCategoriesSpec tableDefaults = new TableProfilingCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeProfilingChecksSpec volumeDefaults = new TableVolumeProfilingChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setProfileRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getProfilingChecks().getVolume().getProfileRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenDailyMonitoringChecksSet_thenConfiguresThemOnTable() {
        TableDailyMonitoringCheckCategoriesSpec tableDefaults = new TableDailyMonitoringCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeDailyMonitoringChecksSpec volumeDefaults = new TableVolumeDailyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getDaily().getVolume().getDailyRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenMonthlyMonitoringChecksSet_thenConfiguresThemOnTable() {
        TableMonthlyMonitoringCheckCategoriesSpec tableDefaults = new TableMonthlyMonitoringCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeMonthlyMonitoringChecksSpec volumeDefaults = new TableVolumeMonthlyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setMonthlyRowCount(new TableRowCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getMonthly().getVolume().getMonthlyRowCount());
    }
}
