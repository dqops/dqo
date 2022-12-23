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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDelayInDataLoadingInDaysCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceLastLoadCheckSpec;
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
           put("monthly_checkpoint_max_delay_in_data_loading_in_days", o -> o.monthlyCheckpointMaxDelayInDataLoadingInDays);
           put("monthly_checkpoint_days_since_last_load", o -> o.monthlyCheckpointDaysSinceLastLoad);
        }
    };

    @JsonPropertyDescription("Monthly checkpoint calculating maximum days since the most recent event timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the maximum event timestamp (the most recent transaction timestamp) and the maximum ingestion timestamp (the most recent data loading timestamp)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDelayInDataLoadingInDaysCheckSpec monthlyCheckpointMaxDelayInDataLoadingInDays;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the current date and the maximum ingestion timestamp (the most recent data loading timestamp) (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceLastLoadCheckSpec monthlyCheckpointDaysSinceLastLoad;

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
    public void setMonthlyCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDaysSinceMostRecentEvent, monthlyCheckpointMaxDaysSinceMostRecentEvent));
        this.monthlyCheckpointMaxDaysSinceMostRecentEvent = monthlyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyCheckpointMaxDaysSinceMostRecentEvent, "monthly_checkpoint_max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum delay in data loading in days check configuration.
     * @return Maximum delay in data loading in days check configuration.
     */
    public TableMaxDelayInDataLoadingInDaysCheckSpec getMonthlyCheckpointMaxDelayInDataLoadingInDays() {
        return monthlyCheckpointMaxDelayInDataLoadingInDays;
    }

    /**
     * Sets a maximum delay in data loading in days check configuration.
     * @param monthlyCheckpointMaxDelayInDataLoadingInDays Maximum delay in data loading in days check configuration.
     */
    public void setMonthlyCheckpointMaxDelayInDataLoadingInDays(TableMaxDelayInDataLoadingInDaysCheckSpec monthlyCheckpointMaxDelayInDataLoadingInDays) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDelayInDataLoadingInDays, monthlyCheckpointMaxDelayInDataLoadingInDays));
        this.monthlyCheckpointMaxDelayInDataLoadingInDays = monthlyCheckpointMaxDelayInDataLoadingInDays;
        this.propagateHierarchyIdToField(monthlyCheckpointMaxDelayInDataLoadingInDays, "monthly_checkpoint_max_delay_in_data_loading_in_days");
    }

    /**
     * Returns a number of days since the last load check configuration.
     * @return A number of days since the last load check configuration..
     */
    public TableDaysSinceLastLoadCheckSpec getMonthlyCheckpointDaysSinceLastLoad() {
        return monthlyCheckpointDaysSinceLastLoad;
    }

    /**
     * Sets a number of days since the last load check configuration.
     * @param monthlyCheckpointDaysSinceLastLoad A number of days since the last load check configuration.
     */
    public void setMonthlyCheckpointDaysSinceLastLoad(TableDaysSinceLastLoadCheckSpec monthlyCheckpointDaysSinceLastLoad) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDaysSinceLastLoad, monthlyCheckpointDaysSinceLastLoad));
        this.monthlyCheckpointDaysSinceLastLoad = monthlyCheckpointDaysSinceLastLoad;
        this.propagateHierarchyIdToField(monthlyCheckpointDaysSinceLastLoad, "monthly_checkpoint_days_since_last_load");
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
