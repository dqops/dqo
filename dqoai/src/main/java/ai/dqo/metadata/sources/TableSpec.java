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

import ai.dqo.checks.table.TableCheckCategoriesSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
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
public class TableSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<TableSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("target", o -> o.target);
			put("time_series", o -> o.timeSeries);
			put("dimensions", o -> o.dimensions);
			put("owner", o -> o.owner);
			put("columns", o -> o.columns);
			put("checks", o -> o.checks);
            put("schedule_override", o -> o.scheduleOverride);
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
     * @param target Target table specification.
     */
    public TableSpec(TableTargetSpec target) {
        this.target = target;
    }

    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name)")
    private TableTargetSpec target = new TableTargetSpec();

    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Stage name.")
    private String stage;

    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries.")
    private String filter;

    @JsonPropertyDescription("Time series source configuration. Chooses the source for the time series. Time series of data quality sensor readings may be calculated from a timestamp column or a current time may be used. Also the time gradient (day, week) may be configured to analyse the data behavior at a correct scale.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();

    @JsonPropertyDescription("Data quality dimensions configuration. Dimensions are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DimensionsConfigurationSpec dimensions = new DimensionsConfigurationSpec();

    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    @JsonPropertyDescription("Configuration of data quality checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableCheckCategoriesSpec checks = new TableCheckCategoriesSpec();

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec scheduleOverride;

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
     * Target table that is covered by the data quality tests.
     * @return Table target.
     */
    public TableTargetSpec getTarget() {
        return target;
    }

    /**
     * Changes the table target.
     * @param target Table target.
     */
    public void setTarget(TableTargetSpec target) {
		setDirtyIf(!Objects.equals(this.target, target));
        this.target = target;
		propagateHierarchyIdToField(target, "target");
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
     * Returns the time series configuration for this table.
     * @return Time series configuration.
     */
    public TimeSeriesConfigurationSpec getTimeSeries() {
        return timeSeries;
    }

    /**
     * Sets a new time series configuration for this table.
     * @param timeSeries New time series configuration.
     */
    public void setTimeSeries(TimeSeriesConfigurationSpec timeSeries) {
		setDirtyIf(!Objects.equals(this.timeSeries, timeSeries));
        this.timeSeries = timeSeries;
		propagateHierarchyIdToField(timeSeries, "time_series");
    }

    /**
     * Returns the data quality measure dimensions configuration for the table.
     * @return Dimension configuration.
     */
    public DimensionsConfigurationSpec getDimensions() {
        return dimensions;
    }

    /**
     * Returns the dimension configuration for the table.
     * @param dimensions Dimension configuration.
     */
    public void setDimensions(DimensionsConfigurationSpec dimensions) {
		setDirtyIf(!Objects.equals(this.dimensions, dimensions));
        this.dimensions = dimensions;
		propagateHierarchyIdToField(dimensions, "dimensions");
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
     * Returns configuration of enabled table level data quality checks.
     * @return Table level data quality checks.
     */
    public TableCheckCategoriesSpec getChecks() {
        return checks;
    }

    /**
     * Sets a new configuration of table level data quality checks.
     * @param checks New checks configuration.
     */
    public void setChecks(TableCheckCategoriesSpec checks) {
		setDirtyIf(!Objects.equals(this.checks, checks));
        this.checks = checks;
		propagateHierarchyIdToField(checks, "checks");
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
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
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
            cloned.checks = null;
            cloned.scheduleOverride = null;
            cloned.owner = null;
            cloned.comments = null;
            if (cloned.target != null) {
                cloned.target = cloned.target.expandAndTrim(secretValueProvider);
            }
            if (cloned.timeSeries != null) {
                cloned.timeSeries = cloned.timeSeries.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimensions != null) {
                cloned.dimensions = cloned.dimensions.expandAndTrim(secretValueProvider);
            }
            if (cloned.labels != null) {
                cloned.labels = cloned.labels.clone(); // TODO: should we expand labels? probably that is too far...
            }
            cloned.columns = this.columns.expandAndTrim(secretValueProvider);
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
    public TableSpec trim() {
        try {
            TableSpec cloned = (TableSpec) this.clone();
            cloned.timeSeries = null;
            cloned.dimensions = null;
            cloned.comments = null;
            cloned.scheduleOverride = null;
            cloned.columns = this.columns.trim();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }
}
