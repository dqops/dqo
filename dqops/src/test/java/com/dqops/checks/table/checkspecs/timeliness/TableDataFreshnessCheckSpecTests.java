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

package com.dqops.checks.table.checkspecs.timeliness;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.timeliness.TableTimelinessDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.profiling.TableTimelinessProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.core.configuration.DqoCheckMiningConfigurationProperties;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionSpecObjectMother;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.rules.comparison.MaxDaysRule1ParametersSpec;
import com.dqops.rules.comparison.MaxDaysRule2ParametersSpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.CheckMiningParametersModel;
import com.dqops.services.check.mining.DataAssetProfilingResults;
import com.dqops.services.check.mining.ProfilingCheckResult;
import com.dqops.services.check.mining.TableProfilingResults;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableDataFreshnessCheckSpecTests extends BaseTest {
    private TableDataFreshnessCheckSpec sut;
    private TableSpec tableSpec;
    private ConnectionSpec connectionSpec;
    private TableDataFreshnessCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private DataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoCheckMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;

    @BeforeEach
    void setUp() {
        this.sut = new TableDataFreshnessCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.tableSpec.getProfilingChecks().setTimeliness(new TableTimelinessProfilingChecksSpec());
        this.tableSpec.getProfilingChecks().getTimeliness().setProfileDataFreshness(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new DataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = new CheckMiningParametersModel();
        this.checkMiningParametersModel.setSeverityLevel(TargetRuleSeverityLevel.error);
    }
    @Test
    void proposeCheckConfiguration_whenTableFreshnessPresentFromCheckButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(10.0);
        this.checkMiningConfiguration.setFreshnessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault());

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
        this.checkMiningConfiguration.setFreshnessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault());

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
        this.checkMiningConfiguration.setFreshnessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault());

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
        this.checkMiningConfiguration.setFreshnessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault());

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new TableDataFreshnessCheckSpec();
        this.tableSpec.getProfilingChecks().getTimeliness().setProfileDataFreshness(this.profilingSut);
        this.profilingSut.setWarning(new MaxDaysRule1ParametersSpec(50.0));
        this.profilingSut.setError(new MaxDaysRule2ParametersSpec(30.0));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.tableSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.tableSpec.getMonitoringChecks().getDaily().setTimeliness(new TableTimelinessDailyMonitoringChecksSpec());
        this.tableSpec.getMonitoringChecks().getDaily().getTimeliness().setDailyDataFreshness(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(10.0);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);
        this.checkMiningConfiguration.setFreshnessMaxDaysMultiplier(2.0);
        this.checkMiningParametersModel.setProposeTimelinessChecks(true);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault());

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(50.0, this.sut.getWarning().getMaxDays());
        Assertions.assertEquals(30.0, this.sut.getError().getMaxDays());
    }
}
