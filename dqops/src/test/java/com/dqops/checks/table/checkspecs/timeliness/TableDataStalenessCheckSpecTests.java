/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.table.checkspecs.timeliness;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.monitoring.timeliness.TableTimelinessDailyMonitoringChecksSpec;
import com.dqops.checks.table.profiling.TableTimelinessProfilingChecksSpec;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionSpecObjectMother;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.rules.comparison.MaxDaysRule1ParametersSpec;
import com.dqops.rules.comparison.MaxDaysRule2ParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableDataStalenessCheckSpecTests extends BaseTest {
    private TableDataStalenessCheckSpec sut;
    private TableSpec tableSpec;
    private ConnectionSpec connectionSpec;
    private TableDataStalenessCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private TableDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new TableDataStalenessCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.tableSpec.getProfilingChecks().setTimeliness(new TableTimelinessProfilingChecksSpec());
        this.tableSpec.getProfilingChecks().getTimeliness().setProfileDataStaleness(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new TableDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }
    @Test
    void proposeCheckConfiguration_whenTableFreshnessPresentFromCheckButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(10.0);
        this.checkMiningConfiguration.setTimelinessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenTableFreshnessPresentFromProfilingCheckThatHasNoRules_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(10.0);
        this.checkMiningConfiguration.setTimelinessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(20.0, this.sut.getError().getMaxDays());
    }

    @Test
    void proposeCheckConfiguration_whenTableFreshnessPresentFromProfilingCheckThatHasNoRulesAndValueIsNotRound_thenProposesRulesWithRoundedValue() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(11.1234567);
        this.checkMiningConfiguration.setTimelinessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(22.2, this.sut.getError().getMaxDays());
    }

    @Test
    void proposeCheckConfiguration_whenTableDelayPresentFromProfilingCheckThatHasNoRulesAndMeasuredDelayIsNegative_thenNotProposed() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(-10.0);
        this.checkMiningConfiguration.setTimelinessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new TableDataStalenessCheckSpec();
        this.tableSpec.getProfilingChecks().getTimeliness().setProfileDataStaleness(this.profilingSut);
        this.profilingSut.setWarning(new MaxDaysRule1ParametersSpec(50.0));
        this.profilingSut.setError(new MaxDaysRule2ParametersSpec(30.0));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.tableSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.tableSpec.getMonitoringChecks().getDaily().setTimeliness(new TableTimelinessDailyMonitoringChecksSpec());
        this.tableSpec.getMonitoringChecks().getDaily().getTimeliness().setDailyDataStaleness(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(10.0);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);
        this.checkMiningConfiguration.setTimelinessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(50.0, this.sut.getWarning().getMaxDays());
        Assertions.assertEquals(30.0, this.sut.getError().getMaxDays());
    }
}
