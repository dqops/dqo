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
package ai.dqo.checks.table.recurring;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.recurring.accuracy.TableAccuracyMonthlyRecurringChecksSpec;
import ai.dqo.checks.table.recurring.availability.TableAvailabilityMonthlyRecurringChecksSpec;
import ai.dqo.checks.table.recurring.schema.TableSchemaMonthlyRecurringChecksSpec;
import ai.dqo.checks.table.recurring.sql.TableSqlMonthlyRecurringChecksSpec;
import ai.dqo.checks.table.recurring.volume.TableVolumeMonthlyRecurringChecksSpec;
import ai.dqo.checks.table.recurring.timeliness.TableTimelinessMonthlyRecurringChecksSpec;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.timeseries.TimeSeriesMode;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.sources.TableSpec;
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
 * Container of table level monthly recurring checks. Contains categories of monthly recurring checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableMonthlyRecurringCheckCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<TableMonthlyRecurringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("timeliness", o -> o.timeliness);
            put("accuracy", o -> o.accuracy);
            put("sql", o -> o.sql);
            put("availability", o -> o.availability);
            put("schema", o -> o.schema);
        }
    };

    @JsonPropertyDescription("Monthly recurring of volume data quality checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeMonthlyRecurringChecksSpec volume;

    @JsonPropertyDescription("Monthly recurring of timeliness checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessMonthlyRecurringChecksSpec timeliness;

    @JsonPropertyDescription("Monthly recurring accuracy checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAccuracyMonthlyRecurringChecksSpec accuracy;

    @JsonPropertyDescription("Monthly recurring of custom SQL checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSqlMonthlyRecurringChecksSpec sql;

    @JsonPropertyDescription("Daily partitioned availability checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityMonthlyRecurringChecksSpec availability;

    @JsonPropertyDescription("Monthly recurring table schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaMonthlyRecurringChecksSpec schema;

    /**
     * Returns the container of recurring for volume data quality checks.
     * @return Container of row volume data quality recurring.
     */
    public TableVolumeMonthlyRecurringChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets the container of volume data quality checks (recurring).
     * @param volume New volume checks.
     */
    public void setVolume(TableVolumeMonthlyRecurringChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of table level timeliness recurring.
     * @return Custom timeliness recurring.
     */
    public TableTimelinessMonthlyRecurringChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a container of timeliness recurring.
     * @param timeliness Custom timeliness recurring.
     */
    public void setTimeliness(TableTimelinessMonthlyRecurringChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns a container of table level accuracy recurring checks.
     * @return Table level accuracy checks.
     */
    public TableAccuracyMonthlyRecurringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a new container of monthly recurring accuracy checks.
     * @param accuracy New daily recurring accuracy checks.
     */
    public void setAccuracy(TableAccuracyMonthlyRecurringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of custom sql recurring.
     * @return Container of custom sql recurring.
     */
    public TableSqlMonthlyRecurringChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom sql recurring.
     * @param sql Custom sql recurring.
     */
    public void setSql(TableSqlMonthlyRecurringChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns a container of custom sql checks.
     * @return Custom sql checks.
     */
    public TableAvailabilityMonthlyRecurringChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to a container of custom sql checks.
     * @param availability Container of custom sql checks.
     */
    public void setAvailability(TableAvailabilityMonthlyRecurringChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaMonthlyRecurringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets a reference to a container with the table schema checks.
     * @param schema Container of table schema checks.
     */
    public void setSchema(TableSchemaMonthlyRecurringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
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
     * Returns time series configuration for the given group of checks.
     *
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    @Override
    public TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec) {
        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimePeriodGradient.month);
        }};
    }

    /**
     * Returns the type of checks (profiling, recurring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.RECURRING;
    }

    /**
     * Returns the time range for recurring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time range and return null.
     *
     * @return Time range (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    /**
     * Returns the check target, where the check could be applied.
     *
     * @return Check target, "table" or "column".
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     *
     * @return Recurring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunRecurringScheduleGroup getSchedulingGroup() {
        return CheckRunRecurringScheduleGroup.recurring_monthly;
    }
}
