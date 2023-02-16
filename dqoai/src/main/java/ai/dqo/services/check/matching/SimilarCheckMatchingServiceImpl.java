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

import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.ColumnDailyCheckpointCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
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

        TableAdHocCheckCategoriesSpec adHocChecks = tableSpec.getChecks();
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(adHocChecks,
                checkSearchFilters, null, tableSpec, null, null),
                adHocChecks.getCheckTarget(), adHocChecks.getCheckType(), adHocChecks.getCheckTimeScale());

        TableDailyCheckpointCategoriesSpec dailyCheckpoints = Objects.requireNonNullElseGet(tableSpec.getCheckpoints().getDaily(), TableDailyCheckpointCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyCheckpoints,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyCheckpoints.getCheckTarget(), dailyCheckpoints.getCheckType(), dailyCheckpoints.getCheckTimeScale());

        TableMonthlyCheckpointCategoriesSpec monthlyCheckpoints = Objects.requireNonNullElseGet(tableSpec.getCheckpoints().getMonthly(), TableMonthlyCheckpointCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyCheckpoints,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyCheckpoints.getCheckTarget(), monthlyCheckpoints.getCheckType(), monthlyCheckpoints.getCheckTimeScale());

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

        ColumnAdHocCheckCategoriesSpec adHocChecks = Objects.requireNonNullElseGet(columnSpec.getChecks(), ColumnAdHocCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(adHocChecks,
                        checkSearchFilters, null, tableSpec,null, null),
                adHocChecks.getCheckTarget(), adHocChecks.getCheckType(), adHocChecks.getCheckTimeScale());

        ColumnCheckpointsSpec checkpoints = Objects.requireNonNullElseGet(columnSpec.getCheckpoints(), ColumnCheckpointsSpec::new);
        ColumnDailyCheckpointCategoriesSpec dailyCheckpoints = Objects.requireNonNullElseGet(checkpoints.getDaily(), ColumnDailyCheckpointCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(dailyCheckpoints,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyCheckpoints.getCheckTarget(), dailyCheckpoints.getCheckType(), dailyCheckpoints.getCheckTimeScale());

        ColumnMonthlyCheckpointCategoriesSpec monthlyCheckpoints = Objects.requireNonNullElseGet(checkpoints.getMonthly(), ColumnMonthlyCheckpointCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToUiCheckMappingService.createUiModel(monthlyCheckpoints,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyCheckpoints.getCheckTarget(), monthlyCheckpoints.getCheckType(), monthlyCheckpoints.getCheckTimeScale());

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
