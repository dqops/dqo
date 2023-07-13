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
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHomeImpl;
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

    /**
     * Creates a service given dependencies.
     * @param specToModelCheckMappingService Check specification to check model mapping service.
     */
    @Autowired
    public SimilarCheckMatchingServiceImpl(SpecToModelCheckMappingService specToModelCheckMappingService) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
    }

    /**
     * Creates a column specification with a label.
     * @param label Label to add to the column.
     * @return Column specification.
     */
    private ColumnSpec createColumnWithLabel(String label) {
        ColumnSpec columnSpec = new ColumnSpec();
        LabelSetSpec labels = new LabelSetSpec();
        labels.add(label);
        columnSpec.setLabels(labels);
        return columnSpec;
    }

    /**
     * Creates a table specification with one sample table and the timestamp columns configured.
     * @param addAnalyzedColumn Boolean value - add the analyzed column.
     * @return Table specification.
     */
    private TableSpec createTableSpec(boolean addAnalyzedColumn) {
        TableSpec tableSpec = new TableSpec();
        tableSpec.setPhysicalTableName(new PhysicalTableName("target_schema", "target_table"));

        if (addAnalyzedColumn) {
            ColumnSpec columnSpec = createColumnWithLabel("This is the column that is analyzed for data quality issues");
            tableSpec.getColumns().put("target_column", columnSpec);
        }

        tableSpec.getColumns().put("col_event_timestamp", createColumnWithLabel("optional column that stores the timestamp when the event/transaction happened"));
        tableSpec.getColumns().put("col_inserted_at", createColumnWithLabel("optional column that stores the timestamp when row was ingested"));
        TimestampColumnsSpec timestampColumns = tableSpec.getTimestampColumns();
        timestampColumns.setEventTimestampColumn("col_event_timestamp");
        timestampColumns.setIngestionTimestampColumn("col_inserted_at");

        return tableSpec;
    }

    /**
     * Find similar checks in all check types for a table.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarTableChecks() {
        UserHomeImpl userHome = new UserHomeImpl();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(false);
        tableWrapper.setSpec(tableSpec);

        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on teh connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        TableProfilingCheckCategoriesSpec profilingChecks = tableSpec.getProfilingChecks();
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(profilingChecks,
                checkSearchFilters, null, tableSpec, null, null),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        TableDailyRecurringCheckCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getDaily(), TableDailyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        TableMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(tableSpec.getRecurringChecks().getMonthly(), TableMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getDaily(), TableDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getMonthly(), TableMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }

    /**
     * Find similar checks in all check types for a column.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarColumnChecks() {
        UserHomeImpl userHome = new UserHomeImpl();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(true);
        tableWrapper.setSpec(tableSpec);
        ColumnSpec columnSpec = tableSpec.getColumns().getAt(0);

        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on teh connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        ColumnProfilingCheckCategoriesSpec profilingChecks = Objects.requireNonNullElseGet(columnSpec.getProfilingChecks(), ColumnProfilingCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(profilingChecks,
                        checkSearchFilters, null, tableSpec,null, null),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        ColumnRecurringChecksRootSpec recurring = Objects.requireNonNullElseGet(columnSpec.getRecurringChecks(), ColumnRecurringChecksRootSpec::new);
        ColumnDailyRecurringCheckCategoriesSpec dailyRecurring = Objects.requireNonNullElseGet(recurring.getDaily(), ColumnDailyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyRecurring.getCheckTarget(), dailyRecurring.getCheckType(), dailyRecurring.getCheckTimeScale());

        ColumnMonthlyRecurringCheckCategoriesSpec monthlyRecurring = Objects.requireNonNullElseGet(recurring.getMonthly(), ColumnMonthlyRecurringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyRecurring,
                        checkSearchFilters, null, tableSpec,null, null),
                monthlyRecurring.getCheckTarget(), monthlyRecurring.getCheckType(), monthlyRecurring.getCheckTimeScale());

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(columnSpec.getPartitionedChecks(), ColumnPartitionedChecksRootSpec::new);
        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getDaily(), ColumnDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getMonthly(), ColumnMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec,null, null),
                dailyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }
}
