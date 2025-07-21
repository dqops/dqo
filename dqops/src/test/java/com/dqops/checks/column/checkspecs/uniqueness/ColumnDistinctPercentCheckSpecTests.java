/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.column.checkspecs.uniqueness;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.uniqueness.ColumnUniquenessDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnUniquenessProfilingChecksSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.*;
import com.dqops.rules.comparison.BetweenPercentRuleParametersSpec;
import com.dqops.rules.comparison.CountBetweenRuleParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100ErrorParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100WarningParametersSpec;
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
public class ColumnDistinctPercentCheckSpecTests extends BaseTest {
    private ColumnDistinctPercentCheckSpec sut;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;
    private ConnectionSpec connectionSpec;
    private ColumnDistinctPercentCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private ColumnDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnDistinctPercentCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.columnSpec = new ColumnSpec();
        this.columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.tableSpec.getColumns().put("col", this.columnSpec);
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        this.columnSpec.getProfilingChecks().setUniqueness(new ColumnUniquenessProfilingChecksSpec());
        this.columnSpec.getProfilingChecks().getUniqueness().setProfileDistinctPercent(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.profilingCheckResult.setSensorName(ColumnNullsNullsCountSensorParametersSpec.SENSOR_NAME);
        this.dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndDistinctPercentWas100AndNotNullCountIsVeryLow_thenNotProposesCheckValueBecauseWeKnowTooLittle() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.dataAssetProfilingResults.setNotNullsCount(90L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndDistinctPercentWas100AndNotNulCountJustAboveThreshold_thenProposesCheckValueExactly100() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.dataAssetProfilingResults.setNotNullsCount(101L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndNotNullsCountAboveThresholdAndCloseTo100WithinErrorRowsRate_thenProposesCheckValueAsMin100Pct() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(99.90);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);
        this.checkMiningParametersModel.setFailChecksAtPercentErrorRows(2.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(100.0, this.sut.getError().getMinPercent());
        Assertions.assertEquals(100.0, this.sut.getError().getMaxPercent());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndDistinctCountBelowThresholdAndFarBelowRowCount_thenProposesCheckValue() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(15.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(10.5, this.sut.getError().getMinPercent());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndValueIsLowAndNotNullsCountIsLow_thenNotProposesRulesBecauseTableTooSmallToKnowIfRowcountGrowing() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(4.0);
        this.dataAssetProfilingResults.setNotNullsCount(50L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndValueBelowThresholdAndNotNullsCountIsHigh_thenDoesNotProposeRulesBecauseDistinctCountShouldBeConfigurd() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        long notNullsCount = 5000000000L;
        this.profilingCheckResult.setActualValue(99.0 / 5000000000L); // percent of distinct out of all not-nulls (99 distinct values)
        this.dataAssetProfilingResults.setNotNullsCount(notNullsCount);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButNoProfilingCheckAndValueHighAndAboveThresholdAndRowCountNotHighEnough_thenNotProposesRulesBecauseTooManyFewDuplicates() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(500.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);  // 20 duplicates per value

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromStatisticsButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(100.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);
        this.checkMiningParametersModel.setProposeUniquenessChecks(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromProfilingCheckThatHasNoRulesAndPercentAbove50_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(60.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(48.0, this.sut.getError().getMinPercent());
        Assertions.assertEquals(72.0, this.sut.getError().getMaxPercent());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentFromProfilingCheckThatHasNoRulesAndPercentBelow50_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(40.0);
        this.dataAssetProfilingResults.setNotNullsCount(10000L);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(28.0, this.sut.getError().getMinPercent());
        Assertions.assertEquals(52.0, this.sut.getError().getMaxPercent());
    }

    @Test
    void proposeCheckConfiguration_whenDistinctPercentPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new ColumnDistinctPercentCheckSpec();
        this.columnSpec.getProfilingChecks().getUniqueness().setProfileDistinctPercent(this.profilingSut);
        this.profilingSut.setWarning(new BetweenPercentRuleParametersSpec(60.0, 70.0));
        this.profilingSut.setError(new BetweenPercentRuleParametersSpec(50.0, 80.0));

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.columnSpec.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().setDaily(new ColumnDailyMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().getDaily().setUniqueness(new ColumnUniquenessDailyMonitoringChecksSpec());
        this.columnSpec.getMonitoringChecks().getDaily().getUniqueness().setDailyDistinctPercent(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(15.0);
        this.dataAssetProfilingResults.setNotNullsCount(100000L);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
        Assertions.assertEquals(60.0, this.sut.getWarning().getMinPercent());
        Assertions.assertEquals(50.0, this.sut.getError().getMinPercent());
    }
}
