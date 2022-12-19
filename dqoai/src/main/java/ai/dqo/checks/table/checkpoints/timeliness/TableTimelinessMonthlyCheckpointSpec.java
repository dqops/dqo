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
package ai.dqo.checks.table.checkpoints.timeliness;
import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentEventCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level monthly checkpoints for standard data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_checkpoint_max_days_since_most_recent_event", o -> o.monthlyCheckpointMaxDaysSinceMostRecentEvent);
        }
    };

    @JsonPropertyDescription("Monthly checkpoint calculating maximum days since the most recent event")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent;

    /**
     * Returns the maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getMonthlyCheckpointMaxDaysSinceMostRecentEvent() {
        return monthlyCheckpointMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets the maximum days since the most recent event.
     * @param monthlyCheckpointMaxDaysSinceMostRecentEvent New maximum days since the most recent event check.
     */
    public void setMonthlyCheckpointCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDaysSinceMostRecentEvent, monthlyCheckpointMaxDaysSinceMostRecentEvent));
        this.monthlyCheckpointMaxDaysSinceMostRecentEvent = monthlyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyCheckpointMaxDaysSinceMostRecentEvent, "monthly_checkpoint_max_days_since_most_recent_event");
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
}
