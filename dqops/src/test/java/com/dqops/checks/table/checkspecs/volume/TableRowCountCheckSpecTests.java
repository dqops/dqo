/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.table.checkspecs.volume;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionSpecObjectMother;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableRowCountCheckSpecTests extends BaseTest {
    private TableRowCountCheckSpec sut;
    private TableSpec tableSpec;
    private ConnectionSpec connectionSpec;
    private TableRowCountCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private TableDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new TableRowCountCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.tableSpec.getProfilingChecks().setVolume(new TableVolumeProfilingChecksSpec());
        this.tableSpec.getProfilingChecks().getVolume().setProfileRowCount(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new TableDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentFromStatisticsButNoProfilingCheck_thenProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.checkMiningConfiguration.setMinCountRate(0.99);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(99L, this.sut.getError().getMinCount());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentFromStatisticsButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.checkMiningConfiguration.setMinCountRate(0.99);
        this.checkMiningParametersModel.setProposeMinimumRowCount(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentFromProfilingCheckThatHasNoRules_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.checkMiningConfiguration.setMinCountRate(0.99);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(99L, this.sut.getError().getMinCount());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentFromProfilingCheckThatHasNoRulesAndOldRowCountWas0_thenProposesRulesWithRowCount1() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(0.0);
        this.checkMiningConfiguration.setMinCountRate(0.99);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(1L, this.sut.getError().getMinCount());
    }

    @Test
    void proposeCheckConfiguration_whenRowCountPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new TableRowCountCheckSpec();
        this.tableSpec.getProfilingChecks().getVolume().setProfileRowCount(this.profilingSut);
        this.profilingSut.setWarning(new MinCountRule1ParametersSpec(50));
        this.profilingSut.setError(new MinCountRule1ParametersSpec(30));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.tableSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.tableSpec.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.tableSpec.getMonitoringChecks().getDaily().setVolume(new TableVolumeDailyMonitoringChecksSpec());
        this.tableSpec.getMonitoringChecks().getDaily().getVolume().setDailyRowCount(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);
        this.checkMiningConfiguration.setMinCountRate(0.99);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(50L, this.sut.getWarning().getMinCount());
        Assertions.assertEquals(30L, this.sut.getError().getMinCount());
    }
}
