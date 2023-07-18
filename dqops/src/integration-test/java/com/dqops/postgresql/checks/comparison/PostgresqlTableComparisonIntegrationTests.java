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

import com.dqops.checks.comparison.ComparisonCheckRules;
import com.dqops.checks.comparison.TableCompareCheckType;
import com.dqops.checks.table.profiling.TableComparisonProfilingChecksSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.TableCheckExecutionServiceImpl;
import com.dqops.execution.checks.TableCheckExecutionServiceObjectMother;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerStub;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
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
        TableComparisonProfilingChecksSpec tableComparisonChecks = this.comparedSampleTable.getTableSpec().getProfilingChecks().getComparisons().getOrAdd(COMPARISON_NAME);
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
        Assertions.assertEquals(1, checkExecutionSummary.getValidResultsCount());
        Assertions.assertEquals(0, checkExecutionSummary.getWarningSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getFatalSeverityIssuesCount());
        Assertions.assertEquals(0, checkExecutionSummary.getTotalExecutionErrorsCount());
    }
}
