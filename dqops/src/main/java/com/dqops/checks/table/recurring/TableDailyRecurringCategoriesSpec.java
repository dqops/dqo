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
package com.dqops.checks.table.recurring;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.recurring.accuracy.TableAccuracyDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.availability.TableAvailabilityDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.schema.TableSchemaDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.sql.TableSqlDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.volume.TableVolumeDailyRecurringChecksSpec;
import com.dqops.checks.table.recurring.timeliness.TableTimelinessDailyRecurringChecksSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationProvider;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
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
 * Container of table level daily recurring. Contains categories of daily recurring.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDailyRecurringCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<TableDailyRecurringCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("timeliness", o -> o.timeliness);
            put("accuracy", o -> o.accuracy);
            put("sql", o -> o.sql);
            put("availability", o -> o.availability);
            put("schema", o -> o.schema);
        }
    };

    @JsonPropertyDescription("Daily recurring volume data quality checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeDailyRecurringChecksSpec volume;

    @JsonPropertyDescription("Daily recurring timeliness checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessDailyRecurringChecksSpec timeliness;

    @JsonPropertyDescription("Daily recurring accuracy checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAccuracyDailyRecurringChecksSpec accuracy;

    @JsonPropertyDescription("Daily recurring custom SQL checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSqlDailyRecurringChecksSpec sql;

    @JsonPropertyDescription("Daily recurring table availability checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityDailyRecurringChecksSpec availability;

    @JsonPropertyDescription("Daily recurring table schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaDailyRecurringChecksSpec schema;


    /**
     * Returns the container of recurring for volume data quality checks.
     * @return Container of row volume data quality recurring.
     */
    public TableVolumeDailyRecurringChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets the container of volume data quality checks (recurring).
     * @param volume New volume checks.
     */
    public void setVolume(TableVolumeDailyRecurringChecksSpec volume) {
		this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
		this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of table level timeliness recurring.
     * @return Custom timeliness recurring.
     */
    public TableTimelinessDailyRecurringChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a container of timeliness recurring.
     * @param timeliness Custom timeliness recurring.
     */
    public void setTimeliness(TableTimelinessDailyRecurringChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns a container of table level accuracy recurring checks.
     * @return Table level accuracy checks.
     */
    public TableAccuracyDailyRecurringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a new container of daily recurring accuracy checks.
     * @param accuracy New daily recurring accuracy checks.
     */
    public void setAccuracy(TableAccuracyDailyRecurringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of table level custom SQL recurring.
     * @return Custom sql recurring.
     */
    public TableSqlDailyRecurringChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom sql recurring.
     * @param sql Custom sql recurring.
     */
    public void setSql(TableSqlDailyRecurringChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns a container of table availability checks.
     * @return Table availability checks.
     */
    public TableAvailabilityDailyRecurringChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to a container of the table availability checks.
     * @param availability Container of table availability checks.
     */
    public void setAvailability(TableAvailabilityDailyRecurringChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaDailyRecurringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets a reference to a container with the table schema checks.
     * @param schema Container of table schema checks.
     */
    public void setSchema(TableSchemaDailyRecurringChecksSpec schema) {
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
            setTimeGradient(TimePeriodGradient.day);
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
        return CheckTimeScale.daily;
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
        return CheckRunRecurringScheduleGroup.recurring_daily;
    }
}
