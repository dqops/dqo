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

package com.dqops.checks.column.checkspecs.acceptedvalues;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.acceptedvalues.ColumnAcceptedValuesDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnAcceptedValuesProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.*;
import com.dqops.rules.comparison.MinPercentRule100ErrorParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100WarningParametersSpec;
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
public class ColumnNumberFoundInSetPercentCheckSpecTests extends BaseTest {
    private ColumnNumberFoundInSetPercentCheckSpec sut;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;
    private ConnectionSpec connectionSpec;
    private ColumnNumberFoundInSetPercentCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private ColumnDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnNumberFoundInSetPercentCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.columnSpec = new ColumnSpec();
        this.columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.tableSpec.getColumns().put("col", this.columnSpec);
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        this.columnSpec.getProfilingChecks().setAcceptedValues(new ColumnAcceptedValuesProfilingChecksSpec());
        this.columnSpec.getProfilingChecks().getAcceptedValues().setProfileNumberFoundInSetPercent(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.tableProfilingResults.setTimeZoneId(ZoneId.of("UTC"));
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndRequiresZeroErrorsAndNoSamples_thenNotProposesCheckBecauseNoSamples() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndAcceptsAboveZeroErrorsErrorsAndNoSamples_thenNotProposesCheckBecauseNoSamples() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(5.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlyIntegers_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(300, 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(400, 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertNotNull(this.sut.getParameters().getExpectedValues());
        Assertions.assertEquals(2, this.sut.getParameters().getExpectedValues().size());
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(300L));
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(400L));
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlyLongs_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(300L, 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(400L, 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertNotNull(this.sut.getParameters().getExpectedValues());
        Assertions.assertEquals(2, this.sut.getParameters().getExpectedValues().size());
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(300L));
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(400L));
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlyLongsDoubles_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(300.0, 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(400.0, 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertNotNull(this.sut.getParameters().getExpectedValues());
        Assertions.assertEquals(2, this.sut.getParameters().getExpectedValues().size());
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(300L));
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(400L));
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainOnlyTextsThatAreIntegers_thenProposesCheckSelectedConfiguration() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("300", 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("400", 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertNotNull(this.sut.getParameters().getExpectedValues());
        Assertions.assertEquals(2, this.sut.getParameters().getExpectedValues().size());
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(300L));
        Assertions.assertTrue(this.sut.getParameters().getExpectedValues().contains(400L));
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainNonNumericValues_thenNotProposesCheckBecauseValuesNotNumeric() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(100, 1000L, null));
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue("invalid value", 100L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentWithNoProfilingCheckResultAndMaxErrors0PctAndSamplesContainFloatValuesWithFractionPart_thenNotProposesCheckBecauseValuesNotInteger() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(100.5, 1000L, null));
        this.dataAssetProfilingResults.setNotNullsCount(5000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(0.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    void proposeCheckConfiguration_whenNumberFoundInSetPercentPresentFromProfilingCheckThatHasNoRulesAndPercentLowAndBelowMaxErrorRate_thenProposesRulesWithMinPercent100() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(99.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentPresentFromProfilingCheckThatHasNoRulesAndPercentBelowMaxPercentErrorsAccepted_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0 - this.checkMiningParametersModel.getMaxPercentErrorRowsForPercentChecks() - 1);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentPresentFromProfilingCheckThatHasNoRulesAndPercentBelowMaxPercentErrorsAcceptedButAboveMaxExpectedError_thenProposesRulesWithMaxPercentToFit() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0 - this.checkMiningParametersModel.getFailChecksAtPercentErrorRows() - 1);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.datetime_date, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(96.1, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentNoProfilingCheckAndValidSampleValueTooManySampleValues_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        for (int i = 0; i < 100; i++) {
            this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(i, 10L, null));
        }
        this.dataAssetProfilingResults.setNotNullsCount(1001L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.numeric_integer, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentNoProfilingCheckAndValidSampleValueButColumnTypeDate_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(null);
        for (int i = 0; i < 10; i++) {
            this.dataAssetProfilingResults.getSampleValues().add(new ProfilingSampleValue(i, 10L, null));
        }
        this.dataAssetProfilingResults.setNotNullsCount(1001L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.datetime_date, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentPresentFromProfilingCheckButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.dataAssetProfilingResults.setNotNullsCount(10001L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);
        this.checkMiningParametersModel.setProposeAcceptedValuesChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }
    @Test
    void proposeCheckConfiguration_whenNumberFoundInSetPercentPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new ColumnNumberFoundInSetPercentCheckSpec();
        this.columnSpec.getProfilingChecks().getAcceptedValues().setProfileNumberFoundInSetPercent(this.profilingSut);
        this.profilingSut.setWarning(new MinPercentRule100WarningParametersSpec(10.0));
        this.profilingSut.setError(new MinPercentRule100ErrorParametersSpec(20.0));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.columnSpec.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().setDaily(new ColumnDailyMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().getDaily().setAcceptedValues(new ColumnAcceptedValuesDailyMonitoringChecksSpec());
        this.columnSpec.getMonitoringChecks().getDaily().getAcceptedValues().setDailyNumberFoundInSetPercent(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(80.0);
        this.dataAssetProfilingResults.setNotNullsCount(100000L);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(10.0, this.sut.getWarning().getMinPercent());
        Assertions.assertEquals(20.0, this.sut.getError().getMinPercent());
    }
}
