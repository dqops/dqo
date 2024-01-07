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
import com.dqops.checks.table.monitoring.accuracy.TableAccuracyMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.availability.TableAvailabilityMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.comparison.TableComparisonMonthlyMonitoringChecksSpecMap;
import com.dqops.checks.table.monitoring.schema.TableSchemaMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.customsql.TableCustomSqlMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.timeliness.TableTimelinessMonthlyMonitoringChecksSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeMonthlyMonitoringChecksSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesMode;
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
 * Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableMonthlyMonitoringCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableMonthlyMonitoringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("timeliness", o -> o.timeliness);
            put("accuracy", o -> o.accuracy);
            put("custom_sql", o -> o.customSql);
            put("availability", o -> o.availability);
            put("schema", o -> o.schema);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Monthly monitoring of volume data quality checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeMonthlyMonitoringChecksSpec volume;

    @JsonPropertyDescription("Monthly monitoring of timeliness checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessMonthlyMonitoringChecksSpec timeliness;

    @JsonPropertyDescription("Monthly monitoring accuracy checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAccuracyMonthlyMonitoringChecksSpec accuracy;

    @JsonPropertyDescription("Monthly monitoring of custom SQL checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableCustomSqlMonthlyMonitoringChecksSpec customSql;

    @JsonPropertyDescription("Daily partitioned availability checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityMonthlyMonitoringChecksSpec availability;

    @JsonPropertyDescription("Monthly monitoring table schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaMonthlyMonitoringChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonMonthlyMonitoringChecksSpecMap comparisons = new TableComparisonMonthlyMonitoringChecksSpecMap();

    /**
     * Returns the container of monitoring for volume data quality checks.
     * @return Container of row volume data quality monitoring.
     */
    public TableVolumeMonthlyMonitoringChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets the container of volume data quality checks (monitoring).
     * @param volume New volume checks.
     */
    public void setVolume(TableVolumeMonthlyMonitoringChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of table level timeliness monitoring.
     * @return Custom timeliness monitoring.
     */
    public TableTimelinessMonthlyMonitoringChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a container of timeliness monitoring.
     * @param timeliness Custom timeliness monitoring.
     */
    public void setTimeliness(TableTimelinessMonthlyMonitoringChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns a container of table level accuracy monitoring checks.
     * @return Table level accuracy checks.
     */
    public TableAccuracyMonthlyMonitoringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a new container of monthly monitoring accuracy checks.
     * @param accuracy New daily monitoring accuracy checks.
     */
    public void setAccuracy(TableAccuracyMonthlyMonitoringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of custom sql monitoring.
     * @return Container of custom sql monitoring.
     */
    public TableCustomSqlMonthlyMonitoringChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to a container of custom sql monitoring.
     * @param customSql Custom sql monitoring.
     */
    public void setCustomSql(TableCustomSqlMonthlyMonitoringChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        this.propagateHierarchyIdToField(customSql, "custom_sql");
    }

    /**
     * Returns a container of custom sql checks.
     * @return Custom sql checks.
     */
    public TableAvailabilityMonthlyMonitoringChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to a container of custom sql checks.
     * @param availability Container of custom sql checks.
     */
    public void setAvailability(TableAvailabilityMonthlyMonitoringChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaMonthlyMonitoringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets a reference to a container with the table schema checks.
     * @param schema Container of table schema checks.
     */
    public void setSchema(TableSchemaMonthlyMonitoringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the dictionary of comparisons.
     * @return Dictionary of comparisons.
     */
    @Override
    public TableComparisonMonthlyMonitoringChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the dictionary of comparisons.
     * @param comparisons Dictionary of comparisons.
     */
    public void setComparisons(TableComparisonMonthlyMonitoringChecksSpecMap comparisons) {
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
        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimePeriodGradient.month);
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
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunScheduleGroup getSchedulingGroup() {
        return CheckRunScheduleGroup.monitoring_monthly;
    }

    public static class TableMonthlyMonitoringCheckCategoriesSpecSampleFactory implements SampleValueFactory<TableMonthlyMonitoringCheckCategoriesSpec> {
        @Override
        public TableMonthlyMonitoringCheckCategoriesSpec createSample() {
            return new TableMonthlyMonitoringCheckCategoriesSpec() {{
                setVolume(new TableVolumeMonthlyMonitoringChecksSpec.TableVolumeMonthlyMonitoringChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
