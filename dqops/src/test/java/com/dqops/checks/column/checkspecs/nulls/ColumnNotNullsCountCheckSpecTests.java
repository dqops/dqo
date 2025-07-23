/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.column.checkspecs.nulls;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.*;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.rules.comparison.MaxCountRule0WarningParametersSpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.sensors.column.nulls.ColumnNullsNullsCountSensorParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnNotNullsCountCheckSpecTests extends BaseTest {
    private ColumnNotNullsCountCheckSpec sut;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;
    private ConnectionSpec connectionSpec;
    private ColumnNotNullsCountCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private ColumnDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnNotNullsCountCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.columnSpec = new ColumnSpec();
        this.columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.tableSpec.getColumns().put("col", this.columnSpec);
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        this.columnSpec.getProfilingChecks().setNulls(new ColumnNullsProfilingChecksSpec());
        this.columnSpec.getProfilingChecks().getNulls().setProfileNotNullsCount(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.profilingCheckResult.setSensorName(ColumnNullsNullsCountSensorParametersSpec.SENSOR_NAME);
        this.dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenNotNullCountPresentFromStatisticsButNoProfilingCheckAndCountOfNotNullRowsNotZero_thenProposesCheckValueCalculatedFromNotNullsCount() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(5.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(4L, this.sut.getError().getMinCount());
    }

    @Test
    void proposeCheckConfiguration_whenNotNullCountPresentFromStatisticsButNoProfilingCheckAndValueIsZero_thenNotProposesRulesBecauseWeWillProposeEmptyColumnCheck() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNotNullCountPresentFromStatisticsButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(5.0);
        this.checkMiningParametersModel.setProposeNotNullsChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNotNullCountPresentFromProfilingCheckThatHasNoRules_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(5.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(4L, this.sut.getError().getMinCount());
    }

    @Test
    void proposeCheckConfiguration_whenNotNullCountPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new ColumnNotNullsCountCheckSpec();
        this.columnSpec.getProfilingChecks().getNulls().setProfileNotNullsCount(this.profilingSut);
        this.profilingSut.setWarning(new MinCountRule1ParametersSpec(50L));
        this.profilingSut.setError(new MinCountRule1ParametersSpec(30L));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.columnSpec.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().setDaily(new ColumnDailyMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().getDaily().setNulls(new ColumnNullsDailyMonitoringChecksSpec());
        this.columnSpec.getMonitoringChecks().getDaily().getNulls().setDailyNotNullsCount(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(50L, this.sut.getWarning().getMinCount());
        Assertions.assertEquals(30L, this.sut.getError().getMinCount());
    }
}
