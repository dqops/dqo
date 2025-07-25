/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.metadata;

import com.dqops.checks.CheckType;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.data.checkresults.models.currentstatus.ColumnCurrentDataQualityStatusModel;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnListModel", description = "Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.")
public class ColumnListModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Physical table name including the schema and table names.
     */
    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    /**
     * Column name.
     */
    @JsonPropertyDescription("Column name.")
    private String columnName;

    /**
     * List of labels applied to the table.
     */
    @JsonPropertyDescription("List of labels applied to the table.")
    private String[] labels;

    /**
     * SQL expression for a calculated column, or a column that applies additional data transformation to the original column value. The original column value is referenced by a token {column}.
     */
    @JsonPropertyDescription("SQL expression for a calculated column, or a column that applies additional data transformation to the original column value. The original column value is referenced by a token {column}.")
    private String sqlExpression;

    /**
     * Column hash that identifies the column using a unique hash code.
     */
    @JsonPropertyDescription("Column hash that identifies the column using a unique hash code.")
    private Long columnHash;

    /**
     * Disables all data quality checks on the column. Data quality checks will not be executed.
     */
    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    /**
     * Marks columns that are part of a primary or a unique key. DQOps captures their values during error sampling to match invalid values to the rows in which the value was found.
     */
    @JsonPropertyDescription("Marks columns that are part of a primary or a unique key. DQOps captures their values during error sampling to match invalid values to the rows in which the value was found.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean id;

    /**
     * True when the column has any checks configured (read-only).
     */
    @JsonPropertyDescription("True when the column has any checks configured (read-only).")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    /**
     * True when the column has any profiling checks configured (read-only).
     */
    @JsonPropertyDescription("True when the column has any profiling checks configured (read-only).")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredProfilingChecks;

    /**
     * True when the column has any monitoring checks configured (read-only).
     */
    @JsonPropertyDescription("True when the column has any monitoring checks configured (read-only).")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredMonitoringChecks;

    /**
     * True when the column has any partition checks configured (read-only).
     */
    @JsonPropertyDescription("True when the column has any partition checks configured (read-only).")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredPartitionChecks;

    /**
     * Column data type that was retrieved when the table metadata was imported.
     */
    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    /**
     * The current data quality status for the column, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache.
     * In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the column.
     */
    @JsonPropertyDescription("The current data quality status for the column, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache. " +
            "In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the column.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnCurrentDataQualityStatusModel dataQualityStatus;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this column.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this column.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run monitoring checks within this column.")
    private CheckSearchFilters runMonitoringChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this column.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collector within this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collector within this column.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.
     */
    @JsonPropertyDescription("A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.")
    private Map<String, String> advancedProperties;

    /**
     * Boolean flag that decides if the current user can update or delete the column.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the column.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can collect statistics.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can collect statistics.")
    private boolean canCollectStatistics;

    /**
     * Boolean flag that decides if the current user can run checks.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can run checks.")
    private boolean canRunChecks;

    /**
     * Boolean flag that decides if the current user can delete data (results).
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can delete data (results).")
    private boolean canDeleteData;

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * This model is used for the column list screen and it has even less fields.
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @param isEditor          The current user has the editor permission.
     * @param isOperator        The current user has the operator permission.
     * @return Basic column model.
     */
    public static ColumnListModel fromColumnSpecificationForListEntry(String connectionName,
                                                                      PhysicalTableName physicalTableName,
                                                                      String columnName,
                                                                      ColumnSpec columnSpec,
                                                                      boolean isEditor,
                                                                      boolean isOperator) {
        return new ColumnListModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setLabels(columnSpec.getLabels() != null ? columnSpec.getLabels().toArray(String[]::new) : null);
            setSqlExpression(columnSpec.getSqlExpression());
            setDisabled(columnSpec.isDisabled());
            setId(columnSpec.isId());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(columnSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(columnSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);

            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setColumn(columnName);
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setColumn(columnName);
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setColumn(columnName);
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setColumn(columnName);
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                getColumnNames().add(columnName);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                setColumnNames(List.of(columnName));

                setDateStart(null);
                setDateEnd(null);

                setDeleteStatistics(true);
                setDeleteErrors(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrorSamples(true);
                setDeleteIncidents(true);
            }});
            setAdvancedProperties(columnSpec.getAdvancedProperties());
        }};
    }

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * @param connectionName    Connection name to store in the model.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @param isEditor          The current user has the editor permission.
     * @param isOperator        The current user has the operator permission.
     * @return Basic column model.
     */
    public static ColumnListModel fromColumnSpecification(String connectionName,
                                                          PhysicalTableName physicalTableName,
                                                          String columnName,
                                                          ColumnSpec columnSpec,
                                                          boolean isEditor,
                                                          boolean isOperator) {
        return new ColumnListModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setSqlExpression(columnSpec.getSqlExpression());
            setDisabled(columnSpec.isDisabled());
            setId(columnSpec.isId());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(columnSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(columnSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
            setAdvancedProperties(columnSpec.getAdvancedProperties());
        }};
    }

    /**
     * Updates a column specification by copying all fields.
     * @param targetColumnSpec Target column specification to update.
     */
    public void copyToColumnSpecification(ColumnSpec targetColumnSpec) {
        targetColumnSpec.setSqlExpression(this.getSqlExpression());
        targetColumnSpec.setDisabled(this.isDisabled());
        targetColumnSpec.setId(this.isId());
        targetColumnSpec.setTypeSnapshot(this.getTypeSnapshot());
        targetColumnSpec.setAdvancedProperties(this.getAdvancedProperties());
    }

    public static class ColumnListModelSampleFactory implements SampleValueFactory<ColumnListModel> {
        @Override
        public ColumnListModel createSample() {
            ColumnSpec columnSpec = new ColumnSpec.ColumnSpecSampleFactory().createSample();
            return fromColumnSpecification(SampleStringsRegistry.getConnectionName(),
                    new PhysicalTableName(SampleStringsRegistry.getSchemaName(), SampleStringsRegistry.getTableName()),
                    SampleStringsRegistry.getColumnName(),
                    columnSpec,
                    false,
                    true);
        }
    }
}
