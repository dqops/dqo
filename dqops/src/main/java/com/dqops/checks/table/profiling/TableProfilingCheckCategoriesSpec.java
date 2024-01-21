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
package com.dqops.checks.table.profiling;

import com.dqops.checks.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
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
 * Container of table level checks that are activated on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableProfilingCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableProfilingCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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

    @JsonPropertyDescription("Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent " +
         "profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfilingTimePeriod resultTruncation;

    @JsonPropertyDescription("Configuration of volume data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeProfilingChecksSpec volume;

    @JsonPropertyDescription("Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessProfilingChecksSpec timeliness;

    @JsonPropertyDescription("Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAccuracyProfilingChecksSpec accuracy;

    @JsonPropertyDescription("Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableCustomSqlProfilingChecksSpec customSql;

    @JsonPropertyDescription("Configuration of the table availability data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityProfilingChecksSpec availability;

    @JsonPropertyDescription("Configuration of schema (column count and schema) data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaProfilingChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonProfilingChecksSpecMap comparisons = new TableComparisonProfilingChecksSpecMap();

    /**
     * Returns the result truncation configuration.
     * @return Result truncation policy.
     */
    public ProfilingTimePeriod getResultTruncation() {
        return resultTruncation;
    }

    /**
     * Sets the result truncation configuration.
     * @param resultTruncation New result truncation configuration.
     */
    public void setResultTruncation(ProfilingTimePeriod resultTruncation) {
        this.setDirtyIf(!Objects.equals(this.resultTruncation, resultTruncation));
        this.resultTruncation = resultTruncation;
    }

    /**
     * Returns a container of volume check configuration on a table level.
     * @return Volume checks configuration.
     */
    public TableVolumeProfilingChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets a reference to a volume checks container.
     * @param volume New volume checks configuration.
     */
    public void setVolume(TableVolumeProfilingChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of timeliness checks.
     * @return Timeliness data quality checks on a table level configuration.
     */
    public TableTimelinessProfilingChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a timeliness table level checks container.
     * @param timeliness New timeliness checks.
     */
    public void setTimeliness(TableTimelinessProfilingChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns the configuration of accuracy checks.
     * @return Accuracy checks.
     */
    public TableAccuracyProfilingChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the configuration of accuracy checks.
     * @param accuracy Accuracy checks.
     */
    public void setAccuracy(TableAccuracyProfilingChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of custom sql checks.
     * @return Custom sql checks.
     */
    public TableCustomSqlProfilingChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to a custom sql checks container.
     * @param customSql Custom sql checks.
     */
    public void setCustomSql(TableCustomSqlProfilingChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        this.propagateHierarchyIdToField(customSql, "custom_sql");
    }

    /**
     * Returns a container of the table availability checks.
     * @return Table availability checks.
     */
    public TableAvailabilityProfilingChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to an availability checks container.
     * @param availability Table availability checks.
     */
    public void setAvailability(TableAvailabilityProfilingChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaProfilingChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets the reference to the table schema checks.
     * @param schema Table schema checks container.
     */
    public void setSchema(TableSchemaProfilingChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the dictionary of comparisons.
     * @return Dictionary of comparisons.
     */
    @Override
    public TableComparisonProfilingChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the dictionary of comparisons.
     * @param comparisons Dictionary of comparisons.
     */
    public void setComparisons(TableComparisonProfilingChecksSpecMap comparisons) {
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
        ProfilingTimePeriod profilingTimePeriod = this.resultTruncation != null ? this.resultTruncation : ProfilingTimePeriod.one_per_month;

        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(profilingTimePeriod.toTimePeriodGradient());
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
        return CheckType.profiling;
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
        return null;
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
        return CheckRunScheduleGroup.profiling;
    }

    public static class TableProfilingCheckCategoriesSpecSampleFactory implements SampleValueFactory<TableProfilingCheckCategoriesSpec> {
        @Override
        public TableProfilingCheckCategoriesSpec createSample() {
            return new TableProfilingCheckCategoriesSpec() {{
                setVolume(new TableVolumeProfilingChecksSpec.TableVolumeProfilingChecksSpecSampleFactory().createSample());
                setComparisons(null);
            }};
        }
    }
}
