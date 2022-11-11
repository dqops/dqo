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

import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rits.cloning.Cloner;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Column specification that identifies a single column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<ColumnSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("type_snapshot", o -> o.typeSnapshot);
			put("time_series_override", o -> o.timeSeriesOverride);
			put("data_streams_override", o -> o.dataStreamsOverride);
			put("checks", o -> o.checks);
            put("checkpoints", o -> o.checkpoints);
            put("partitioned_checks", o -> o.partitionedChecks);
            put("schedule_override", o -> o.scheduleOverride);
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

    @JsonPropertyDescription("Time series source configuration for a table. When a time series configuration is assigned at a table level, it overrides any time series settings from the connection or table levels. Time series configuration chooses the source for the time series. Time series of data quality sensor readings may be calculated from a timestamp column or a current time may be used. Also the time gradient (day, week) may be configured to analyse the data behavior at a correct scale.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    @Deprecated
    private TimeSeriesConfigurationSpec timeSeriesOverride;

    @JsonPropertyDescription("Data streams configuration. When a data streams configuration is assigned at a table level, it overrides any data streams settings from the connection or table levels. Dimensions are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    @Deprecated
    private DataStreamMappingSpec dataStreamsOverride;

    @JsonPropertyDescription("Configuration of data quality checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAdHocCheckCategoriesSpec checks;

    @JsonPropertyDescription("Configuration of column level checkpoints. Checkpoints are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A checkpoint stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCheckpointsSpec checkpoints;

    @JsonPropertyDescription("Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPartitionedChecksRootSpec partitionedChecks;

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec scheduleOverride;

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
     * Returns the time series configuration for this column.
     * @return Time series configuration.
     */
    public TimeSeriesConfigurationSpec getTimeSeriesOverride() {
        return timeSeriesOverride;
    }

    /**
     * Sets a new time series configuration for this column.
     * @param timeSeriesOverride New time series configuration.
     */
    public void setTimeSeriesOverride(TimeSeriesConfigurationSpec timeSeriesOverride) {
		setDirtyIf(!Objects.equals(this.timeSeriesOverride, timeSeriesOverride));
        this.timeSeriesOverride = timeSeriesOverride;
		propagateHierarchyIdToField(timeSeriesOverride, "time_series_override");
    }

    /**
     * Returns the data streams configuration for the column.
     * @return Data streams configuration.
     */
    @Deprecated
    public DataStreamMappingSpec getDataStreamsOverride() {
        return dataStreamsOverride;
    }

    /**
     * Returns the data streams configuration for the column.
     * @param dataStreamsOverride Data streams configuration.
     */
    public void setDataStreamsOverride(DataStreamMappingSpec dataStreamsOverride) {
		setDirtyIf(!Objects.equals(this.dataStreamsOverride, dataStreamsOverride));
        this.dataStreamsOverride = dataStreamsOverride;
		propagateHierarchyIdToField(dataStreamsOverride, "data_streams_override");
    }

    /**
     * Returns configuration of enabled column level data quality checks.
     * @return Column level data quality checks.
     */
    public ColumnAdHocCheckCategoriesSpec getChecks() {
        return checks;
    }

    /**
     * Sets a new configuration of column level data quality checks.
     * @param checks New checks configuration.
     */
    public void setChecks(ColumnAdHocCheckCategoriesSpec checks) {
		setDirtyIf(!Objects.equals(this.checks, checks));
        this.checks = checks;
		propagateHierarchyIdToField(checks, "checks");
    }

    /**
     * Returns configuration of enabled column level checkpoints.
     * @return Column level checkpoints.
     */
    public ColumnCheckpointsSpec getCheckpoints() {
        return checkpoints;
    }

    /**
     * Sets a new configuration of column level data quality checkpoints.
     * @param checkpoints New checkpoints configuration.
     */
    public void setCheckpoints(ColumnCheckpointsSpec checkpoints) {
        setDirtyIf(!Objects.equals(this.checkpoints, checkpoints));
        this.checkpoints = checkpoints;
        propagateHierarchyIdToField(checkpoints, "checkpoints");
    }

    /**
     * Returns configuration of enabled column level date/time partitioned checks.
     * @return Column level date/time partitioned checks.
     */
    public ColumnPartitionedChecksRootSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of column level date/time partitioned data quality checkpoints.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(ColumnPartitionedChecksRootSpec partitionedChecks) {
        setDirtyIf(!Objects.equals(this.partitionedChecks, partitionedChecks));
        this.partitionedChecks = partitionedChecks;
        propagateHierarchyIdToField(partitionedChecks, "partitioned_checks");
    }

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public RecurringScheduleSpec getScheduleOverride() {
        return scheduleOverride;
    }

    /**
     * Stores a new schedule configuration.
     * @param scheduleOverride New schedule configuration.
     */
    public void setScheduleOverride(RecurringScheduleSpec scheduleOverride) {
        setDirtyIf(!Objects.equals(this.scheduleOverride, scheduleOverride));
        this.scheduleOverride = scheduleOverride;
        propagateHierarchyIdToField(scheduleOverride, "schedule_override");
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
    public ColumnSpec clone() {
        try {
            ColumnSpec cloned = (ColumnSpec) super.clone();
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.clone();
            }

            if (cloned.timeSeriesOverride != null) {
                cloned.timeSeriesOverride = cloned.timeSeriesOverride.clone();
            }

            if (cloned.dataStreamsOverride != null) {
                cloned.dataStreamsOverride = cloned.dataStreamsOverride.clone();
            }

            if (cloned.labels != null) {
                cloned.labels = cloned.labels.clone();
            }

            if (cloned.comments != null) {
                cloned.comments = cloned.comments.clone();
            }

            if (cloned.scheduleOverride != null) {
                cloned.scheduleOverride = cloned.scheduleOverride.clone();
            }

            if (cloned.checks != null) {
                Cloner cloner = new Cloner();
                cloned.checks = cloner.deepClone(cloned.checks);
            }

            if (cloned.checkpoints != null) {
                Cloner cloner = new Cloner();
                cloned.checkpoints = cloner.deepClone(cloned.checkpoints);
            }

            if (cloned.partitionedChecks != null) {
                Cloner cloner = new Cloner();
                cloned.partitionedChecks = cloner.deepClone(cloned.partitionedChecks);
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
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
            ColumnSpec cloned = (ColumnSpec) super.clone(); // skipping "this" clone, we are using an alternative clone concept
            cloned.comments = null;
            cloned.checks = null;
            cloned.checkpoints = null;
            cloned.partitionedChecks = null;
            cloned.scheduleOverride = null;
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.expandAndTrim(secretValueProvider);
            }
            if (cloned.timeSeriesOverride != null) {
                cloned.timeSeriesOverride = cloned.timeSeriesOverride.expandAndTrim(secretValueProvider);
            }
            if (cloned.dataStreamsOverride != null) {
                cloned.dataStreamsOverride = cloned.dataStreamsOverride.expandAndTrim(secretValueProvider);
            }
            if (cloned.labels != null) {
                cloned.labels = cloned.labels.clone();
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
            ColumnSpec cloned = (ColumnSpec) super.clone(); // skipping "this" clone, we are using an alternative clone concept
            if (cloned.typeSnapshot != null) {
                cloned.typeSnapshot = cloned.typeSnapshot.clone();
            }
            cloned.comments = null;
            cloned.checks = null;
            cloned.checkpoints = null;
            cloned.partitionedChecks = null;
            cloned.scheduleOverride = null;
            cloned.timeSeriesOverride = null;
            cloned.dataStreamsOverride = null;
            cloned.labels = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            return this;
        }
    }
}
