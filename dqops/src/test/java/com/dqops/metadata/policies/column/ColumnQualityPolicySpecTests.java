/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.policies.column;

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
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnQualityPolicySpecTests extends BaseTest {
    private ColumnQualityPolicySpec sut;
    private TableSpec targetTable;
    private ColumnSpec targetColumn;
    private ProviderDialectSettings dialectSettings;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnQualityPolicySpec();
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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

        Assertions.assertNotNull(targetColumn.getProfilingChecks().getNulls().getProfileNullsCount());
    }

    @Test
    public void applyOnColumn_whenDailyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        ColumnDailyMonitoringCheckCategoriesSpec columnDefaults = new ColumnDailyMonitoringCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnNullsDailyMonitoringChecksSpec nullsDefaults = new ColumnNullsDailyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setDailyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

        Assertions.assertNotNull(targetColumn.getMonitoringChecks().getDaily().getNulls().getDailyNullsCount());
    }

    @Test
    public void applyOnColumn_whenMonthlyMonitoringChecksSetOnColumn_thenConfiguresThemOnColumn() {
        ColumnMonthlyMonitoringCheckCategoriesSpec columnDefaults = new ColumnMonthlyMonitoringCheckCategoriesSpec();
        this.sut.setColumnCheckRootContainer(columnDefaults);
        ColumnNullsMonthlyMonitoringChecksSpec nullsDefaults = new ColumnNullsMonthlyMonitoringChecksSpec();
        columnDefaults.setNulls(nullsDefaults);
        nullsDefaults.setMonthlyNullsCount(new ColumnNullsCountCheckSpec());
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

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
        this.sut.applyOnColumn(this.targetTable, targetColumn, this.dialectSettings, null);

        Assertions.assertNull(targetColumn.getPartitionedChecks());
    }
}
