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
package ai.dqo.checks.column.partitioned;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.partitioned.numeric.ColumnNumericDailyPartitionedChecksSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.groupings.TimeSeriesMode;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
 * Container of column level daily partitioned checks. Contains categories of data quality checks that are executed for daily partitionsColumnNullsDailyPartitionedChecksSpec.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
           put("daily_partition_max_negative_count", o -> o.dailyPartitionMaxNegativeCount);
        }
    };

    @JsonPropertyDescription("Daily partitioned checks of negative values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyPartitionedChecksSpec dailyPartitionMaxNegativeCount;

    /**
     * Returns the container of daily negative data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnNumericDailyPartitionedChecksSpec getDailyPartitionMaxNegativeCount() {
        return dailyPartitionMaxNegativeCount;
    }

    /**
     * Sets the container of daily negative data quality partitioned checks.
     * @param dailyPartitionMaxNegativeCount New negative checks.
     */
    public void setDailyPartitionMaxNegativeCount(ColumnNumericDailyPartitionedChecksSpec dailyPartitionMaxNegativeCount) {
		this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxNegativeCount, dailyPartitionMaxNegativeCount));
        this.dailyPartitionMaxNegativeCount = dailyPartitionMaxNegativeCount;
        propagateHierarchyIdToField(dailyPartitionMaxNegativeCount, "daily_partition_max_negative_count");
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
            setTimeGradient(TimeSeriesGradient.DAY);
            setTimestampColumn(tableSpec.getTimestampColumns().getEffectivePartitioningColumn());
        }};
    }

    /**
     * Returns the type of checks (adhoc, checkpoint, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.PARTITIONED;
    }

    /**
     * Returns the time range for checkpoint and partitioned checks (daily, monthly, etc.).
     * Adhoc checks do not have a time range and return null.
     *
     * @return Time range (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }
}
