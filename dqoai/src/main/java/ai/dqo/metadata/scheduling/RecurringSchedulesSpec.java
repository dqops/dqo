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
package ai.dqo.metadata.scheduling;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Container of all recurring schedules (cron expressions) for each type of checks.
 * Data quality checks are grouped by type (profiling, whole table checks, time period partitioned checks).
 * Each group of checks could be divided additionally by time scale (daily, monthly, etc).
 * Each time scale has a different recurring schedule used by the job scheduler to run the checks.
 * These schedules are defined in this object.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class RecurringSchedulesSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<RecurringSchedulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("profiling", o -> o.profiling);
            put("daily", o -> o.daily);
            put("monthly", o -> o.monthly);
        }
    };

    @JsonPropertyDescription("Schedule for running profiling data quality checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec profiling;

    @JsonPropertyDescription("Schedule for running daily whole table checks and day level time period checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec daily;

    @JsonPropertyDescription("Schedule for running monthly whole table checks and month level time period checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec monthly;

    /**
     * Returns the schedule for running profiling data quality checks
     * @return Schedule for running profiling.
     */
    public RecurringScheduleSpec getProfiling() {
        return profiling;
    }

    /**
     * Sets the configuration of the schedule for running profiling.
     * @param profiling Configuration of the profiling schedule.
     */
    public void setProfiling(RecurringScheduleSpec profiling) {
        this.setDirtyIf(!Objects.equals(this.profiling, profiling));
        this.profiling = profiling;
        propagateHierarchyIdToField(profiling, CheckRunRecurringScheduleGroup.profiling);
    }

    /**
     * Returns the configuration of the schedule for running daily checks.
     * @return Configuration of daily checks.
     */
    public RecurringScheduleSpec getDaily() {
        return daily;
    }

    /**
     * Sets the schedule for running daily checks.
     * @param daily Configuration of schedule for daily checks.
     */
    public void setDaily(RecurringScheduleSpec daily) {
        this.setDirtyIf(!Objects.equals(this.daily, daily));
        this.daily = daily;
        propagateHierarchyIdToField(daily, CheckRunRecurringScheduleGroup.daily);
    }

    /**
     * Returns the configuration of the schedule for running monthly checks.
     * @return Configuration of monthly checks.
     */
    public RecurringScheduleSpec getMonthly() {
        return monthly;
    }

    /**
     * Sets the schedule for running monthly checks.
     * @param monthly Configuration of schedule for monthly checks.
     */
    public void setMonthly(RecurringScheduleSpec monthly) {
        this.setDirtyIf(!Objects.equals(this.monthly, monthly));
        this.monthly = monthly;
        propagateHierarchyIdToField(monthly, CheckRunRecurringScheduleGroup.monthly);
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
     * @return Configuration of a recurring schedule (cron expression) for the given scheduling group.
     */
    public RecurringScheduleSpec getScheduleForCheckSchedulingGroup(CheckRunRecurringScheduleGroup checkSchedulingGroup) {
        return (RecurringScheduleSpec)this.getChild(checkSchedulingGroup);
    }
}
