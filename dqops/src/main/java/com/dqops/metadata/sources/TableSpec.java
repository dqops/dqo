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
package com.dqops.metadata.sources;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.statistics.table.TableStatisticsCollectorsRootCategoriesSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.parquet.Strings;

import java.util.Map;
import java.util.Objects;

/**
 * Table specification that defines data quality tests that are enabled on a table and its columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<TableSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("timestamp_columns", o -> o.timestampColumns);
            put("incremental_time_window", o -> o.incrementalTimeWindow);
			put("groupings", o -> o.groupings);
            put("table_comparisons", o -> o.tableComparisons);
            put("incident_grouping", o -> o.incidentGrouping);
			put("owner", o -> o.owner);
			put("columns", o -> o.columns);
			put("profiling_checks", o -> o.profilingChecks);
            put("monitoring_checks", o -> o.monitoringChecks);
            put("partitioned_checks", o -> o.partitionedChecks);
            put("statistics", o -> o.statistics);
            put("schedules_override", o -> o.schedulesOverride);
			put("labels", o -> o.labels);
			put("comments", o -> o.comments);
            put("file_format", o -> o.fileFormat);
        }
    };

    /**
     * Creates a default table spec.
     */
    public TableSpec() {
    }

    /**
     * Creates a table spec with a target table specification.
     * @param physicalTableName Target table specification.
     */
    public TableSpec(PhysicalTableName physicalTableName) {
        this.setPhysicalTableName(physicalTableName);
    }

    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Stage name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String stage;

    @JsonPropertyDescription("Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer priority;

    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String filter;

    @JsonPropertyDescription("Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TimestampColumnsSpec timestampColumns = new TimestampColumnsSpec();

    @JsonPropertyDescription("Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PartitionIncrementalTimeWindowSpec incrementalTimeWindow = new PartitionIncrementalTimeWindowSpec();

    @JsonPropertyDescription("The name of the default data grouping configuration that is applied on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined in the 'groupings' dictionary (indexed by the data grouping name).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String defaultGroupingName;

    @JsonPropertyDescription("Data grouping configurations list. Data grouping configurations are configured in two cases:" +
            " (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning." +
            " (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DataGroupingConfigurationSpecMap groupings = new DataGroupingConfigurationSpecMap();

    @JsonPropertyDescription("Dictionary of data comparison configurations. Data comparison configurations are used for comparisons between data sources to " +
                             "compare this table (called the compared table) with other reference tables (the source of truth). " +
                             "The reference table's metadata must be imported into DQOps, but the reference table may be located in another data source. " +
                             "DQOps will compare metrics calculated for groups of rows (using the GROUP BY clause). For each comparison, the user must specify a name of a data grouping. " +
                             "The number of data grouping dimensions in the parent table and the reference table defined in the selected data grouping configurations must match. " +
                             "DQOps will run the same data quality sensors on both the parent table (table under test) and the reference table (the source of truth), " +
                             "comparing the measures (sensor readouts) captured from both tables.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonConfigurationSpecMap tableComparisons = new TableComparisonConfigurationSpecMap();

    @JsonPropertyDescription("Incident grouping configuration with the overridden configuration at a table level. The configured field value in this object will " +
            "override the default configuration from the connection level. Incident grouping level can be changed or incident creation can be disabled.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableIncidentGroupingSpec incidentGrouping;

    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    @JsonPropertyDescription("Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMonitoringCheckCategoriesSpec monitoringChecks = new TableMonitoringCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionedCheckCategoriesSpec partitionedChecks = new TablePartitionedCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableStatisticsCollectorsRootCategoriesSpec statistics;

    @JsonPropertyDescription("Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultSchedulesSpec schedulesOverride;

    @JsonPropertyDescription("Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.")
    private ColumnSpecMap columns = new ColumnSpecMap();

    @JsonPropertyDescription("Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private LabelSetSpec labels;

    @JsonPropertyDescription("Comments used for change tracking and documenting changes directly in the table data quality specification file.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonIgnore
    private String yamlParsingError;

    @JsonPropertyDescription("File format with the specification used as a source data. It overrides the connection spec's file format when it is set")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private FileFormatSpec fileFormat;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

    /**
     * Disable quality checks and prevent it from executing on this table and it's columns.
     * @return Quality check is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Changes the disabled flag of data quality checks on this table and it's columns.
     * @param disabled When true, the tests will be disabled and will not be executed.
     */
    public void setDisabled(boolean disabled) {
		this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }

    /**
     * Returns the stage name.
     * @return Stage name.
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage name.
     * @param stage Stage name.
     */
    public void setStage(String stage) {
		setDirtyIf(!Objects.equals(this.stage, stage));
        this.stage = stage;
    }

    /**
     * Returns the table priority level for grouping of data quality issues by the importance of tables.
     * @return Table priority.
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the table priority used for grouping data quality issues per table priority.
     * @param priority New table priority.
     */
    public void setPriority(Integer priority) {
        setDirtyIf(!Objects.equals(this.priority, priority));
        this.priority = priority;
    }

    /**
     * Returns the WHERE clause filter expression.
     * @return WHERE clause filtering expression.
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets the WHERE clause expression.
     * @param filter Filtering expression.
     */
    public void setFilter(String filter) {
		setDirtyIf(!Objects.equals(this.filter, filter));
        this.filter = filter;
    }

    /**
     * Returns the configuration of timestamp columns used for timeliness data quality checks and date/time partitioned checks.
     * @return Configuration of timestamp columns.
     */
    public TimestampColumnsSpec getTimestampColumns() {
        return timestampColumns;
    }

    /**
     * Sets the configuration of timestamp columns used for timeliness checks.
     * @param timestampColumns Timestamp columns configuration.
     */
    public void setTimestampColumns(TimestampColumnsSpec timestampColumns) {
        setDirtyIf(!Objects.equals(this.timestampColumns, timestampColumns));
        this.timestampColumns = timestampColumns;
        propagateHierarchyIdToField(timestampColumns, "timestamp_columns");
    }

    /**
     * Returns the configuration of incremental time window for running partitioned data quality checks in an incremental mode.
     * @return Configuration of the incremental time window for partitioned checks.
     */
    public PartitionIncrementalTimeWindowSpec getIncrementalTimeWindow() {
        return incrementalTimeWindow;
    }

    /**
     * Sets the configuration of incremental time windows for running partitioned checks.
     * @param incrementalTimeWindow New configuration of the incremental time windows.
     */
    public void setIncrementalTimeWindow(PartitionIncrementalTimeWindowSpec incrementalTimeWindow) {
        setDirtyIf(!Objects.equals(this.incrementalTimeWindow, incrementalTimeWindow));
        this.incrementalTimeWindow = incrementalTimeWindow;
        propagateHierarchyIdToField(incrementalTimeWindow, "incremental_time_window");
    }

    /**
     * Returns the name of the default data grouping.
     * @return The name of the default data grouping.
     */
    public String getDefaultGroupingName() {
        return defaultGroupingName;
    }

    /**
     * Sets the name of the default data grouping.
     * @param defaultGroupingName The name of the default data grouping or null to disable data grouping.
     */
    public void setDefaultGroupingName(String defaultGroupingName) {
        setDirtyIf(!Objects.equals(this.defaultGroupingName, defaultGroupingName));
        this.defaultGroupingName = defaultGroupingName;
    }

    /**
     * Returns the data groupings configurations for the table.
     * @return Data groupings configurations.
     */
    public DataGroupingConfigurationSpecMap getGroupings() {
        return groupings;
    }

    /**
     * Returns the data groupings configuration for the table.
     * @param groupings Data groupings configuration.
     */
    public void setGroupings(DataGroupingConfigurationSpecMap groupings) {
		setDirtyIf(!Objects.equals(this.groupings, groupings));
        this.groupings = groupings;
		propagateHierarchyIdToField(groupings, "groupings");
    }

    /**
     * Returns the map of named comparisons to reference tables.
     * @return Dictionary of comparisons to reference tables.
     */
    public TableComparisonConfigurationSpecMap getTableComparisons() {
        return tableComparisons;
    }

    /**
     * Sets the dictionary of comparisons to reference tables.
     * @param tableComparisons Dictionary of comparisons to reference tables.
     */
    public void setTableComparisons(TableComparisonConfigurationSpecMap tableComparisons) {
        setDirtyIf(!Objects.equals(this.tableComparisons, tableComparisons));
        this.tableComparisons = tableComparisons;
        propagateHierarchyIdToField(tableComparisons, "table_comparisons");
    }

    /**
     * Returns the configuration of incident grouping at a table level.
     * @return Incident grouping configuration at a table level.
     */
    public TableIncidentGroupingSpec getIncidentGrouping() {
        return incidentGrouping;
    }

    /**
     * Changes the incident grouping configuration at a table level.
     * @param incidentGrouping Incident grouping configuration.
     */
    public void setIncidentGrouping(TableIncidentGroupingSpec incidentGrouping) {
        setDirtyIf(!Objects.equals(this.incidentGrouping, incidentGrouping));
        this.incidentGrouping = incidentGrouping;
        propagateHierarchyIdToField(incidentGrouping, "incident_grouping");
    }

    /**
     * Returns the table owner information.
     * @return Table owner information.
     */
    public TableOwnerSpec getOwner() {
        return owner;
    }

    /**
     * Sets the table owner information.
     * @param owner Table owner information.
     */
    public void setOwner(TableOwnerSpec owner) {
		setDirtyIf(!Objects.equals(this.owner, owner));
        this.owner = owner;
		propagateHierarchyIdToField(owner, "owner");
    }

    /**
     * Returns configuration of enabled table level profiling data quality checks.
     * @return Table level profiling data quality checks.
     */
    public TableProfilingCheckCategoriesSpec getProfilingChecks() {
        return profilingChecks;
    }

    /**
     * Sets a new configuration of table level profiling data quality checks.
     * @param profilingChecks New profiling checks configuration.
     */
    public void setProfilingChecks(TableProfilingCheckCategoriesSpec profilingChecks) {
		setDirtyIf(!Objects.equals(this.profilingChecks, profilingChecks));
        this.profilingChecks = profilingChecks;
		propagateHierarchyIdToField(profilingChecks, "profiling_checks");
    }

    /**
     * Returns configuration of enabled table level monitoring.
     * @return Table level monitoring.
     */
    public TableMonitoringCheckCategoriesSpec getMonitoringChecks() {
        return monitoringChecks;
    }

    /**
     * Sets a new configuration of table level data quality monitoring checks.
     * @param monitoringChecks New monitoring checks configuration.
     */
    public void setMonitoringChecks(TableMonitoringCheckCategoriesSpec monitoringChecks) {
        setDirtyIf(!Objects.equals(this.monitoringChecks, monitoringChecks));
        this.monitoringChecks = monitoringChecks;
        propagateHierarchyIdToField(monitoringChecks, "monitoring_checks");
    }

    /**
     * Returns configuration of enabled table level date/time partitioned checks.
     * @return Table level date/time partitioned checks.
     */
    public TablePartitionedCheckCategoriesSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of table level date/time partitioned data quality checks.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(TablePartitionedCheckCategoriesSpec partitionedChecks) {
        setDirtyIf(!Objects.equals(this.partitionedChecks, partitionedChecks));
        this.partitionedChecks = partitionedChecks;
        propagateHierarchyIdToField(partitionedChecks, "partitioned_checks");
    }

    /**
     * Returns a configuration of the table statistics collector (if any changes were applied).
     * @return Configuration of the table level statistics collector.
     */
    public TableStatisticsCollectorsRootCategoriesSpec getStatistics() {
        return statistics;
    }

    /**
     * Sets a new configuration of a table level statistics collector.
     * @param statistics Table level statistics collector.
     */
    public void setStatistics(TableStatisticsCollectorsRootCategoriesSpec statistics) {
        setDirtyIf(!Objects.equals(this.statistics, statistics));
        this.statistics = statistics;
        propagateHierarchyIdToField(statistics, "statistics");
    }

    /**
     * Returns the table specific configuration of schedules for each type of checks that have a separate schedule.
     * @return Configuration of schedules for each type of schedules.
     */
    public DefaultSchedulesSpec getSchedulesOverride() {
        return schedulesOverride;
    }

    /**
     * Sets the table specific configuration of schedules for running checks.
     * @param schedulesOverride Configuration of schedules for running checks.
     */
    public void setSchedulesOverride(DefaultSchedulesSpec schedulesOverride) {
        setDirtyIf(!Objects.equals(this.schedulesOverride, schedulesOverride));
        this.schedulesOverride = schedulesOverride;
        propagateHierarchyIdToField(schedulesOverride, "schedules_override");
    }

    /**
     * Returns a hashtable of columns, indexed by the column name.
     * @return Dictionary of columns.
     */
    public ColumnSpecMap getColumns() {
        return columns;
    }

    /**
     * Sets a new collection of columns.
     * @param columns New dictionary of columns.
     */
    public void setColumns(ColumnSpecMap columns) {
		setDirtyIf(!Objects.equals(this.columns, columns));
        this.columns = columns;
		propagateHierarchyIdToField(columns, "columns");
    }

    /**
     * List of labels assigned to a table. Labels are used for targeting the execution of tests.
     * @return Labels collection.
     */
    public LabelSetSpec getLabels() {
        return labels;
    }

    /**
     * Changes a list of labels.
     * @param labels Labels collection.
     */
    public void setLabels(LabelSetSpec labels) {
		setDirtyIf(!Objects.equals(this.labels, labels));
        this.labels = labels;
		propagateHierarchyIdToField(labels, "labels");
    }

    /**
     * Returns a list of comments (change tracking).
     * @return Collection of comments.
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a new list of comments.
     * @param comments List of comments.
     */
    public void setComments(CommentsListSpec comments) {
		setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
		propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Returns a file format.
     * @return A file format.
     */
    public FileFormatSpec getFileFormat() {
        return fileFormat;
    }

    /**
     * Sets a new file format.
     * @param fileFormat A file format.
     */
    public void setFileFormat(FileFormatSpec fileFormat) {
        setDirtyIf(!Objects.equals(this.fileFormat, fileFormat));
        this.fileFormat = fileFormat;
        propagateHierarchyIdToField(fileFormat, "file_format");
    }

    /**
     * Merges (imports) source columns from a different table spec.
     * @param sourceTableSpec Source table spec.
     */
    public void mergeColumnsFrom(TableSpec sourceTableSpec) {
        for (Map.Entry<String, ColumnSpec> sourceColumnEntry : sourceTableSpec.getColumns().entrySet()) {
            ColumnSpec existingColumnSpec = this.getColumns().get(sourceColumnEntry.getKey());
            if (existingColumnSpec == null) {
				this.getColumns().put(sourceColumnEntry.getKey(), sourceColumnEntry.getValue());
            } else {
                existingColumnSpec.setTypeSnapshot(sourceColumnEntry.getValue().getTypeSnapshot());
                // quality checks are not modified for existing columns, only the type snapshot
            }
        }
    }

    /**
     * Retrieves the data grouping configuration that is selected as the default data grouping or returns null when no data grouping configuration
     * is selected or the selected data grouping configuration is not found in the 'groupings' dictionary.
     * @return The default data grouping configuration specification or null, when no grouping should be applied.
     */
    @JsonIgnore
    public DataGroupingConfigurationSpec getDefaultDataGroupingConfiguration() {
        if (Strings.isNullOrEmpty(this.defaultGroupingName) || this.groupings == null) {
            return null;
        }

        return this.groupings.get(this.defaultGroupingName);
    }

    /**
     * Sets the default data grouping configuration or updates the existing default grouping configuration.
     * @param dataGroupingConfigurationSpec Data grouping configuration.
     */
    @JsonIgnore
    public void setDefaultDataGroupingConfiguration(DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
        if (dataGroupingConfigurationSpec != null) {
            if (Strings.isNullOrEmpty(this.defaultGroupingName)) {
                this.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
                this.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);
            } else {
                this.getGroupings().put(this.defaultGroupingName, dataGroupingConfigurationSpec);
            }
        } else {
            if (this.defaultGroupingName != null) {
                this.getGroupings().remove(this.defaultGroupingName);
                this.setDefaultGroupingName(null);
            }
        }
    }

    /**
     * Retrieves a non-null root check container for the requested category.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the table specification.
     * @param checkType Check type.
     * @param checkTimeScale Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the table specification.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getTableCheckRootContainer(CheckType checkType,
                                                                      CheckTimeScale checkTimeScale,
                                                                      boolean attachCheckContainer) {
        return getTableCheckRootContainer(checkType, checkTimeScale, attachCheckContainer, true);
    }

    /**
     * Retrieves a non-null root check container for the requested category. Returns null when the check container is not present.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the table specification.
     * @param checkType Check type.
     * @param checkTimeScale Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the table specification.
     * @param createEmptyContainerWhenNull Creates a new check container instance when it is null.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getTableCheckRootContainer(CheckType checkType,
                                                                      CheckTimeScale checkTimeScale,
                                                                      boolean attachCheckContainer,
                                                                      boolean createEmptyContainerWhenNull) {

        switch (checkType) {
            case profiling: {
                if (this.profilingChecks != null) {
                    return this.profilingChecks;
                }

                if (!createEmptyContainerWhenNull) {
                    return null;
                }

                TableProfilingCheckCategoriesSpec tableProfilingCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
                tableProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "profiling_checks"));
                if (attachCheckContainer) {
                    this.profilingChecks = tableProfilingCheckCategoriesSpec;
                }
                return tableProfilingCheckCategoriesSpec;
            }

            case monitoring: {
                TableMonitoringCheckCategoriesSpec monitoringSpec = this.monitoringChecks;
                if (monitoringSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    monitoringSpec = new TableMonitoringCheckCategoriesSpec();
                    monitoringSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "monitoring_checks"));
                    if (attachCheckContainer) {
                        this.monitoringChecks = monitoringSpec;
                    }
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (monitoringSpec.getDaily() != null) {
                            return monitoringSpec.getDaily();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableDailyMonitoringCheckCategoriesSpec dailyMonitoringCategoriesSpec = new TableDailyMonitoringCheckCategoriesSpec();
                        dailyMonitoringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(monitoringSpec.getHierarchyId(), "daily"));
                        if (attachCheckContainer) {
                            monitoringSpec.setDaily(dailyMonitoringCategoriesSpec);
                        }
                        return dailyMonitoringCategoriesSpec;
                    }
                    case monthly: {
                        if (monitoringSpec.getMonthly() != null) {
                            return monitoringSpec.getMonthly();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableMonthlyMonitoringCheckCategoriesSpec monthlyMonitoringCategoriesSpec = new TableMonthlyMonitoringCheckCategoriesSpec();
                        monthlyMonitoringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(monitoringSpec.getHierarchyId(), "monthly"));
                        if (attachCheckContainer) {
                            monitoringSpec.setMonthly(monthlyMonitoringCategoriesSpec);
                        }
                        return monthlyMonitoringCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            case partitioned: {
                TablePartitionedCheckCategoriesSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    partitionedChecksSpec = new TablePartitionedCheckCategoriesSpec();
                    partitionedChecksSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "partitioned_checks"));
                    if (attachCheckContainer) {
                        this.partitionedChecks = partitionedChecksSpec;
                    }
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (partitionedChecksSpec.getDaily() != null) {
                            return partitionedChecksSpec.getDaily();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCategoriesSpec = new TableDailyPartitionedCheckCategoriesSpec();
                        dailyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "daily"));
                        if (attachCheckContainer) {
                            partitionedChecksSpec.setDaily(dailyPartitionedCategoriesSpec);
                        }
                        return dailyPartitionedCategoriesSpec;
                    }
                    case monthly: {
                        if (partitionedChecksSpec.getMonthly() != null) {
                            return partitionedChecksSpec.getMonthly();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCategoriesSpec = new TableMonthlyPartitionedCheckCategoriesSpec();
                        monthlyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "monthly"));
                        if (attachCheckContainer) {
                            partitionedChecksSpec.setMonthly(monthlyPartitionedCategoriesSpec);
                        }
                        return monthlyPartitionedCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            default: {
                throw new IllegalArgumentException("Unsupported check type");
            }
        }
    }

    /**
     * Sets the given container of checks at a proper level of the check hierarchy.
     * The object can be a profiling check container, one of monitoring check containers or one of partitioned check containers.
     * @param checkRootContainer Root check container to store.
     */
    @JsonIgnore
    public void setTableCheckRootContainer(AbstractRootChecksContainerSpec checkRootContainer) {
        if (checkRootContainer == null) {
            throw new NullPointerException("Root check container cannot be null");
        }

        if (checkRootContainer instanceof TableProfilingCheckCategoriesSpec) {
            this.setProfilingChecks((TableProfilingCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableDailyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new TableMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setDaily((TableDailyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new TableMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setMonthly((TableMonthlyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setDaily((TableDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setMonthly((TableMonthlyPartitionedCheckCategoriesSpec)checkRootContainer);
        } else {
            throw new IllegalArgumentException("Unsupported check root container type " + checkRootContainer.getClass().getCanonicalName());
        }
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Inspects all check containers and verifies if any of them has any checks configured.
     * @return True when the table has some table level checks (not column level), false when no table level checks were found.
     */
    public boolean hasAnyChecksConfigured() {
        if (this.profilingChecks != null && this.profilingChecks.hasAnyConfiguredChecks()) {
            return true;
        }

        if (this.monitoringChecks != null && this.monitoringChecks.hasAnyConfiguredChecks()) {
            return true;
        }

        if (this.partitionedChecks != null && this.partitionedChecks.hasAnyConfiguredChecks()) {
            return true;
        }

        return false;
    }

    /**
     * Inspects all check containers and verifies if any of them has any checks configured for a given check type.
     * @param checkType Check type.
     * @return True when the table has some table level checks (not column level), false when no table level checks were found.
     */
    public boolean hasAnyChecksConfigured(CheckType checkType) {
        switch (checkType) {
            case profiling:
                return this.profilingChecks != null && this.profilingChecks.hasAnyConfiguredChecks();

            case monitoring:
                return this.monitoringChecks != null && this.monitoringChecks.hasAnyConfiguredChecks();

            case partitioned:
                return this.partitionedChecks != null && this.partitionedChecks.hasAnyConfiguredChecks();
        }

        return false;
    }

    /**
     * Creates an expanded and trimmed (no checks for columns, no comments) deep copy of the table.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Cloned, trimmed and expanded table specification.
     */
    public TableSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        try {
            TableSpec cloned = (TableSpec) this.clone();
            cloned.profilingChecks = null;
            cloned.monitoringChecks = null;
            cloned.partitionedChecks = null;
            cloned.labels = null;
            cloned.owner = null;
            cloned.comments = null;
            cloned.statistics = null;
            cloned.tableComparisons = null;
            if (cloned.timestampColumns != null) {
                cloned.timestampColumns = cloned.timestampColumns.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.incrementalTimeWindow != null) {
                cloned.incrementalTimeWindow = cloned.incrementalTimeWindow.deepClone();
            }
            if (cloned.groupings != null) {
                cloned.groupings = cloned.groupings.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.incidentGrouping != null) {
                cloned.incidentGrouping = cloned.incidentGrouping.expandAndTrim(secretValueProvider);
            }
            cloned.columns = this.columns.expandAndTrim(secretValueProvider, secretValueLookupContext);
            if(cloned.fileFormat != null){
                cloned.fileFormat = cloned.fileFormat.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            return this;
        }
    }

    /**
     * Creates a cloned and trimmed version of the object without unwanted properties. Only the "target" is preserved and deeply cloned.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public TableSpec trim() {
        try {
            TableSpec cloned = (TableSpec) this.clone();
            if (cloned.timestampColumns != null) {
                cloned.timestampColumns = cloned.timestampColumns.deepClone();
            }
            if (cloned.incrementalTimeWindow != null) {
                cloned.incrementalTimeWindow = cloned.incrementalTimeWindow.deepClone();
            }
            cloned.profilingChecks = null;
            cloned.monitoringChecks = null;
            cloned.partitionedChecks = null;
            cloned.owner = null;
            cloned.groupings = null;
            cloned.tableComparisons = null;
            cloned.labels = null;
            cloned.comments = null;
            cloned.statistics = null;
            cloned.incidentGrouping = null;
            cloned.columns = this.columns.trim();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a cloned copy that has no collection nodes like checks or columns.
     * A bare clone is returned by the rest api.
     * @return Bare clone of the object.
     */
    public TableSpec cloneBare() {
        try {
            TableSpec cloned = (TableSpec) this.clone();
            cloned.profilingChecks = null;
            cloned.monitoringChecks = null;
            cloned.partitionedChecks = null;
            cloned.owner = null;
            cloned.timestampColumns = null;
            cloned.incrementalTimeWindow = null;
            cloned.groupings = null;
            cloned.tableComparisons = null;
            cloned.labels = null;
            cloned.comments = null;
            cloned.fileFormat = null;
            cloned.columns = null;
            cloned.statistics = null;
            cloned.incidentGrouping = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Retrieves the physical table name from the HierarchyId.
     * @return Physical table name, retrieved from the hierarchy id.
     */
    @JsonIgnore
    public PhysicalTableName getPhysicalTableName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        return hierarchyId.getPhysicalTableName();
    }

    /**
     * Stores a physical table name in a temporary hierarchy id, using a fake connection name.
     * This method can be called only for a new table specification that is not yet attached to a parent node.
     * @param physicalTableName Physical table name to store.
     */
    @JsonIgnore
    public void setPhysicalTableName(PhysicalTableName physicalTableName) {
        if (this.getHierarchyId() != null) {
            throw new DqoRuntimeException("Cannot assign a temporary physical table name in the hierarchy id when the hierarchy ID was already created");
        }

        HierarchyId hierarchyId = HierarchyId.makeHierarchyIdForTable("unknown", physicalTableName);
        this.setHierarchyId(hierarchyId);
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return super.toString();
        }

        return hierarchyId.getConnectionName() + "." + hierarchyId.getPhysicalTableName().toString();
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public TableSpec deepClone() {
        return (TableSpec)super.deepClone();
    }

    public static class TableSpecSampleFactory implements SampleValueFactory<TableSpec> {
        @Override
        public TableSpec createSample() {
            String schemaTableName = SampleStringsRegistry.getSchemaTableName();
            return new TableSpec() {{
                setDisabled(false);
                setTimestampColumns(new TimestampColumnsSpec.TimestampColumnsSpecSampleFactory().createSample());
                setProfilingChecks(new TableProfilingCheckCategoriesSpec.TableProfilingCheckCategoriesSpecSampleFactory().createSample());
                setPhysicalTableName(PhysicalTableName.fromSchemaTableFilter(schemaTableName));
                setIncrementalTimeWindow(new PartitionIncrementalTimeWindowSpec.PartitionIncrementalTimeWindowSpecSampleFactory().createSample());
            }};
        }
    }
}
