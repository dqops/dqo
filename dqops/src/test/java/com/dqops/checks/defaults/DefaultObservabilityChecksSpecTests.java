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

package com.dqops.checks.defaults;

import com.dqops.BaseTest;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthAboveMaxLengthCheckSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnTextProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeMonthlyMonitoringChecksSpec;
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
public class DefaultObservabilityChecksSpecTests extends BaseTest {
    private DefaultObservabilityChecksSpec sut;
    private TableSpec targetTable;
    private ColumnSpec targetColumn;
    private ProviderDialectSettings dialectSettings;

    @BeforeEach
    void setUp() {
        this.sut = new DefaultObservabilityChecksSpec();
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
        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingTableObservabilityCheckSettingsSpec tableDefaults = new DefaultProfilingTableObservabilityCheckSettingsSpec();
        profilingDefaults.setTable(tableDefaults);
        TableVolumeProfilingChecksSpec volumeDefaults = new TableVolumeProfilingChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setProfileRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getProfilingChecks().getVolume().getProfileRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenDailyMonitoringChecksSet_thenConfiguresThemOnTable() {
        DefaultDailyMonitoringObservabilityCheckSettingsSpec dailyDefaults = new DefaultDailyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringDaily(dailyDefaults);
        DefaultDailyMonitoringTableObservabilityCheckSettingsSpec tableDefaults = new DefaultDailyMonitoringTableObservabilityCheckSettingsSpec();
        dailyDefaults.setTable(tableDefaults);
        TableVolumeDailyMonitoringChecksSpec volumeDefaults = new TableVolumeDailyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getDaily().getVolume().getDailyRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenMonthlyMonitoringChecksSet_thenConfiguresThemOnTable() {
        DefaultMonthlyMonitoringObservabilityCheckSettingsSpec dailyDefaults = new DefaultMonthlyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringMonthly(dailyDefaults);
        DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec tableDefaults = new DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec();
        dailyDefaults.setTable(tableDefaults);
        TableVolumeMonthlyMonitoringChecksSpec volumeDefaults = new TableVolumeMonthlyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setMonthlyRowCount(new TableRowCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getMonthly().getVolume().getMonthlyRowCount());
    }

    @Test
    public void applyOnTable_whenProfilingChecksSetOnColumn_thenConfiguresThemOnTable() {
        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultProfilingColumnObservabilityCheckSettingsSpec();
        profilingDefaults.setColumn(columnDefaults);
        ColumnNullsProfilingChecksSpec nullsDefaults = new ColumnNullsProfilingChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setProfileNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getNulls().getProfileNullsCount());
    }

    @Test
    public void applyOnTable_whenDailyMonitoringChecksSetOnColumn_thenConfiguresThemOnTable() {
        DefaultDailyMonitoringObservabilityCheckSettingsSpec monitoringDailyDefaults = new DefaultDailyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringDaily(monitoringDailyDefaults);
        DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec();
        monitoringDailyDefaults.setColumn(columnDefaults);
        ColumnNullsDailyMonitoringChecksSpec nullsDefaults = new ColumnNullsDailyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getDaily().getNulls().getDailyNullsCount());
    }

    @Test
    public void applyOnTable_whenMonthlyMonitoringChecksSetOnColumn_thenConfiguresThemOnTable() {
        DefaultMonthlyMonitoringObservabilityCheckSettingsSpec monitoringDailyDefaults = new DefaultMonthlyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringMonthly(monitoringDailyDefaults);
        DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec();
        monitoringDailyDefaults.setColumn(columnDefaults);
        ColumnNullsMonthlyMonitoringChecksSpec nullsDefaults = new ColumnNullsMonthlyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setMonthlyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getMonthly().getNulls().getMonthlyNullsCount());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumn_thenConfiguresThemOnColumn() {
        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultProfilingColumnObservabilityCheckSettingsSpec();
        profilingDefaults.setColumn(columnDefaults);
        ColumnNullsProfilingChecksSpec nullsDefaults = new ColumnNullsProfilingChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setProfileNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getNulls().getProfileNullsCount());
    }

    @Test
    public void applyOnColumn_whenDailyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        DefaultDailyMonitoringObservabilityCheckSettingsSpec monitoringDailyDefaults = new DefaultDailyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringDaily(monitoringDailyDefaults);
        DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec();
        monitoringDailyDefaults.setColumn(columnDefaults);
        ColumnNullsDailyMonitoringChecksSpec nullsDefaults = new ColumnNullsDailyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getDaily().getNulls().getDailyNullsCount());
    }

    @Test
    public void applyOnColumn_whenMonthlyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        DefaultMonthlyMonitoringObservabilityCheckSettingsSpec monitoringDailyDefaults = new DefaultMonthlyMonitoringObservabilityCheckSettingsSpec();
        this.sut.setMonitoringMonthly(monitoringDailyDefaults);
        DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec();
        monitoringDailyDefaults.setColumn(columnDefaults);
        ColumnNullsMonthlyMonitoringChecksSpec nullsDefaults = new ColumnNullsMonthlyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setMonthlyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getMonthly().getNulls().getMonthlyNullsCount());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextAndTypeIsValid_thenConfiguresThemOnColumn() {
        targetColumn.getTypeSnapshot().setColumnType("varchar");

        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultProfilingColumnObservabilityCheckSettingsSpec();
        profilingDefaults.setColumn(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getText().getProfileTextLengthAboveMaxLength());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextButTypeIsNotMatchingNumeric_thenCheckNotConfigured() {
        targetColumn.getTypeSnapshot().setColumnType("numeric");

        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultProfilingColumnObservabilityCheckSettingsSpec();
        profilingDefaults.setColumn(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNull(targetColumn.getProfilingChecks().getText());
    }

    @Test
    public void applyOnColumn_whenProfilingChecksSetOnColumnAndTypeRequiresTextButTypeIsMissing_thenCheckNotConfigured() {
        targetColumn.getTypeSnapshot().setColumnType(null);

        DefaultProfilingObservabilityCheckSettingsSpec profilingDefaults = new DefaultProfilingObservabilityCheckSettingsSpec();
        this.sut.setProfiling(profilingDefaults);
        DefaultProfilingColumnObservabilityCheckSettingsSpec columnDefaults = new DefaultProfilingColumnObservabilityCheckSettingsSpec();
        profilingDefaults.setColumn(columnDefaults);
        ColumnTextProfilingChecksSpec textDefault = new ColumnTextProfilingChecksSpec();
        columnDefaults.setText(textDefault);
        textDefault.setProfileTextLengthAboveMaxLength(new ColumnTextLengthAboveMaxLengthCheckSpec());
        this.sut.applyOnColumn(targetColumn, this.dialectSettings);

        Assertions.assertNull(targetColumn.getProfilingChecks().getText());
    }
}
