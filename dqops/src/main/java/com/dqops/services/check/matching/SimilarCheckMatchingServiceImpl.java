/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.matching;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
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
    private final SpecToModelCheckMappingService specToModelCheckMappingService;
    private final DqoHomeContextFactory dqoHomeContextFactory;
    private final SimilarCheckGroupingKeyFactory similarCheckGroupingKeyFactory;

    /**
     * Creates a service given dependencies.
     *
     * @param specToModelCheckMappingService Check specification to check model mapping service.
     * @param dqoHomeContextFactory          Returns the default DQOps home context.
     * @param similarCheckGroupingKeyFactory Creates a key by which to separate checks into similar check groups.
     */
    @Autowired
    public SimilarCheckMatchingServiceImpl(SpecToModelCheckMappingService specToModelCheckMappingService,
                                           DqoHomeContextFactory dqoHomeContextFactory,
                                           SimilarCheckGroupingKeyFactory similarCheckGroupingKeyFactory) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.similarCheckGroupingKeyFactory = similarCheckGroupingKeyFactory;
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

        tableSpec.getTableComparisons().put("comparison_to_source_of_truth_profiling",
                new TableComparisonConfigurationSpec() {{
                    setCheckType(CheckType.profiling);
                }});
        tableSpec.getTableComparisons().put("comparison_to_source_of_truth_monitoring_daily",
                new TableComparisonConfigurationSpec() {{
                    setCheckType(CheckType.monitoring);
                    setTimeScale(CheckTimeScale.daily);
                }});
        tableSpec.getTableComparisons().put("comparison_to_source_of_truth_monitoring_monthly",
                new TableComparisonConfigurationSpec() {{
                    setCheckType(CheckType.monitoring);
                    setTimeScale(CheckTimeScale.monthly);
                }});
        tableSpec.getTableComparisons().put("comparison_to_source_of_truth_partitioned_daily",
                new TableComparisonConfigurationSpec() {{
                    setCheckType(CheckType.partitioned);
                    setTimeScale(CheckTimeScale.daily);
                }});
        tableSpec.getTableComparisons().put("comparison_to_source_of_truth_partitioned_monthly",
                new TableComparisonConfigurationSpec() {{
                    setCheckType(CheckType.partitioned);
                    setTimeScale(CheckTimeScale.monthly);
                }});

        return tableSpec;
    }

    /**
     * Find similar checks in all check types for a table.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarTableChecks() {
        UserHomeImpl userHome = new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(false);

        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on the connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        ExecutionContext executionContext = new ExecutionContext(null, this.dqoHomeContextFactory.openLocalDqoHome());

        TableProfilingCheckCategoriesSpec profilingChecks = tableSpec.getProfilingChecks();
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(profilingChecks,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = Objects.requireNonNullElseGet(tableSpec.getMonitoringChecks().getDaily(), TableDailyMonitoringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(dailyMonitoring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                dailyMonitoring.getCheckTarget(), dailyMonitoring.getCheckType(), dailyMonitoring.getCheckTimeScale());

        TableMonthlyMonitoringCheckCategoriesSpec monthlyMonitoring = Objects.requireNonNullElseGet(tableSpec.getMonitoringChecks().getMonthly(), TableMonthlyMonitoringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(monthlyMonitoring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                monthlyMonitoring.getCheckTarget(), monthlyMonitoring.getCheckType(), monthlyMonitoring.getCheckTimeScale());

        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getDaily(), TableDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(tableSpec.getPartitionedChecks().getMonthly(), TableMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                monthlyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }

    /**
     * Find similar checks in all check types for a column.
     * @return List of similar checks.
     */
    @Override
    public SimilarChecksContainer findSimilarColumnChecks() {
        UserHomeImpl userHome = new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(true);
        tableWrapper.setSpec(tableSpec);
        ColumnSpec columnSpec = tableSpec.getColumns().getAt(0);

        SimilarChecksContainer similarChecksContainer = new SimilarChecksContainer();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setEnabled(true);
            // TODO: we could add additional filters on the connection name and table name, extracted from the hierarchyId in the tableSpec
        }};

        ExecutionContext executionContext = new ExecutionContext(null, this.dqoHomeContextFactory.openLocalDqoHome());

        ColumnProfilingCheckCategoriesSpec profilingChecks = Objects.requireNonNullElseGet(columnSpec.getProfilingChecks(), ColumnProfilingCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(profilingChecks,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                profilingChecks.getCheckTarget(), profilingChecks.getCheckType(), profilingChecks.getCheckTimeScale());

        ColumnMonitoringCheckCategoriesSpec monitoring = Objects.requireNonNullElseGet(columnSpec.getMonitoringChecks(), ColumnMonitoringCheckCategoriesSpec::new);
        ColumnDailyMonitoringCheckCategoriesSpec dailyMonitoring = Objects.requireNonNullElseGet(monitoring.getDaily(), ColumnDailyMonitoringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(dailyMonitoring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                dailyMonitoring.getCheckTarget(), dailyMonitoring.getCheckType(), dailyMonitoring.getCheckTimeScale());

        ColumnMonthlyMonitoringCheckCategoriesSpec monthlyMonitoring = Objects.requireNonNullElseGet(monitoring.getMonthly(), ColumnMonthlyMonitoringCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(monthlyMonitoring,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                monthlyMonitoring.getCheckTarget(), monthlyMonitoring.getCheckType(), monthlyMonitoring.getCheckTimeScale());

        ColumnPartitionedCheckCategoriesSpec partitionedChecks = Objects.requireNonNullElseGet(columnSpec.getPartitionedChecks(), ColumnPartitionedCheckCategoriesSpec::new);
        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getDaily(), ColumnDailyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(dailyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                dailyPartitioned.getCheckTarget(), dailyPartitioned.getCheckType(), dailyPartitioned.getCheckTimeScale());

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = Objects.requireNonNullElseGet(partitionedChecks.getMonthly(), ColumnMonthlyPartitionedCheckCategoriesSpec::new);
        similarChecksContainer.appendAllChecks(this.similarCheckGroupingKeyFactory,
                this.specToModelCheckMappingService.createModel(monthlyPartitioned,
                        checkSearchFilters, null, tableSpec, executionContext, ProviderType.bigquery, true),
                dailyPartitioned.getCheckTarget(), monthlyPartitioned.getCheckType(), monthlyPartitioned.getCheckTimeScale());

        return similarChecksContainer;
    }
}
