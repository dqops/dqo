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
package com.dqops.metadata.scheduling;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Container of all monitoring schedules (cron expressions) for each type of checks.
 * Data quality checks are grouped by type (profiling, whole table checks, time period partitioned checks).
 * Each group of checks could be divided additionally by time scale (daily, monthly, etc).
 * Each time scale has a different monitoring schedule used by the job scheduler to run the checks.
 * These schedules are defined in this object.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class MonitoringSchedulesSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<MonitoringSchedulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("profiling", o -> o.profiling);
            put("monitoring_daily", o -> o.monitoringDaily);
            put("monitoring_monthly", o -> o.monitoringMonthly);
            put("partitioned_daily", o -> o.partitionedDaily);
            put("partitioned_monthly", o -> o.partitionedMonthly);
        }
    };

    @JsonPropertyDescription("Schedule for running profiling data quality checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MonitoringScheduleSpec profiling;

    @JsonPropertyDescription("Schedule for running daily monitoring checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MonitoringScheduleSpec monitoringDaily;

    @JsonPropertyDescription("Schedule for running monthly monitoring checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MonitoringScheduleSpec monitoringMonthly;

    @JsonPropertyDescription("Schedule for running daily partitioned checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MonitoringScheduleSpec partitionedDaily;

    @JsonPropertyDescription("Schedule for running monthly partitioned checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MonitoringScheduleSpec partitionedMonthly;

    /**
     * Returns the schedule for running profiling data quality checks
     * @return Schedule for running profiling.
     */
    public MonitoringScheduleSpec getProfiling() {
        return profiling;
    }

    /**
     * Sets the configuration of the schedule for running profiling.
     * @param profiling Configuration of the profiling schedule.
     */
    public void setProfiling(MonitoringScheduleSpec profiling) {
        this.setDirtyIf(!Objects.equals(this.profiling, profiling));
        this.profiling = profiling;
        propagateHierarchyIdToField(profiling, CheckRunScheduleGroup.profiling.name());
    }

    /**
     * Returns the configuration of the schedule for running daily checks.
     * @return Configuration of daily checks.
     */
    public MonitoringScheduleSpec getMonitoringDaily() {
        return monitoringDaily;
    }

    /**
     * Sets the schedule for running daily checks.
     * @param monitoringDaily Configuration of schedule for daily checks.
     */
    public void setMonitoringDaily(MonitoringScheduleSpec monitoringDaily) {
        this.setDirtyIf(!Objects.equals(this.monitoringDaily, monitoringDaily));
        this.monitoringDaily = monitoringDaily;
        propagateHierarchyIdToField(monitoringDaily, CheckRunScheduleGroup.monitoring_daily.name());
    }

    /**
     * Returns the configuration of the schedule for running monthly checks.
     * @return Configuration of monthly checks.
     */
    public MonitoringScheduleSpec getMonitoringMonthly() {
        return monitoringMonthly;
    }

    /**
     * Sets the schedule for running monthly checks.
     * @param monitoringMonthly Configuration of schedule for monthly checks.
     */
    public void setMonitoringMonthly(MonitoringScheduleSpec monitoringMonthly) {
        this.setDirtyIf(!Objects.equals(this.monitoringMonthly, monitoringMonthly));
        this.monitoringMonthly = monitoringMonthly;
        propagateHierarchyIdToField(monitoringMonthly, CheckRunScheduleGroup.monitoring_monthly.name());
    }

    /**
     * Returns the configuration of the schedule for running daily partitioned checks.
     * @return Configuration of daily partitioned checks.
     */
    public MonitoringScheduleSpec getPartitionedDaily() {
        return partitionedDaily;
    }

    /**
     * Sets the schedule for running daily partitioned checks.
     * @param partitionedDaily Configuration of schedule for daily partitioned checks.
     */
    public void setPartitionedDaily(MonitoringScheduleSpec partitionedDaily) {
        this.setDirtyIf(!Objects.equals(this.partitionedDaily, partitionedDaily));
        this.partitionedDaily = partitionedDaily;
        propagateHierarchyIdToField(partitionedDaily, CheckRunScheduleGroup.partitioned_daily.name());
    }

    /**
     * Returns the configuration of the schedule for running monthly partitioned checks.
     * @return Configuration of monthly partitioned checks.
     */
    public MonitoringScheduleSpec getPartitionedMonthly() {
        return partitionedMonthly;
    }

    /**
     * Sets the schedule for running monthly partitioned checks.
     * @param partitionedMonthly Configuration of schedule for monthly partitioned checks.
     */
    public void setPartitionedMonthly(MonitoringScheduleSpec partitionedMonthly) {
        this.setDirtyIf(!Objects.equals(this.partitionedMonthly, partitionedMonthly));
        this.partitionedMonthly = partitionedMonthly;
        propagateHierarchyIdToField(partitionedMonthly, CheckRunScheduleGroup.partitioned_monthly.name());
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
     * Returns the configuration of schedule for a given scheduling group (type of checks that share a schedule configuration).
     * @param checkSchedulingGroup Check run scheduling group.
     * @return Configuration of a monitoring schedule (cron expression) for the given scheduling group.
     */
    public MonitoringScheduleSpec getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup checkSchedulingGroup) {
        return (MonitoringScheduleSpec)this.getChild(checkSchedulingGroup.name());
    }

    /**
     * Sets the configuration of schedule for a given scheduling group (type of checks that share a schedule configuration).
     * @param newScheduleSpec Schedule specification to be replaces in the MonitoringSchedulesSpec object.
     * @param schedulingGroup Check run scheduling group.
     */
    public void setScheduleForCheckSchedulingGroup(MonitoringScheduleSpec newScheduleSpec,
                                                   CheckRunScheduleGroup schedulingGroup) {
        switch (schedulingGroup) {
            case profiling:
                setProfiling(newScheduleSpec);
                break;

            case monitoring_daily:
                setMonitoringDaily(newScheduleSpec);
                break;

            case monitoring_monthly:
                setMonitoringMonthly(newScheduleSpec);
                break;

            case partitioned_daily:
                setPartitionedDaily(newScheduleSpec);
                break;

            case partitioned_monthly:
                setPartitionedMonthly(newScheduleSpec);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported scheduling group " + schedulingGroup);
        }
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public MonitoringSchedulesSpec deepClone() {
        return (MonitoringSchedulesSpec)super.deepClone();
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Trimmed and expanded version of this object.
     */
    public MonitoringSchedulesSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        MonitoringSchedulesSpec cloned = (MonitoringSchedulesSpec) super.deepClone();
        if (cloned.profiling != null) {
            cloned.setProfiling(cloned.profiling.expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        if (cloned.monitoringDaily != null) {
            cloned.setMonitoringDaily(cloned.monitoringDaily.expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        if (cloned.monitoringMonthly != null) {
            cloned.setMonitoringMonthly(cloned.monitoringMonthly.expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        if (cloned.partitionedDaily != null) {
            cloned.setPartitionedDaily(cloned.partitionedDaily.expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        if (cloned.partitionedMonthly != null) {
            cloned.setPartitionedMonthly(cloned.partitionedMonthly.expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        return cloned;
    }

}
