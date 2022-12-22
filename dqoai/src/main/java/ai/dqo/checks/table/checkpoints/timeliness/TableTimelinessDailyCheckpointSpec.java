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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentIngestionCheckSpec;
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
 * Container of table level daily checkpoints for standard data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_checkpoint_max_days_since_most_recent_event", o -> o.dailyCheckpointMaxDaysSinceMostRecentEvent);
           put("daily_checkpoint_max_days_since_most_recent_ingestion", o -> o.dailyCheckpointMaxDaysSinceMostRecentIngestion);
           put("daily_checkpoint_max_delay_in_data_loading_in_days", o -> o.dailyCheckpointMaxDelayInDataLoadingInDays);
           put("daily_checkpoint_days_since_last_load", o -> o.dailyCheckpointDaysSinceLastLoad);
        }
    };

    @JsonPropertyDescription("Daily checkpoint calculating the maximum days since the most recent event timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily checkpoint calculating the maximum days since the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the maximum event timestamp (the most recent transaction timestamp) and the maximum ingestion timestamp (the most recent data loading timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDelayInDataLoadingInDaysCheckSpec dailyCheckpointMaxDelayInDataLoadingInDays;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the current date and the maximum ingestion timestamp (the most recent data loading timestamp) (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceLastLoadCheckSpec dailyCheckpointDaysSinceLastLoad;

    /**
     * Returns a maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getDailyCheckpointMaxDaysSinceMostRecentEvent() {
        return dailyCheckpointMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets a maximum days since the most recent event.
     * @param dailyCheckpointMaxDaysSinceMostRecentEvent New maximum days since the most recent event check.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentEvent, dailyCheckpointMaxDaysSinceMostRecentEvent));
        this.dailyCheckpointMaxDaysSinceMostRecentEvent = dailyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentEvent, "daily_checkpoint_max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum days since the most recent ingestion check configuration.
     * @return Maximum days since the most recent ingestion check configuration.
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getDailyCheckpointMaxDaysSinceMostRecentIngestion() {
        return dailyCheckpointMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a maximum days since the most recent ingestion.
     * @param dailyCheckpointMaxDaysSinceMostRecentIngestion New maximum days since the most recent ingestion check.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentIngestion, dailyCheckpointMaxDaysSinceMostRecentIngestion));
        this.dailyCheckpointMaxDaysSinceMostRecentIngestion = dailyCheckpointMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentIngestion, "daily_checkpoint_max_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum delay in data loading in days check configuration.
     * @return Maximum delay in data loading in days check configuration.
     */
    public TableMaxDelayInDataLoadingInDaysCheckSpec getDailyCheckpointMaxDelayInDataLoadingInDays() {
        return dailyCheckpointMaxDelayInDataLoadingInDays;
    }

    /**
     * Sets a maximum delay in data loading in days check configuration.
     * @param dailyCheckpointMaxDelayInDataLoadingInDays Maximum delay in data loading in days check configuration.
     */
    public void setDailyCheckpointMaxDelayInDataLoadingInDays(TableMaxDelayInDataLoadingInDaysCheckSpec dailyCheckpointMaxDelayInDataLoadingInDays) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDelayInDataLoadingInDays, dailyCheckpointMaxDelayInDataLoadingInDays));
        this.dailyCheckpointMaxDelayInDataLoadingInDays = dailyCheckpointMaxDelayInDataLoadingInDays;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDelayInDataLoadingInDays, "daily_checkpoint_max_delay_in_data_loading_in_days");
    }

    /**
     * Returns a number of days since the last load check configuration.
     * @return A number of days since the last load check configuration..
     */
    public TableDaysSinceLastLoadCheckSpec getDailyCheckpointDaysSinceLastLoad() {
        return dailyCheckpointDaysSinceLastLoad;
    }

    /**
     * Sets a number of days since the last load check configuration.
     * @param dailyCheckpointDaysSinceLastLoad A number of days since the last load check configuration.
     */
    public void setDailyCheckpointDaysSinceLastLoad(TableDaysSinceLastLoadCheckSpec dailyCheckpointDaysSinceLastLoad) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDaysSinceLastLoad, dailyCheckpointDaysSinceLastLoad));
        this.dailyCheckpointDaysSinceLastLoad = dailyCheckpointDaysSinceLastLoad;
        this.propagateHierarchyIdToField(dailyCheckpointDaysSinceLastLoad, "daily_checkpoint_days_since_last_load");
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
