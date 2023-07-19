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

package com.dqops.postgresql.checks.comparison;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.*;
import com.dqops.connectors.ProviderType;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.TableCheckExecutionServiceImpl;
import com.dqops.execution.checks.TableCheckExecutionServiceObjectMother;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerStub;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.postgresql.BasePostgresqlIntegrationTest;
import com.dqops.rules.comparison.MaxDiffPercentRule1ParametersSpec;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostgresqlTableComparisonIntegrationTests extends BasePostgresqlIntegrationTest {
    private static final String COMPARISON_NAME = "comparison";

    private SampleTableMetadata comparedSampleTable;
    private SampleTableMetadata referenceSampleTable;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private TableComparisonConfigurationSpec tableComparisonConfigurationSpec;
    private ExecutionContext executionContext;
    private TableCheckExecutionServiceImpl tableCheckExecutionService;
    private UserHome userHome;
    private ConnectionWrapper comparedConnectionWrapper;
    private TableWrapper comparedTableWrapper;
    private CheckSearchFilters checkSearchFilters;

    @BeforeEach
    void setUp() {
        comparedSampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.comparedSampleTable);
        referenceSampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.referenceSampleTable);
        referenceSampleTable.setConnectionName("reference_connection");
        comparedSampleTable.getTableSpec().setTimestampColumns(TimestampColumnsSpec.createForPartitionByColumn("date"));
        referenceSampleTable.getTableSpec().setTimestampColumns(TimestampColumnsSpec.createForPartitionByColumn("date"));
        comparedSampleTable.getTableSpec().setIncrementalTimeWindow(PartitionIncrementalTimeWindowSpecObjectMother.createLongTermTimeWindow());
        referenceSampleTable.getTableSpec().setIncrementalTimeWindow(PartitionIncrementalTimeWindowSpecObjectMother.createLongTermTimeWindow());

        userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithEmptyTemporaryContext();
        userHomeContext = userHomeContextFactory.openLocalUserHome();
        UserHomeContextObjectMother.addSampleTable(userHomeContext, comparedSampleTable);
        UserHomeContextObjectMother.addSampleTable(userHomeContext, referenceSampleTable);
        userHome = userHomeContext.getUserHome();
        comparedConnectionWrapper = userHome.getConnections().getByObjectName(comparedSampleTable.getConnectionName(), true);
        comparedTableWrapper = comparedConnectionWrapper.getTables().getByObjectName(comparedSampleTable.getTableSpec().getPhysicalTableName(), true);

        tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec();
        tableComparisonConfigurationSpec.setComparedTableGroupingName(comparedSampleTable.getTableSpec().getDefaultGroupingName());
        tableComparisonConfigurationSpec.setReferenceTableGroupingName(referenceSampleTable.getTableSpec().getDefaultGroupingName());
        tableComparisonConfigurationSpec.setReferenceTableConnectionName(referenceSampleTable.getConnectionSpec().getConnectionName());
        tableComparisonConfigurationSpec.setReferenceTableSchemaName(referenceSampleTable.getTableSpec().getPhysicalTableName().getSchemaName());
        tableComparisonConfigurationSpec.setReferenceTableName(referenceSampleTable.getTableSpec().getPhysicalTableName().getTableName());
        comparedSampleTable.getTableSpec().getTableComparisons().put(COMPARISON_NAME, tableComparisonConfigurationSpec);

        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        this.executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        this.tableCheckExecutionService = TableCheckExecutionServiceObjectMother.createCheckExecutionServiceOnUserHomeContext(userHomeContext);

        checkSearchFilters = new CheckSearchFilters();
    }

    @Test
    void profiling_whenComparingRowCountNoGroupingSameTable_thenValuesMatch() {
        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(1, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(1, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void profiling_whenComparingRowCountGroupingOnBooleanAndComparingToSameTable_thenValuesMatch() {
        this.comparedSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder_ok"));
        this.referenceSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder_ok"));

        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(2, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(2, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void profiling_whenComparingRowCountGroupingOnBooleanAndComparingToSameTableButGroupingDifferentColumns_thenValuesNotMatch() {
        this.comparedSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder_ok"));
        this.referenceSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("email_ok"));

        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(2, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(2, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void profiling_whenComparingRowCountGroupingAndGroupingUsesCalculatedColumnThatMatchNotAlways_thenValuesNotAlwaysMatch() {
        this.referenceSampleTable.getTableSpec().getColumns().put("null_placeholder_ok_recalculated",
                new ColumnSpec() {{
                    setSqlExpression("{alias}.null_placeholder_ok * 2");
                }});
        this.comparedSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder_ok"));
        this.referenceSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder_ok_recalculated"));

        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(4, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(1, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(3, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void profiling_whenComparingRowCountGroupingOnNotMatchingColumns_thenValuesNotMatch() {
        this.comparedSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("boolean_placeholder"));
        this.referenceSampleTable.getTableSpec().getDefaultDataGroupingConfiguration()
                .setLevel1(DataGroupingDimensionSpec.createForColumn("null_placeholder"));

        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(25, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(25, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void profiling_whenComparingSumOnColumnNoGroupingSameTable_thenValuesMatch() {
        ColumnSpec comparedColumn = this.comparedSampleTable.getTableSpec().getColumns().get("range_integers");
        AbstractRootChecksContainerSpec columnCheckRootContainer =
                comparedColumn.getColumnCheckRootContainer(CheckType.PROFILING, null, true);
        AbstractColumnComparisonCheckCategorySpec columnComparisonChecks =
                (AbstractColumnComparisonCheckCategorySpec) columnCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        columnComparisonChecks.setReferenceColumn("range_integers");
        ComparisonCheckRules sumMatch = columnComparisonChecks.getCheckSpec(ColumnCompareCheckType.sum_match, true);
        sumMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(1, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(1, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }

    @Test
    void partitionedDaily_whenComparingRowCountNoGroupingSameTable_thenValuesMatch() {
        AbstractRootChecksContainerSpec tableCheckRootContainer = this.comparedSampleTable.getTableSpec()
                .getTableCheckRootContainer(CheckType.PARTITIONED, CheckTimeScale.daily, true);
        AbstractTableComparisonCheckCategorySpec tableComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec) tableCheckRootContainer.getComparisons().getOrAdd(COMPARISON_NAME);
        ComparisonCheckRules rowCountMatch = tableComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec());

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(this.executionContext,
                this.userHomeContext.getUserHome(),
                comparedConnectionWrapper,
                comparedTableWrapper,
                checkSearchFilters,
                null,
                new CheckExecutionProgressListenerStub(),
                false,
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNull(checkExecutionSummary.getCheckExecutionErrorSummary(),
                checkExecutionSummary.getCheckExecutionErrorSummary() != null ?
                        checkExecutionSummary.getCheckExecutionErrorSummary().getDebugMessage() : null);
        Assertions.assertEquals(1, checkExecutionSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(25, checkExecutionSummary.getTotalCheckResultsCount());
        Assertions.assertEquals(25, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }
}
