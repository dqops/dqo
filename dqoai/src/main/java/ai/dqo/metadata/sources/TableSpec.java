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
package ai.dqo.metadata.sources;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.recurring.TableDailyRecurringCategoriesSpec;
import ai.dqo.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import ai.dqo.checks.table.recurring.TableRecurringChecksSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataGroupingConfigurationSpecMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.incidents.TableIncidentGroupingSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.statistics.table.TableStatisticsCollectorsRootCategoriesSpec;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/**
 * Table specification that defines data quality tests that are enabled on a table and columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TableSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("timestamp_columns", o -> o.timestampColumns);
            put("incremental_time_window", o -> o.incrementalTimeWindow);
			put("groupings", o -> o.groupings);
            put("incident_grouping", o -> o.incidentGrouping);
			put("owner", o -> o.owner);
			put("columns", o -> o.columns);
			put("profiling_checks", o -> o.profilingChecks);
            put("recurring_checks", o -> o.recurringChecks);
            put("partitioned_checks", o -> o.partitionedChecks);
            put("statistics", o -> o.statistics);
            put("schedules_override", o -> o.schedulesOverride);
			put("labels", o -> o.labels);
			put("comments", o -> o.comments);
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

    @JsonPropertyDescription("Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.")
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

    @JsonPropertyDescription("Data grouping configurations list. Data grouping configurations are configured in two cases:" +
            " (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning." +
            " (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DataGroupingConfigurationSpecMap groupings = new DataGroupingConfigurationSpecMap();

    @JsonPropertyDescription("Incident grouping configuration with the overridden configuration at a table level. The field value in this object that are configured will override the default configuration from the connection level. The incident grouping level could be changed or incident creation could be disabled.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableIncidentGroupingSpec incidentGrouping;

    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    @JsonPropertyDescription("Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level recurring checks. Recurring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A recurring check stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRecurringChecksSpec recurringChecks = new TableRecurringChecksSpec();

    @JsonPropertyDescription("Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionedChecksRootSpec partitionedChecks = new TablePartitionedChecksRootSpec();

    @JsonPropertyDescription("Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableStatisticsCollectorsRootCategoriesSpec statistics;

    @JsonPropertyDescription("Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringSchedulesSpec schedulesOverride;

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
     * Returns configuration of enabled table level recurring.
     * @return Table level recurring.
     */
    public TableRecurringChecksSpec getRecurringChecks() {
        return recurringChecks;
    }

    /**
     * Sets a new configuration of table level data quality recurring checks.
     * @param recurringChecks New recurring checks configuration.
     */
    public void setRecurringChecks(TableRecurringChecksSpec recurringChecks) {
        setDirtyIf(!Objects.equals(this.recurringChecks, recurringChecks));
        this.recurringChecks = recurringChecks;
        propagateHierarchyIdToField(recurringChecks, "recurring_checks");
    }

    /**
     * Returns configuration of enabled table level date/time partitioned checks.
     * @return Table level date/time partitioned checks.
     */
    public TablePartitionedChecksRootSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of table level date/time partitioned data quality checks.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(TablePartitionedChecksRootSpec partitionedChecks) {
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
    public RecurringSchedulesSpec getSchedulesOverride() {
        return schedulesOverride;
    }

    /**
     * Sets the table specific configuration of schedules for running checks.
     * @param schedulesOverride Configuration of schedules for running checks.
     */
    public void setSchedulesOverride(RecurringSchedulesSpec schedulesOverride) {
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
        switch (checkType) {
            case PROFILING: {
                if (this.profilingChecks != null) {
                    return this.profilingChecks;
                }

                TableProfilingCheckCategoriesSpec tableProfilingCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
                tableProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "profiling_checks"));
                if (attachCheckContainer) {
                    this.profilingChecks = tableProfilingCheckCategoriesSpec;
                }
                return tableProfilingCheckCategoriesSpec;
            }

            case RECURRING: {
                TableRecurringChecksSpec recurringSpec = this.recurringChecks;
                if (recurringSpec == null) {
                    recurringSpec = new TableRecurringChecksSpec();
                    recurringSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "recurring_checks"));
                    if (attachCheckContainer) {
                        this.recurringChecks = recurringSpec;
                    }
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (recurringSpec.getDaily() != null) {
                            return recurringSpec.getDaily();
                        }

                        TableDailyRecurringCategoriesSpec dailyRecurringCategoriesSpec = new TableDailyRecurringCategoriesSpec();
                        dailyRecurringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(recurringSpec.getHierarchyId(), "daily"));
                        if (attachCheckContainer) {
                            recurringSpec.setDaily(dailyRecurringCategoriesSpec);
                        }
                        return dailyRecurringCategoriesSpec;
                    }
                    case monthly: {
                        if (recurringSpec.getMonthly() != null) {
                            return recurringSpec.getMonthly();
                        }

                        TableMonthlyRecurringCheckCategoriesSpec monthlyRecurringCategoriesSpec = new TableMonthlyRecurringCheckCategoriesSpec();
                        monthlyRecurringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(recurringSpec.getHierarchyId(), "monthly"));
                        if (attachCheckContainer) {
                            recurringSpec.setMonthly(monthlyRecurringCategoriesSpec);
                        }
                        return monthlyRecurringCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            case PARTITIONED: {
                TablePartitionedChecksRootSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    partitionedChecksSpec = new TablePartitionedChecksRootSpec();
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
     * The object could be a profiling check container, one of recurring check containers or one of partitioned check containers.
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
        else if (checkRootContainer instanceof TableDailyRecurringCategoriesSpec) {
            if (this.recurringChecks == null) {
                this.setRecurringChecks(new TableRecurringChecksSpec());
            }

            this.getRecurringChecks().setDaily((TableDailyRecurringCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyRecurringCheckCategoriesSpec) {
            if (this.recurringChecks == null) {
                this.setRecurringChecks(new TableRecurringChecksSpec());
            }

            this.getRecurringChecks().setMonthly((TableMonthlyRecurringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedChecksRootSpec());
            }

            this.getPartitionedChecks().setDaily((TableDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedChecksRootSpec());
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

        if (this.recurringChecks != null && this.recurringChecks.hasAnyConfiguredChecks()) {
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
            case PROFILING:
                return this.profilingChecks != null && this.profilingChecks.hasAnyConfiguredChecks();

            case RECURRING:
                return this.recurringChecks != null && this.recurringChecks.hasAnyConfiguredChecks();

            case PARTITIONED:
                return this.partitionedChecks != null && this.partitionedChecks.hasAnyConfiguredChecks();
        }

        return false;
    }

    /**
     * Creates an expanded and trimmed (no checks for columns, no comments) deep copy of the table.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     * @param secretValueProvider Secret value provider.
     * @return Cloned, trimmed and expanded table specification.
     */
    public TableSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            TableSpec cloned = (TableSpec) this.clone();
            cloned.profilingChecks = null;
            cloned.recurringChecks = null;
            cloned.partitionedChecks = null;
            cloned.labels = null;
            cloned.owner = null;
            cloned.comments = null;
            cloned.statistics = null;
            if (cloned.timestampColumns != null) {
                cloned.timestampColumns = cloned.timestampColumns.expandAndTrim(secretValueProvider);
            }
            if (cloned.incrementalTimeWindow != null) {
                cloned.incrementalTimeWindow = cloned.incrementalTimeWindow.deepClone();
            }
            if (cloned.groupings != null) {
                cloned.groupings = cloned.groupings.expandAndTrim(secretValueProvider);
            }
            if (cloned.incidentGrouping != null) {
                cloned.incidentGrouping = cloned.incidentGrouping.expandAndTrim(secretValueProvider);
            }
            cloned.columns = this.columns.expandAndTrim(secretValueProvider);
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
            cloned.recurringChecks = null;
            cloned.partitionedChecks = null;
            cloned.owner = null;
            cloned.groupings = null;
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
            cloned.recurringChecks = null;
            cloned.partitionedChecks = null;
            cloned.owner = null;
            cloned.timestampColumns = null;
            cloned.incrementalTimeWindow = null;
            cloned.groupings = null;
            cloned.labels = null;
            cloned.comments = null;
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
     * This method could be called only for a new table specification that is not yet attached to a parent node.
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
}
