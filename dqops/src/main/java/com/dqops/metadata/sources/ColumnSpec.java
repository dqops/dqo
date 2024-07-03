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
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.statistics.column.ColumnStatisticsCollectorsRootCategoriesSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column specification that identifies a single column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ColumnSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("type_snapshot", o -> o.typeSnapshot);
			put("profiling_checks", o -> o.profilingChecks);
            put("monitoring_checks", o -> o.monitoringChecks);
            put("partitioned_checks", o -> o.partitionedChecks);
            put("statistics", o -> o.statistics);
            put("labels", o -> o.labels);
			put("comments", o -> o.comments);
        }
    };

    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("SQL expression used for calculated fields or when additional column value transformation is required before the column can be used for analysis with data quality checks (data type conversion, transformation). It should be an SQL expression that uses the SQL language of the analyzed database type. Use the replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of the table under analysis, or {column} to replace the content with the analyzed column name. An example of extracting a value from a string column storing JSON in PostgreSQL: \"{column}::json->'address'->'zip'\".")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String sqlExpression;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("True when this column is a part of the primary key or a business key that identifies a row. Error sampling captures values of id columns to identify the row where the error sample was found.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean id;

    @JsonPropertyDescription("Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnProfilingCheckCategoriesSpec profilingChecks;

    @JsonPropertyDescription("Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnMonitoringCheckCategoriesSpec monitoringChecks;

    @JsonPropertyDescription("Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPartitionedCheckCategoriesSpec partitionedChecks;

    @JsonPropertyDescription("Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStatisticsCollectorsRootCategoriesSpec statistics;

    @JsonPropertyDescription("Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private LabelSetSpec labels;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    public ColumnSpec() {
    }

    /**
     * Creates a column spec given the type snapshot.
     * @param typeSnapshot Column type snapshot.
     */
    public ColumnSpec(ColumnTypeSnapshotSpec typeSnapshot) {
        this.typeSnapshot = typeSnapshot;
    }

    /**
     * Disable the quality checks and prevent it from executing for this column.
     * @return Quality check is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Changes the disabled flag of data quality checks for a column.
     * @param disabled When true, the test will be disabled and will not be executed.
     */
    public void setDisabled(boolean disabled) {
		this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }

    /**
     * Returns an SQL expression for a calculated column.
     * @return SQL expression.
     */
    public String getSqlExpression() {
        return sqlExpression;
    }

    /**
     * Sets an SQL expression used for a calculated column.
     * @param sqlExpression SQL expression used for a calculated column.
     */
    public void setSqlExpression(String sqlExpression) {
        this.setDirtyIf(!Objects.equals(this.sqlExpression, sqlExpression));
        this.sqlExpression = sqlExpression;
    }

    /**
     * Returns the column type snapshot that was captured at the time of the table metadata import. The column type snapshot
     * may be used for quality checks.
     * @return Column type snapshot.
     */
    public ColumnTypeSnapshotSpec getTypeSnapshot() {
        return typeSnapshot;
    }

    /**
     * Sets the column type snapshot.
     * @param typeSnapshot Column type snapshot.
     */
    public void setTypeSnapshot(ColumnTypeSnapshotSpec typeSnapshot) {
		this.setDirtyIf(!Objects.equals(this.typeSnapshot, typeSnapshot));
        this.typeSnapshot = typeSnapshot;
		propagateHierarchyIdToField(typeSnapshot, "type_snapshot");
    }

    /**
     * Returns true when this column is a part of the business or primary key of the row. The values of ID columns are captured for error samples.
     * @return True when the value is an ID column.
     */
    public boolean isId() {
        return id;
    }

    /**
     * Sets a flag to identify columns that are part of the identifier (primary or business key).
     * @param id Is an ID column.
     */
    public void setId(boolean id) {
        this.setDirtyIf(this.id != id);
        this.id = id;
    }

    /**
     * Returns configuration of enabled column level data quality checks.
     * @return Column level data quality checks.
     */
    public ColumnProfilingCheckCategoriesSpec getProfilingChecks() {
        return profilingChecks;
    }

    /**
     * Sets a new configuration of column level profiling data quality checks.
     * @param profilingChecks New profiling checks configuration.
     */
    public void setProfilingChecks(ColumnProfilingCheckCategoriesSpec profilingChecks) {
		setDirtyIf(!Objects.equals(this.profilingChecks, profilingChecks));
        this.profilingChecks = profilingChecks;
		propagateHierarchyIdToField(profilingChecks, "profiling_checks");
    }

    /**
     * Returns configuration of enabled column level monitoring checks.
     * @return Column level monitoring.
     */
    public ColumnMonitoringCheckCategoriesSpec getMonitoringChecks() {
        return monitoringChecks;
    }

    /**
     * Sets a new configuration of column level data quality monitoring checks.
     * @param monitoringChecks New monitoring checks configuration.
     */
    public void setMonitoringChecks(ColumnMonitoringCheckCategoriesSpec monitoringChecks) {
        setDirtyIf(!Objects.equals(this.monitoringChecks, monitoringChecks));
        this.monitoringChecks = monitoringChecks;
        propagateHierarchyIdToField(monitoringChecks, "monitoring_checks");
    }

    /**
     * Returns configuration of enabled column level date/time partitioned checks.
     * @return Column level date/time partitioned checks.
     */
    public ColumnPartitionedCheckCategoriesSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of column level date/time partitioned data quality checks.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(ColumnPartitionedCheckCategoriesSpec partitionedChecks) {
        setDirtyIf(!Objects.equals(this.partitionedChecks, partitionedChecks));
        this.partitionedChecks = partitionedChecks;
        propagateHierarchyIdToField(partitionedChecks, "partitioned_checks");
    }

    /**
     * Returns a custom configuration of a column level statistics collector for this column.
     * @return Custom statistics collector instance or null when the default (built-in) configuration settings should be used.
     */
    public ColumnStatisticsCollectorsRootCategoriesSpec getStatistics() {
        return statistics;
    }

    /**
     * Sets a reference to a custom statistics collector configuration on a column level.
     * @param statistics Custom statistics collector configuration.
     */
    public void setStatistics(ColumnStatisticsCollectorsRootCategoriesSpec statistics) {
        setDirtyIf(!Objects.equals(this.statistics, statistics));
        this.statistics = statistics;
        propagateHierarchyIdToField(statistics, "statistics");
    }

    /**
     * List of labels assigned to a column. Labels are used for targeting the execution of tests.
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
     * Returns a collection of comments for this connection.
     * @return List of comments (or null).
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a list of comments for this connection.
     * @param comments Comments list.
     */
    public void setComments(CommentsListSpec comments) {
		setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
		propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Retrieves a non-null root check container for the requested category.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the column specification.
     *
     * @param checkType            Check type.
     * @param checkTimeScale       Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the column.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getColumnCheckRootContainer(CheckType checkType,
                                                                       CheckTimeScale checkTimeScale,
                                                                       boolean attachCheckContainer) {
        return getColumnCheckRootContainer(checkType, checkTimeScale, attachCheckContainer, true);
    }

    /**
     * Retrieves a non-null root check container for the requested category.  Returns null when the check container is not present.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the column specification.
     *
     * @param checkType            Check type.
     * @param checkTimeScale       Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the column.
     * @param createEmptyContainerWhenNull Creates a new check container instance when it is null.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getColumnCheckRootContainer(CheckType checkType,
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

                ColumnProfilingCheckCategoriesSpec columnProfilingCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
                columnProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "profiling_checks"));
                if (attachCheckContainer) {
                    this.profilingChecks = columnProfilingCheckCategoriesSpec;
                }
                return columnProfilingCheckCategoriesSpec;
            }

            case monitoring: {
                ColumnMonitoringCheckCategoriesSpec monitoringSpec = this.monitoringChecks;
                if (monitoringSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    monitoringSpec = new ColumnMonitoringCheckCategoriesSpec();
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

                        ColumnDailyMonitoringCheckCategoriesSpec dailyMonitoringCategoriesSpec = new ColumnDailyMonitoringCheckCategoriesSpec();
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

                        ColumnMonthlyMonitoringCheckCategoriesSpec monthlyMonitoringCategoriesSpec = new ColumnMonthlyMonitoringCheckCategoriesSpec();
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
                ColumnPartitionedCheckCategoriesSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    partitionedChecksSpec = new ColumnPartitionedCheckCategoriesSpec();
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

                        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedCategoriesSpec = new ColumnDailyPartitionedCheckCategoriesSpec();
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

                        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCategoriesSpec = new ColumnMonthlyPartitionedCheckCategoriesSpec();
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
     * The object could be a profiling check container, one of monitoring containers or one of partitioned checks container.
     * @param checkRootContainer Root check container to store.
     */
    @JsonIgnore
    public void setColumnCheckRootContainer(AbstractRootChecksContainerSpec checkRootContainer) {
        if (checkRootContainer == null) {
            throw new NullPointerException("Root check container cannot be null");
        }

        if (checkRootContainer instanceof ColumnProfilingCheckCategoriesSpec) {
            this.setProfilingChecks((ColumnProfilingCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setDaily((ColumnDailyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setMonthly((ColumnMonthlyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setDaily((ColumnDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setMonthly((ColumnMonthlyPartitionedCheckCategoriesSpec)checkRootContainer);
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
     * Creates and returns a copy of this object.
     */
    @Override
    public ColumnSpec deepClone() {
        ColumnSpec cloned = (ColumnSpec) super.deepClone();
        return cloned;
    }

    /**
     * Retrieves the column name from the parent hierarchy. Works only when the column specification was added to the column map under a column name.
     * @return Column name.
     */
    @JsonIgnore
    public String getColumnName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        return hierarchyId.getLast().toString();
    }

    /**
     * Creates a trimmed version of the object without unwanted properties. Additionally, some variables that contain environment variables are expanded.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Trimmed version of this object.
     */
    public ColumnSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        try {
            ColumnSpec cloned = (ColumnSpec) super.clone(); // skipping "this" deepClone, we are using an alternative clone concept
            cloned.comments = null;
            cloned.profilingChecks = null;
            cloned.monitoringChecks = null;
            cloned.partitionedChecks = null;
            cloned.statistics = null;
            cloned.labels = null;
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            return this;
        }
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public ColumnSpec trim() {
        try {
            ColumnSpec cloned = (ColumnSpec) super.clone(); // skipping "this" deepClone, we are using an alternative clone concept
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.deepClone();
            }
            cloned.comments = null;
            cloned.profilingChecks = null;
            cloned.monitoringChecks = null;
            cloned.partitionedChecks = null;
            cloned.labels = null;
            cloned.statistics = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            return this;
        }
    }

    /**
     * Inspects all check containers and verifies if any of them has any checks configured.
     * @return True when the column has some column level checks, false when no column level checks were found.
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
     * @return True when the column has some column level checks, false when no column level checks were found.
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

    public static class ColumnSpecSampleFactory implements SampleValueFactory<ColumnSpec> {
        @Override
        public ColumnSpec createSample() {
            return new ColumnSpec() {{
                setDisabled(false);
                setTypeSnapshot(new ColumnTypeSnapshotSpec.ColumnTypeSnapshotSpecSampleFactory().createSample());
                setProfilingChecks(new ColumnProfilingCheckCategoriesSpec.ColumnProfilingCheckCategoriesSpecSampleFactory().createSample());
            }};
        }
    }
}
