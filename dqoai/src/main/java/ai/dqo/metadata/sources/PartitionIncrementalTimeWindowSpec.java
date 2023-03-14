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

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Configuration of the time window for running incremental partition checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class PartitionIncrementalTimeWindowSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<PartitionIncrementalTimeWindowSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Number of recent days that are analyzed by daily partitioned checks in incremental mode. The default value is 7 days back.")
    private Integer dailyPartitioningRecentDays = 7;

    @JsonPropertyDescription("Analyze also today's data by daily partitioned checks in incremental mode. The default value is false, which means that the today's and the future partitions are not analyzed, only yesterday's partition and earlier daily partitions are analyzed because today's data could be still incomplete. Change the value to 'true' if the current day should be also analyzed. The change may require configuring the schedule for daily checks correctly, to run after the data load.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean dailyPartitioningIncludeToday;

    @JsonPropertyDescription("Number of recent months that are analyzed by monthly partitioned checks in incremental mode. The default value is 1 month back which means the previous calendar month.")
    private Integer monthlyPartitioningRecentMonths = 1;

    @JsonPropertyDescription("Analyze also this month's data by monthly partitioned checks in incremental mode. The default value is false, which means that the current month is not analyzed and future data is also filtered out because the current month could be incomplete. Set the value to 'true' if the current month should be analyzed before the end of the month. The schedule for running monthly checks should be also configured to run more frequently (daily, hourly, etc.).")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean monthlyPartitioningIncludeCurrentMonth;

    /**
     * Returns the number of recent days that are analyzed by daily partitioned checks in incremental mode.
     * @return Number of recent days that are analyzed by daily partitioned checks in incremental mode.
     */
    public Integer getDailyPartitioningRecentDays() {
        return dailyPartitioningRecentDays;
    }

    /**
     * Sets the number of recent days that are analyzed by daily partitioned checks in incremental mode.
     * @param dailyPartitioningRecentDays Number of recent days that are analyzed by daily partitioned checks in incremental mode.
     */
    public void setDailyPartitioningRecentDays(Integer dailyPartitioningRecentDays) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitioningRecentDays, dailyPartitioningRecentDays));
        this.dailyPartitioningRecentDays = dailyPartitioningRecentDays;
    }

    /**
     * Returns a switch to analyze also today's data by daily partitioned checks in incremental mode.
     * @return True - analyze also today's data by daily partitioned checks in incremental mode.
     */
    public boolean isDailyPartitioningIncludeToday() {
        return dailyPartitioningIncludeToday;
    }

    /**
     * Sets the switch to analyze also today's data by daily partitioned checks in incremental mode.
     * @param dailyPartitioningIncludeToday True - analyze also today's data by daily partitioned checks in incremental mode.
     */
    public void setDailyPartitioningIncludeToday(boolean dailyPartitioningIncludeToday) {
        this.setDirtyIf(this.dailyPartitioningIncludeToday != dailyPartitioningIncludeToday);
        this.dailyPartitioningIncludeToday = dailyPartitioningIncludeToday;
    }

    /**
     * Returns the number of recent months that are analyzed by monthly partitioned checks in incremental mode.
     * @return Number of recent months that are analyzed by monthly partitioned checks in incremental mode.
     */
    public Integer getMonthlyPartitioningRecentMonths() {
        return monthlyPartitioningRecentMonths;
    }

    /**
     * Sets the number of recent months that are analyzed by monthly partitioned checks in incremental mode.
     * @param monthlyPartitioningRecentMonths Number of recent months that are analyzed by monthly partitioned checks in incremental mode.
     */
    public void setMonthlyPartitioningRecentMonths(Integer monthlyPartitioningRecentMonths) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitioningRecentMonths, monthlyPartitioningRecentMonths));
        this.monthlyPartitioningRecentMonths = monthlyPartitioningRecentMonths;
    }

    /**
     * Returns a switch to analyze also this month's data by monthly partitioned checks in incremental mode.
     * @return True - analyze also this month's data by monthly partitioned checks in incremental mode.
     */
    public boolean isMonthlyPartitioningIncludeCurrentMonth() {
        return monthlyPartitioningIncludeCurrentMonth;
    }

    /**
     * Sets the switch to analyze also this month's data by monthly partitioned checks in incremental mode.
     * @param monthlyPartitioningIncludeCurrentMonth True - analyze also this month's data by monthly partitioned checks in incremental mode.
     */
    public void setMonthlyPartitioningIncludeCurrentMonth(boolean monthlyPartitioningIncludeCurrentMonth) {
        this.setDirtyIf(this.monthlyPartitioningIncludeCurrentMonth != monthlyPartitioningIncludeCurrentMonth);
        this.monthlyPartitioningIncludeCurrentMonth = monthlyPartitioningIncludeCurrentMonth;
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
    public PartitionIncrementalTimeWindowSpec deepClone() {
        PartitionIncrementalTimeWindowSpec cloned = (PartitionIncrementalTimeWindowSpec) super.deepClone();
        return cloned;
    }
}
