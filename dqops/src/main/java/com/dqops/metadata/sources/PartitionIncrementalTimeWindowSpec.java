/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.core.jobqueue.jobs.table.ImportTablesResult;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import tech.tablesaw.api.Table;

import java.util.List;
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

    @JsonPropertyDescription("The number of recent days analyzed by daily partition checks in incremental mode. The default value is 7 last days.")
    private Integer dailyPartitioningRecentDays = 7;

    @JsonPropertyDescription("Analyze also today's data by daily partition checks in incremental mode. The default value is false, which means that the today's and the future partitions are not analyzed. Only yesterday's partition and previous daily partitions are analyzed because today's data may still be incomplete. Change the value to 'true' if the current day should also be analyzed. This change may require you to configure the schedule for daily checks correctly. The checks must run after the data load.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean dailyPartitioningIncludeToday;

    @JsonPropertyDescription("The number of recent days analyzed by monthly partition checks in incremental mode. The default value is the previous calendar month.")
    private Integer monthlyPartitioningRecentMonths = 1;

    @JsonPropertyDescription("Analyze also this month's data by monthly partition checks in incremental mode. The default value is false, which means that the current month is not analyzed. Future data is also filtered out because the current month may be incomplete. Change the value to 'true' if the current month should also be analyzed before the end of the month. This change may require you to configure the schedule to run monthly checks more frequently (daily, hourly, etc.).")
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

    public static class PartitionIncrementalTimeWindowSpecSampleFactory implements SampleValueFactory<PartitionIncrementalTimeWindowSpec> {
        @Override
        public PartitionIncrementalTimeWindowSpec createSample() {
            PartitionIncrementalTimeWindowSpec result = new PartitionIncrementalTimeWindowSpec();
            result.dailyPartitioningIncludeToday = true;
            result.monthlyPartitioningRecentMonths = null;
            return result;
        }
    }
}
