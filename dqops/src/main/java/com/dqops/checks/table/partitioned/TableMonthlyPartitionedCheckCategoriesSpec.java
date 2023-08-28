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
package com.dqops.checks.table.partitioned;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.partitioned.comparison.TableComparisonMonthlyPartitionedChecksSpecMap;
import com.dqops.checks.table.partitioned.sql.TableSqlMonthlyPartitionedChecksSpec;
import com.dqops.checks.table.partitioned.volume.TableVolumeMonthlyPartitionedChecksSpec;
import com.dqops.checks.table.partitioned.timeliness.TableTimelinessMonthlyPartitionedChecksSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
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
 * Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableMonthlyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableMonthlyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("timeliness", o -> o.timeliness);
            put("sql", o -> o.sql);
            put("comparisons", o -> o.comparisons);

            // accuracy checks are not supported on partitioned checks yet, but we support comparisons
        }
    };

    @JsonPropertyDescription("Volume monthly partitioned data quality checks that verify the quality of every month of data separately")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeMonthlyPartitionedChecksSpec volume;

    @JsonPropertyDescription("Monthly partitioned timeliness checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessMonthlyPartitionedChecksSpec timeliness;

    @JsonPropertyDescription("Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSqlMonthlyPartitionedChecksSpec sql;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableComparisonMonthlyPartitionedChecksSpecMap comparisons = new TableComparisonMonthlyPartitionedChecksSpecMap();


    /**
     * Returns the container of monthly partitioned checks for volume data quality checks.
     * @return Container of row volume data quality checks.
     */
    public TableVolumeMonthlyPartitionedChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets the container of volume data quality checks.
     * @param volume New volume checks.
     */
    public void setVolume(TableVolumeMonthlyPartitionedChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of table level timeliness partitioned checks.
     * @return Custom timeliness partitioned checks.
     */
    public TableTimelinessMonthlyPartitionedChecksSpec getTimeliness() {
        return timeliness;
    }

    /**
     * Sets a reference to a container of timeliness partitioned checks.
     * @param timeliness Custom timeliness partitioned checks.
     */
    public void setTimeliness(TableTimelinessMonthlyPartitionedChecksSpec timeliness) {
        this.setDirtyIf(!Objects.equals(this.timeliness, timeliness));
        this.timeliness = timeliness;
        this.propagateHierarchyIdToField(timeliness, "timeliness");
    }

    /**
     * Returns the container of custom SQL checks.
     * @return Custom sql checks.
     */
    public TableSqlMonthlyPartitionedChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Container of custom SQL checks.
     */
    public void setSql(TableSqlMonthlyPartitionedChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns the dictionary of comparisons.
     * @return Dictionary of comparisons.
     */
    @Override
    public TableComparisonMonthlyPartitionedChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the dictionary of comparisons.
     * @param comparisons Dictionary of comparisons.
     */
    public void setComparisons(TableComparisonMonthlyPartitionedChecksSpecMap comparisons) {
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
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.month);
            setTimestampColumn(tableSpec.getTimestampColumns().getPartitionByColumn());
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
        return CheckType.partitioned;
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
        return CheckRunScheduleGroup.partitioned_monthly;
    }
}
