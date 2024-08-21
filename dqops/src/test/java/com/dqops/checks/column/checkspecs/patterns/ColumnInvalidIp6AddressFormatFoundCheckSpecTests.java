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

package com.dqops.checks.column.checkspecs.patterns;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.patterns.ColumnPatternsDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnPatternsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.*;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.rules.comparison.MaxCountRule0WarningParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;

@SpringBootTest
public class ColumnInvalidIp6AddressFormatFoundCheckSpecTests extends BaseTest {
    private ColumnInvalidIp6AddressFormatFoundCheckSpec sut;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;
    private ConnectionSpec connectionSpec;
    private ColumnInvalidIp6AddressFormatFoundCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private ColumnDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnInvalidIp6AddressFormatFoundCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.columnSpec = new ColumnSpec();
        this.columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.tableSpec.getColumns().put("col", this.columnSpec);
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        this.columnSpec.getProfilingChecks().setPatterns(new ColumnPatternsProfilingChecksSpec());
        this.columnSpec.getProfilingChecks().getPatterns().setProfileInvalidIp6AddressFormatFound(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.tableProfilingResults.setTimeZoneId(ZoneId.of("UTC"));
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountWithNoProfilingCheckResultAndRequiresZeroErrorsAndNoSamples_thenNotProposesCheckBecauseNoSamples() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountWithNoProfilingCheckResultAndAcceptsAboveZeroErrorsErrorsAndNoSamples_thenNotProposesCheckBecauseNoSamples() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(5.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvaliIP6AddressCountWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlyValidValues_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("66b2:454b:2638:9d9e:b0c1:1c3e:a39c:e83a", 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("d8bc:e680:7787:3c3e:c9b9:61d6:80e8:bcfa", 4000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(0L, this.sut.getError().getMaxCount());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlySomeValidValues_thenNotProposesCheckBecauseErrorsAlreadyFound() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("66b2:454b:2638:9d9e:b0c1:1c3e:a39c:e83a", 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("invalid value", 100L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountWithNoProfilingCheckResultAndMaxErrors5PctAndSamplesContainEnoughValidValues_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("66b2:454b:2638:9d9e:b0c1:1c3e:a39c:e83a", 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("invalid value", 10L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(5.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(0L, this.sut.getError().getMaxCount());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountPresentFromProfilingCheckThatHasNoRulesAndPercentLowAndBelowMaxErrorRate_thenProposesRulesWithMinPercent100() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);
        this.tableProfilingResults.setRowCount(50000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(0L, this.sut.getError().getMaxCount());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountPresentFromProfilingCheckThatHasNoRulesAndPercentBelowMaxPercentErrorsAccepted_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        long notNullCount = 10000L;
        this.profilingCheckResult.setActualValue(notNullCount * this.checkMiningParametersModel.getMaxPercentErrorRowsForPercentChecks() + 10.0);
        this.dataAssetProfilingResults.setNotNullsCount(notNullCount);
        this.tableProfilingResults.setRowCount(50000L);


        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountPresentFromProfilingCheckThatHasNoRulesAndPercentBelowMaxPercentErrorsAcceptedButAboveMaxExpectedError_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        long notNullCount = 10000L;
        this.profilingCheckResult.setActualValue(notNullCount * this.checkMiningParametersModel.getFailChecksAtPercentErrorRows() + 1);
        this.dataAssetProfilingResults.setNotNullsCount(notNullCount);
        this.tableProfilingResults.setRowCount(50000L);


        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.datetime_date, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountNoProfilingCheckAndValidSampleValueButColumnTypeNotText_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(1.0, 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(1001L);
        this.tableProfilingResults.setRowCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_float, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountPresentFromProfilingCheckButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.dataAssetProfilingResults.setNotNullsCount(10001L);
        this.tableProfilingResults.setRowCount(20000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);
        this.checkMiningParametersModel.setProposeStandardPatternChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }
    @Test
    void proposeCheckConfiguration_whenInvalidIP6AddressCountPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new ColumnInvalidIp6AddressFormatFoundCheckSpec();
        this.columnSpec.getProfilingChecks().getPatterns().setProfileInvalidIp6AddressFormatFound(this.profilingSut);
        this.profilingSut.setWarning(new MaxCountRule0WarningParametersSpec(10L));
        this.profilingSut.setError(new MaxCountRule0ErrorParametersSpec(20L));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.columnSpec.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().setDaily(new ColumnDailyMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().getDaily().setPatterns(new ColumnPatternsDailyMonitoringChecksSpec());
        this.columnSpec.getMonitoringChecks().getDaily().getPatterns().setDailyInvalidIp6AddressFormatFound(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(80.0);
        this.dataAssetProfilingResults.setNotNullsCount(100000L);
        this.tableProfilingResults.setRowCount(50000L);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(10L, this.sut.getWarning().getMaxCount());
        Assertions.assertEquals(20L, this.sut.getError().getMaxCount());
    }
}
