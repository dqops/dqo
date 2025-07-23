/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.column.checkspecs.schema;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnSchemaProfilingChecksSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.metadata.sources.*;
import com.dqops.rules.comparison.Equals1RuleParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.CheckModelObjectMother;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnSchemaColumnExistsCheckSpecTests extends BaseTest {
    private ColumnSchemaColumnExistsCheckSpec sut;
    private TableSpec tableSpec;
    private ColumnSpec columnSpec;
    private ConnectionSpec connectionSpec;
    private ColumnSchemaColumnExistsCheckSpec profilingSut;
    private ProfilingCheckResult profilingCheckResult;
    private ColumnDataAssetProfilingResults dataAssetProfilingResults;
    private TableProfilingResults tableProfilingResults;
    private DqoRuleMiningConfigurationProperties checkMiningConfiguration;
    private CheckMiningParametersModel checkMiningParametersModel;
    private RuleMiningRuleRegistry ruleMiningRuleRegistry;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnSchemaColumnExistsCheckSpec();
        this.profilingSut = this.sut;
        this.tableSpec = TableSpecObjectMother.create("public", "tab");
        this.columnSpec = new ColumnSpec();
        this.columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec());
        this.tableSpec.getColumns().put("col", this.columnSpec);
        this.connectionSpec = ConnectionSpecObjectMother.createSampleConnectionSpec(this.tableSpec.getHierarchyId().getConnectionName());
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        this.columnSpec.getProfilingChecks().setSchema(new ColumnSchemaProfilingChecksSpec());
        this.columnSpec.getProfilingChecks().getSchema().setProfileColumnExists(this.profilingSut);
        this.profilingCheckResult = new ProfilingCheckResult();
        this.dataAssetProfilingResults = new ColumnDataAssetProfilingResults();
        this.tableProfilingResults = new TableProfilingResults();
        this.checkMiningConfiguration = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();
        this.checkMiningParametersModel = CheckMiningParametersModelObjectMother.create();
        this.ruleMiningRuleRegistry = RuleMiningRuleRegistryObjectMother.getDefault();
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentFromStatisticsButNoProfilingCheckAndValueIsExists_thenProposesRuleRule() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentFromStatisticsAndColumnIsCalculatedButReferencesSelf_thenProposesRuleRule() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.columnSpec.setSqlExpression("cast({column} as int)");

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentFromStatisticsIsZero_thenDoesNotConfigureCheck() {
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
    void proposeCheckConfiguration_whenColumnExistsPresentFromStatisticsButMiningParametersDisabledCheck_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue((1.0));
        this.checkMiningParametersModel.setProposeColumnExists(false);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentFromStatisticsButColumnIsCalculated_thenNotProposesRules() {
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue((1.0));
        this.columnSpec.setSqlExpression("age*2");

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, DataTypeCategory.text, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertFalse(proposed);
        Assertions.assertNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentFromProfilingCheckThatHasNoRules_thenProposesRules() {
        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false),
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getError());
    }

    @Test
    void proposeCheckConfiguration_whenColumnExistsPresentAndProfilingCheckHasRulesAndTargetIsMonitoringCheck_thenCopiesRules() {
        this.profilingSut = new ColumnSchemaColumnExistsCheckSpec();
        this.columnSpec.getProfilingChecks().getSchema().setProfileColumnExists(this.profilingSut);
        this.profilingSut.setWarning(new Equals1RuleParametersSpec());
        this.profilingSut.setError(new Equals1RuleParametersSpec());

        CheckModel profilingCheckModel = CheckModelObjectMother.createCheckModel(this.profilingSut, this.columnSpec.getProfilingChecks(),
                this.connectionSpec, this.tableSpec);
        this.profilingCheckResult.importCheckModel(profilingCheckModel);

        AbstractRootChecksContainerSpec targetCheckRootContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
        this.columnSpec.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().setDaily(new ColumnDailyMonitoringCheckCategoriesSpec());
        this.columnSpec.getMonitoringChecks().getDaily().setSchema(new ColumnSchemaDailyMonitoringChecksSpec());
        this.columnSpec.getMonitoringChecks().getDaily().getSchema().setDailyColumnExists(this.sut);
        CheckModel myCheckModel = CheckModelObjectMother.createCheckModel(this.sut, targetCheckRootContainer,
                this.connectionSpec, this.tableSpec);

        this.profilingCheckResult.setActualValue(1.0);
        this.profilingCheckResult.setSeverityLevel(CheckResultStatus.valid);

        boolean proposed = this.sut.proposeCheckConfiguration(this.profilingCheckResult, this.dataAssetProfilingResults, this.tableProfilingResults,
                tableSpec, targetCheckRootContainer,
                myCheckModel, this.checkMiningParametersModel, null, this.checkMiningConfiguration, JsonSerializerObjectMother.getDefault(), this.ruleMiningRuleRegistry);

        Assertions.assertTrue(proposed);
        Assertions.assertNotNull(this.sut.getWarning());
        Assertions.assertNotNull(this.sut.getError());
    }
}
