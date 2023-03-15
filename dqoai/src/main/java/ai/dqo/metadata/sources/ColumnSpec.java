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
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnRecurringSpec;
import ai.dqo.checks.column.recurring.ColumnDailyRecurringCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnMonthlyRecurringCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.statistics.column.ColumnStatisticsCollectorsRootCategoriesSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
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
			put("checks", o -> o.checks);
            put("recurring", o -> o.recurring);
            put("partitioned_checks", o -> o.partitionedChecks);
            put("statistics_collector", o -> o.statisticsCollector);
            put("labels", o -> o.labels);
			put("comments", o -> o.comments);
        }
    };

    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("Configuration of data quality checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnProfilingCheckCategoriesSpec checks;

    @JsonPropertyDescription("Configuration of column level recurring. Recurring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A recurring stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnRecurringSpec recurring;

    @JsonPropertyDescription("Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPartitionedChecksRootSpec partitionedChecks;

    @JsonPropertyDescription("Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStatisticsCollectorsRootCategoriesSpec statisticsCollector;

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
     * Returns configuration of enabled column level data quality checks.
     * @return Column level data quality checks.
     */
    public ColumnProfilingCheckCategoriesSpec getChecks() {
        return checks;
    }

    /**
     * Sets a new configuration of column level data quality checks.
     * @param checks New checks configuration.
     */
    public void setChecks(ColumnProfilingCheckCategoriesSpec checks) {
		setDirtyIf(!Objects.equals(this.checks, checks));
        this.checks = checks;
		propagateHierarchyIdToField(checks, "checks");
    }

    /**
     * Returns configuration of enabled column level recurring.
     * @return Column level recurring.
     */
    public ColumnRecurringSpec getRecurring() {
        return recurring;
    }

    /**
     * Sets a new configuration of column level data quality recurring.
     * @param recurring New recurring configuration.
     */
    public void setRecurring(ColumnRecurringSpec recurring) {
        setDirtyIf(!Objects.equals(this.recurring, recurring));
        this.recurring = recurring;
        propagateHierarchyIdToField(recurring, "recurring");
    }

    /**
     * Returns configuration of enabled column level date/time partitioned checks.
     * @return Column level date/time partitioned checks.
     */
    public ColumnPartitionedChecksRootSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of column level date/time partitioned data quality recurring.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(ColumnPartitionedChecksRootSpec partitionedChecks) {
        setDirtyIf(!Objects.equals(this.partitionedChecks, partitionedChecks));
        this.partitionedChecks = partitionedChecks;
        propagateHierarchyIdToField(partitionedChecks, "partitioned_checks");
    }

    /**
     * Returns a custom configuration of a column level statistics collector for this column.
     * @return Custom statistics collector instance or null when the default (built-in) configuration settings should be used.
     */
    public ColumnStatisticsCollectorsRootCategoriesSpec getStatisticsCollector() {
        return statisticsCollector;
    }

    /**
     * Sets a reference to a custom statistics collector configuration on a column level.
     * @param statisticsCollector Custom statistics collector configuration.
     */
    public void setStatisticsCollector(ColumnStatisticsCollectorsRootCategoriesSpec statisticsCollector) {
        setDirtyIf(!Objects.equals(this.statisticsCollector, statisticsCollector));
        this.statisticsCollector = statisticsCollector;
        propagateHierarchyIdToField(statisticsCollector, "statistics_collector");
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
     * @param checkType Check type.
     * @param checkTimeScale Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getColumnCheckRootContainer(CheckType checkType,
                                                                       CheckTimeScale checkTimeScale) {
        switch (checkType) {
            case PROFILING: {
                if (this.checks != null) {
                    return this.checks;
                }

                ColumnProfilingCheckCategoriesSpec columnProfilingCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
                columnProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "checks"));
                return columnProfilingCheckCategoriesSpec;
            }

            case RECURRING: {
                ColumnRecurringSpec recurringSpec = this.recurring;
                if (recurringSpec == null) {
                    recurringSpec = new ColumnRecurringSpec();
                    recurringSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "recurring"));
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (recurringSpec.getDaily() != null) {
                            return recurringSpec.getDaily();
                        }

                        ColumnDailyRecurringCategoriesSpec dailyRecurringCategoriesSpec = new ColumnDailyRecurringCategoriesSpec();
                        dailyRecurringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(recurringSpec.getHierarchyId(), "daily"));
                        return dailyRecurringCategoriesSpec;
                    }
                    case monthly: {
                        if (recurringSpec.getMonthly() != null) {
                            return recurringSpec.getMonthly();
                        }

                        ColumnMonthlyRecurringCategoriesSpec monthlyRecurringCategoriesSpec = new ColumnMonthlyRecurringCategoriesSpec();
                        monthlyRecurringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(recurringSpec.getHierarchyId(), "monthly"));
                        return monthlyRecurringCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            case PARTITIONED: {
                ColumnPartitionedChecksRootSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
                    partitionedChecksSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "partitioned_checks"));
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (partitionedChecksSpec.getDaily() != null) {
                            return partitionedChecksSpec.getDaily();
                        }

                        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedCategoriesSpec = new ColumnDailyPartitionedCheckCategoriesSpec();
                        dailyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "daily"));
                        return dailyPartitionedCategoriesSpec;
                    }
                    case monthly: {
                        if (partitionedChecksSpec.getMonthly() != null) {
                            return partitionedChecksSpec.getMonthly();
                        }

                        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCategoriesSpec = new ColumnMonthlyPartitionedCheckCategoriesSpec();
                        monthlyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "monthly"));
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
     * The object could be a profiling check container, one of recurring containers or one of partitioned checks container.
     * @param checkRootContainer Root check container to store.
     */
    @JsonIgnore
    public void setColumnCheckRootContainer(AbstractRootChecksContainerSpec checkRootContainer) {
        if (checkRootContainer == null) {
            throw new NullPointerException("Root check container cannot be null");
        }

        if (checkRootContainer instanceof ColumnProfilingCheckCategoriesSpec) {
            this.setChecks((ColumnProfilingCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyRecurringCategoriesSpec) {
            if (this.recurring == null) {
                this.setRecurring(new ColumnRecurringSpec());
            }

            this.getRecurring().setDaily((ColumnDailyRecurringCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyRecurringCategoriesSpec) {
            if (this.recurring == null) {
                this.setRecurring(new ColumnRecurringSpec());
            }

            this.getRecurring().setMonthly((ColumnMonthlyRecurringCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedChecksRootSpec());
            }

            this.getPartitionedChecks().setDaily((ColumnDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedChecksRootSpec());
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
     * @return Trimmed version of this object.
     */
    public ColumnSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            ColumnSpec cloned = (ColumnSpec) super.clone(); // skipping "this" deepClone, we are using an alternative clone concept
            cloned.comments = null;
            cloned.checks = null;
            cloned.recurring = null;
            cloned.partitionedChecks = null;
            cloned.statisticsCollector = null;
            cloned.labels = null;
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.expandAndTrim(secretValueProvider);
            }
//            if (cloned.labels != null) {
//                cloned.labels = cloned.labels.deepClone();
//            }
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
            cloned.checks = null;
            cloned.recurring = null;
            cloned.partitionedChecks = null;
            cloned.labels = null;
            cloned.statisticsCollector = null;
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
        if (this.checks != null && this.checks.hasAnyConfiguredChecks()) {
            return true;
        }

        if (this.recurring != null && this.recurring.hasAnyConfiguredChecks()) {
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
            case PROFILING:
                return this.checks != null && this.checks.hasAnyConfiguredChecks();

            case RECURRING:
                return this.recurring != null && this.recurring.hasAnyConfiguredChecks();

            case PARTITIONED:
                return this.partitionedChecks != null && this.partitionedChecks.hasAnyConfiguredChecks();
        }

        return false;
    }
}
