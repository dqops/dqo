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
package com.dqops.checks.table.monitoring;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.monitoring.accuracy.TableAccuracyDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.comparison.TableComparisonDailyMonitoringChecksSpecMap;
import com.dqops.checks.table.monitoring.schema.TableSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.sql.TableSqlDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.timeliness.TableTimelinessDailyMonitoringChecksSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
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
 * Container of table level daily monitoring. Contains categories of daily monitoring.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDailyMonitoringCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableDailyMonitoringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("timeliness", o -> o.timeliness);
            put("accuracy", o -> o.accuracy);
            put("sql", o -> o.sql);
            put("availability", o -> o.availability);
            put("schema", o -> o.schema);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Daily monitoring volume data quality checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeDailyMonitoringChecksSpec volume;

    @JsonPropertyDescription("Daily monitoring timeliness checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessDailyMonitoringChecksSpec timeliness;

    @JsonPropertyDescription("Daily monitoring accuracy checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAccuracyDailyMonitoringChecksSpec accuracy;

    @JsonPropertyDescription("Daily monitoring custom SQL checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSqlDailyMonitoringChecksSpec sql;

    @JsonPropertyDescription("Daily monitoring table availability checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityDailyMonitoringChecksSpec availability;

    @JsonPropertyDescription("Daily monitoring table schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaDailyMonitoringChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonDailyMonitoringChecksSpecMap comparisons = new TableComparisonDailyMonitoringChecksSpecMap();


    /**
     * Returns the container of monitoring for volume data quality checks.
     *
     * @return Container of row volume data quality monitoring.
     */
    public TableVolumeDailyMonitoringChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets the container of volume data quality checks (monitoring).
     *
     * @param volume New volume checks.
     */
    public void setVolume(TableVolumeDailyMonitoringChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of table level timeliness monitoring.
     *
     * @return Custom timeliness monitoring.
     */
    public TableTimelinessDailyMonitoringChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a container of timeliness monitoring.
     *
     * @param timeliness Custom timeliness monitoring.
     */
    public void setTimeliness(TableTimelinessDailyMonitoringChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns a container of table level accuracy monitoring checks.
     *
     * @return Table level accuracy checks.
     */
    public TableAccuracyDailyMonitoringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a new container of daily monitoring accuracy checks.
     *
     * @param accuracy New daily monitoring accuracy checks.
     */
    public void setAccuracy(TableAccuracyDailyMonitoringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of table level custom SQL monitoring.
     *
     * @return Custom sql monitoring.
     */
    public TableSqlDailyMonitoringChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom sql monitoring.
     *
     * @param sql Custom sql monitoring.
     */
    public void setSql(TableSqlDailyMonitoringChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns a container of table availability checks.
     *
     * @return Table availability checks.
     */
    public TableAvailabilityDailyMonitoringChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to a container of the table availability checks.
     *
     * @param availability Container of table availability checks.
     */
    public void setAvailability(TableAvailabilityDailyMonitoringChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     *
     * @return Table schema checks.
     */
    public TableSchemaDailyMonitoringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets a reference to a container with the table schema checks.
     *
     * @param schema Container of table schema checks.
     */
    public void setSchema(TableSchemaDailyMonitoringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the dictionary of comparisons.
     * @return Dictionary of comparisons.
     */
    @Override
    public TableComparisonDailyMonitoringChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the dictionary of comparisons.
     * @param comparisons Dictionary of comparisons.
     */
    public void setComparisons(TableComparisonDailyMonitoringChecksSpecMap comparisons) {
        this.setDirtyIf(!Objects.equals(this.comparisons, comparisons));
        this.comparisons = comparisons;
        this.propagateHierarchyIdToField(comparisons, "comparisons");
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
        return new TimeSeriesConfigurationSpec() {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimePeriodGradient.day);
        }};
    }

    /**
     * Returns the type of checks (profiling, monitoring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.monitoring;
    }

    /**
     * Returns the time range for monitoring and partitioned checks (daily, monthly, etc.).
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
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunScheduleGroup getSchedulingGroup() {
        return CheckRunScheduleGroup.monitoring_daily;
    }

    public static class TableDailyMonitoringCheckCategoriesSpecSampleFactory implements SampleValueFactory<TableDailyMonitoringCheckCategoriesSpec> {
        @Override
        public TableDailyMonitoringCheckCategoriesSpec createSample() {
            return new TableDailyMonitoringCheckCategoriesSpec() {{
                setVolume(new TableVolumeDailyMonitoringChecksSpec.TableVolumeDailyMonitoringChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
