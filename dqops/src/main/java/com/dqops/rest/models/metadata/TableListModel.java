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
import com.dqops.checks.ProfilingTimePeriodTruncation;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableOwnerSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.Map;

/**
 * Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableListModel", description = "Table list model with a subset of parameters, excluding all nested objects.")
public class TableListModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Table hash that identifies the table using a unique hash code.
     */
    @JsonPropertyDescription("Table hash that identifies the table using a unique hash code.")
    private Long tableHash;

    /**
     * Physical table details (a physical schema name and a physical table name).
     */
    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name).")
    private PhysicalTableName target;

    /**
     * List of labels applied to the table.
     */
    @JsonPropertyDescription("List of labels applied to the table.")
    private String[] labels;

    /**
     * Disables all data quality checks on the table. Data quality checks will not be executed.
     */
    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    /**
     * Stage name.
     */
    @JsonPropertyDescription("Stage name.")
    private String stage;

    /**
     * SQL WHERE clause added to the sensor queries.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries.")
    private String filter;

    /**
     * Disable automatic collection of error samples in the profiling section. The profiling checks by default always collect error samples for failed data quality checks.
     */
    @JsonPropertyDescription("Disable automatic collection of error samples in the profiling section. The profiling checks by default always collect error samples for failed data quality checks.")
    private boolean doNotCollectErrorSamplesInProfiling;

    /**
     * Always collect error samples for failed monitoring checks. DQOps will not collect error samples automatically when the checks are executed by a scheduler or by running checks from the metadata tree. Error samples are always collected only when the checks are run from the check editor.
     */
    @JsonPropertyDescription("Always collect error samples for failed monitoring checks. DQOps will not collect error samples automatically when the checks are executed by a scheduler or by running checks from the metadata tree. Error samples are always collected only when the checks are run from the check editor.")
    private boolean alwaysCollectErrorSamplesInMonitoring;

    /**
     * Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.
     */
    @JsonPropertyDescription("Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer priority;

    /**
     * Table owner information like the data steward name or the business application name."
     */
    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    /**
     * Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent
     * profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.
     */
    @JsonPropertyDescription("Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent " +
            "profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfilingTimePeriodTruncation profilingChecksResultTruncation;

    /**
     * File format for a file based table, such as a CSV or Parquet file.
     */
    @JsonPropertyDescription("File format for a file based table, such as a CSV or Parquet file.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private FileFormatSpec fileFormat;

    /**
     * The current data quality status for the table, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache.
     * In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the table.
     */
    @JsonPropertyDescription("The current data quality status for the table, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache. " +
            "In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the table.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TableCurrentDataQualityStatusModel dataQualityStatus;

    /**
     * True when the table has any checks configured.
     */
    @JsonPropertyDescription("True when the table has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    /**
     * True when the table has any profiling checks configured.
     */
    @JsonPropertyDescription("True when the table has any profiling checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredProfilingChecks;

    /**
     * True when the table has any monitoring checks configured.
     */
    @JsonPropertyDescription("True when the table has any monitoring checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredMonitoringChecks;

    /**
     * True when the table has any partition checks configured.
     */
    @JsonPropertyDescription("True when the table has any partition checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredPartitionChecks;

    /**
     * True when the table has any partition checks configured, but the partitionByColumn is not set, so the partition checks will fail when started.
     */
    @JsonPropertyDescription("True when the table has missing configuration of the \"partition_by_column\" column, making any partition checks fail when executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean partitioningConfigurationMissing;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this table.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this table.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run monitoring checks within this table.")
    private CheckSearchFilters runMonitoringChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this table.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this table.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.
     */
    @JsonPropertyDescription("A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.")
    private Map<String, String> advancedProperties;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
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
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * This model is used for the table list screen and it has even less fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @param isEditor       The current user has the editor permission.
     * @param isOperator     The current user has the operator permission.
     * @return Basic table model.
     */
    public static TableListModel fromTableSpecificationForListEntry(
            String connectionName,
            TableSpec tableSpec,
            boolean isEditor,
            boolean isOperator) {
        return new TableListModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getPhysicalTableName());
            setLabels(tableSpec.getLabels() != null ? tableSpec.getLabels().toArray(String[]::new) : null);
            setDisabled(tableSpec.isDisabled());
            setDoNotCollectErrorSamplesInProfiling(tableSpec.isDoNotCollectErrorSamplesInProfiling());
            setAlwaysCollectErrorSamplesInMonitoring(tableSpec.isAlwaysCollectErrorSamplesInMonitoring());
            setProfilingChecksResultTruncation(tableSpec.getProfilingChecks() != null ? tableSpec.getProfilingChecks().getResultTruncation() : null);
            setPartitioningConfigurationMissing(tableSpec.getTimestampColumns() == null ||
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn()));
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(tableSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(tableSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(tableSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
            setYamlParsingError(tableSpec.getYamlParsingError());
            setStage(tableSpec.getStage());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
            setAdvancedProperties(tableSpec.getAdvancedProperties());
        }};
    }

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @param isEditor       The current user has the editor permission.
     * @param isOperator     The current user has the operator permission.
     * @return Basic table model.
     */
    public static TableListModel fromTableSpecification(
            String connectionName,
            TableSpec tableSpec,
            boolean isEditor,
            boolean isOperator) {
        return new TableListModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getPhysicalTableName());
            setDisabled(tableSpec.isDisabled());
            setStage(tableSpec.getStage());
            setFilter(tableSpec.getFilter());
            setPriority(tableSpec.getPriority());
            setOwner(tableSpec.getOwner());
            setFileFormat(tableSpec.getFileFormat());
            setDoNotCollectErrorSamplesInProfiling(tableSpec.isDoNotCollectErrorSamplesInProfiling());
            setAlwaysCollectErrorSamplesInMonitoring(tableSpec.isAlwaysCollectErrorSamplesInMonitoring());
            setProfilingChecksResultTruncation(tableSpec.getProfilingChecks() != null ? tableSpec.getProfilingChecks().getResultTruncation() : null);
            setPartitioningConfigurationMissing(tableSpec.getTimestampColumns() == null ||
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn()));
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(tableSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(tableSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(tableSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
            setYamlParsingError(tableSpec.getYamlParsingError());

            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnection(connectionName);
                setFullTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());

                setDateStart(null);
                setDateEnd(null);

                setDeleteStatistics(true);
                setDeleteErrors(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrorSamples(true);
                setDeleteIncidents(true);
            }});
            setAdvancedProperties(tableSpec.getAdvancedProperties());
        }};
    }

    /**
     * Updates a table specification by copying all fields. The {@see TableSpec#target field is not updated}.
     * @param targetTableSpec Target table specification to update.
     */
    public void copyToTableSpecification(TableSpec targetTableSpec) {
        targetTableSpec.setDisabled(this.isDisabled());
        targetTableSpec.setStage(this.getStage());
        targetTableSpec.setFilter(this.getFilter());
        targetTableSpec.setDoNotCollectErrorSamplesInProfiling(targetTableSpec.isDoNotCollectErrorSamplesInProfiling());
        targetTableSpec.setAlwaysCollectErrorSamplesInMonitoring(targetTableSpec.isAlwaysCollectErrorSamplesInMonitoring());
        targetTableSpec.setPriority(this.getPriority());
        targetTableSpec.setOwner(this.getOwner());
        targetTableSpec.setFileFormat(this.getFileFormat());

        if (targetTableSpec.getProfilingChecks() == null) {
            targetTableSpec.setProfilingChecks(new TableProfilingCheckCategoriesSpec());
        }
        targetTableSpec.getProfilingChecks().setResultTruncation(this.profilingChecksResultTruncation);
        targetTableSpec.setAdvancedProperties(this.getAdvancedProperties());
    }

    public static class TableListModelSampleFactory implements SampleValueFactory<TableListModel> {
        @Override
        public TableListModel createSample() {
            return fromTableSpecification(
                    SampleStringsRegistry.getConnectionName(),
                    new TableSpec.TableSpecSampleFactory().createSample(),
                    true,
                    true
            );
        }
    }
}
