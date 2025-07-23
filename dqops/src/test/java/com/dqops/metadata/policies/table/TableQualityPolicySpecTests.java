/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.policies.table;

import com.dqops.BaseTest;
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
public class TableQualityPolicySpecTests extends BaseTest {
    private TableQualityPolicySpec sut;
    private TableSpec targetTable;
    private ColumnSpec targetColumn;
    private ProviderDialectSettings dialectSettings;

    @BeforeEach
    void setUp() {
        this.sut = new TableQualityPolicySpec();
        this.targetTable = new TableSpec();
        this.targetColumn = new ColumnSpec();
        this.targetColumn.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.targetTable.getColumns().put("col1", this.targetColumn);
        this.dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.postgresql);
    }

    @Test
    public void applyOnTable_whenNoDefaultChecks_thenDoesNothing() {
        this.sut.applyOnTable(targetTable, this.dialectSettings, null);
    }

    @Test
    public void applyOnTable_whenProfilingChecksSet_thenConfiguresThemOnTable() {
        TableProfilingCheckCategoriesSpec tableDefaults = new TableProfilingCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeProfilingChecksSpec volumeDefaults = new TableVolumeProfilingChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setProfileRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings, null);

        Assertions.assertNotNull(targetTable.getProfilingChecks().getVolume().getProfileRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenDailyMonitoringChecksSet_thenConfiguresThemOnTable() {
        TableDailyMonitoringCheckCategoriesSpec tableDefaults = new TableDailyMonitoringCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeDailyMonitoringChecksSpec volumeDefaults = new TableVolumeDailyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setDailyRowCountAnomaly(new TableRowCountAnomalyDifferencingCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings, null);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getDaily().getVolume().getDailyRowCountAnomaly());
    }

    @Test
    public void applyOnTable_whenMonthlyMonitoringChecksSet_thenConfiguresThemOnTable() {
        TableMonthlyMonitoringCheckCategoriesSpec tableDefaults = new TableMonthlyMonitoringCheckCategoriesSpec();
        this.sut.setTableCheckRootContainer(tableDefaults);
        TableVolumeMonthlyMonitoringChecksSpec volumeDefaults = new TableVolumeMonthlyMonitoringChecksSpec();
        tableDefaults.setVolume(volumeDefaults);
        volumeDefaults.setMonthlyRowCount(new TableRowCountCheckSpec());
        this.sut.applyOnTable(targetTable, this.dialectSettings, null);

        Assertions.assertNotNull(targetTable.getMonitoringChecks().getMonthly().getVolume().getMonthlyRowCount());
    }
}
