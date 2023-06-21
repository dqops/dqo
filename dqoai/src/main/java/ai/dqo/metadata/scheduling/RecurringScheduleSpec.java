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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Recurring job schedule specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class RecurringScheduleSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<RecurringScheduleSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    public RecurringScheduleSpec() {
    }

    /**
     * Creates a recurring schedule given a unix cron expression.
     * @param cronExpression Unix style cron expression.
     */
    public RecurringScheduleSpec(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @JsonPropertyDescription("Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String cronExpression;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Disables the schedule. When the value of this 'disable' field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.")
    private boolean disabled;

    /**
     * Returns the cron expression.
     * @return Cron expression.
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets the cron expression.
     * @param cronExpression Cron expression.
     */
    public void setCronExpression(String cronExpression) {
        setDirtyIf(!Objects.equals(this.cronExpression, cronExpression));
        this.cronExpression = cronExpression;
    }

    /**
     * When true, the schedule is disabled.
     * @return True when the schedule is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the 'disable' flag to pause the schedule.
     * @param disabled Disable the schedule.
     */
    public void setDisabled(boolean disabled) {
        setDirtyIf(disabled != this.disabled);
        this.disabled = disabled;
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
     * Creates and returns a copy of this object.
     */
    @Override
    public RecurringScheduleSpec deepClone() {
        RecurringScheduleSpec cloned = (RecurringScheduleSpec) super.deepClone();
        return cloned;
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        if (!Strings.isNullOrEmpty(this.cronExpression) || this.disabled) {
            return false;
        }

        return true;
    }
}
