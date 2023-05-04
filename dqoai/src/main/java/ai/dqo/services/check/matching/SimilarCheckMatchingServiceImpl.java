/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.services.check.matching;

import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnDailyRecurringCheckCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnMonthlyRecurringCheckCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnRecurringChecksRootSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.recurring.TableDailyRecurringCategoriesSpec;
import ai.dqo.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Service that finds similar checks on a table or on a column.
 */
@Component
public class SimilarCheckMatchingServiceImpl implements SimilarCheckMatchingService {
    private SpecToUiCheckMappingService specToUiCheckMappingService;

    /**
     * Creates a service given dependencies.
     * @param specToUiCheckMappingService Check specification to UI model mapping service.
     */
    @Autowired
    public SimilarCheckMatchingServiceImpl(SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.specToUiCheckMappingService = specToUiCheckMappingService;
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

        TableProfilingCheckCategoriesSpec profilingChecks = tableSpec.getProfilingChecks();
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(profilingChecks,
                checkSearchFilters, null, tableSpec, null, null),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        TableDailyRecurringCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getDaily(), TableDailyRecurringCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        TableMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getMonthly(), TableMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getDaily(), TableDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getMonthly(), TableMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
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

        ColumnProfilingCheckCategoriesSpec profilingChecks = Objects.requireNonNullElseGet(columnSpec.getProfilingChecks(), ColumnProfilingCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(profilingChecks,
                        checkSearchFilters, null, tableSpec,null, null),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        ColumnRecurringChecksRootSpec recurring = Objects.requireNonNullElseGet(columnSpec.getRecurringChecks(), ColumnRecurringChecksRootSpec::new);
        ColumnDailyRecurringCheckCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(recurring.getDaily(), ColumnDailyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        ColumnMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(recurring.getMonthly(), ColumnMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(columnSpec.getPartitionedChecks(), ColumnPartitionedChecksRootSpec::new);
        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getDaily(), ColumnDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getMonthly(), ColumnMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }
}
