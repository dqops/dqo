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
package com.dqops.services.check.matching;

import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnRecurringChecksRootSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Service that finds similar checks on a table or on a column.
 */
@Component
public class SimilarCheckMatchingServiceImpl implements SimilarCheckMatchingService {
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private DqoHomeContextFactory dqoHomeContextFactory;

    /**
     * Creates a service given dependencies.
     * @param specToModelCheckMappingService Check specification to check model mapping service.
     * @param dqoHomeContextFactory Returns the default DQO home context.
     */
    @Autowired
    public SimilarCheckMatchingServiceImpl(SpecToModelCheckMappingService specToModelCheckMappingService,
                                           DqoHomeContextFactory dqoHomeContextFactory) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
    }

    /**
     * Find similar checks in all check types for a table.
     * @param tableSpec Table to be analyzed.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarTableChecks(TableSpec tableSpec) {
        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on teh connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        ExecutionContext executionContext = new ExecutionContext(null, this.dqoHomeContextFactory.openLocalDqoHome());

        TableProfilingCheckCategoriesSpec profilingChecks = tableSpec.getProfilingChecks();
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(profilingChecks,
                checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        TableDailyRecurringCheckCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getDaily(), TableDailyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        TableMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getMonthly(), TableMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getDaily(), TableDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getMonthly(), TableMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                monthlyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }

    /**
     * Find similar checks in all check types for a column.
     * @param tableSpec Parent table of the column
     * @param columnSpec Column specification.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarColumnChecks(TableSpec tableSpec, ColumnSpec columnSpec) {
        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on teh connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        ExecutionContext executionContext = new ExecutionContext(null, this.dqoHomeContextFactory.openLocalDqoHome());

        ColumnProfilingCheckCategoriesSpec profilingChecks = Objects.requireNonNullElseGet(columnSpec.getProfilingChecks(), ColumnProfilingCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(profilingChecks,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        ColumnRecurringChecksRootSpec recurring = Objects.requireNonNullElseGet(columnSpec.getRecurringChecks(), ColumnRecurringChecksRootSpec::new);
        ColumnDailyRecurringCheckCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(recurring.getDaily(), ColumnDailyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        ColumnMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(recurring.getMonthly(), ColumnMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(columnSpec.getPartitionedChecks(), ColumnPartitionedChecksRootSpec::new);
        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getDaily(), ColumnDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getMonthly(), ColumnMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery),
                dailyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }
}
