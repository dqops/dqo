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
import ai.dqo.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentIngestionCheckSpec;
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
 * Container of table level daily checkpoints for timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_checkpoint_days_since_most_recent_event", o -> o.dailyCheckpointDaysSinceMostRecentEvent);
           put("daily_checkpoint_data_ingestion_delay", o -> o.dailyCheckpointDataIngestionDelay);
           put("daily_checkpoint_days_since_most_recent_ingestion", o -> o.dailyCheckpointDaysSinceMostRecentIngestion);
        }
    };

    @JsonPropertyDescription("Daily checkpoint calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentEventCheckSpec dailyCheckpointDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec dailyCheckpointDataIngestionDelay;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentIngestionCheckSpec dailyCheckpointDaysSinceMostRecentIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getDailyCheckpointDaysSinceMostRecentEvent() {
        return dailyCheckpointDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param dailyCheckpointDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setDailyCheckpointDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec dailyCheckpointDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.dailyCheckpointDaysSinceMostRecentEvent, dailyCheckpointDaysSinceMostRecentEvent));
        this.dailyCheckpointDaysSinceMostRecentEvent = dailyCheckpointDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(dailyCheckpointDaysSinceMostRecentEvent, "daily_checkpoint_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDailyCheckpointDataIngestionDelay() {
        return dailyCheckpointDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyCheckpointDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyCheckpointDataIngestionDelay(TableDataIngestionDelayCheckSpec dailyCheckpointDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDataIngestionDelay, dailyCheckpointDataIngestionDelay));
        this.dailyCheckpointDataIngestionDelay = dailyCheckpointDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyCheckpointDataIngestionDelay, "daily_checkpoint_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDaysSinceMostRecentIngestionCheckSpec getDailyCheckpointDaysSinceMostRecentIngestion() {
        return dailyCheckpointDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dailyCheckpointDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setDailyCheckpointDaysSinceMostRecentIngestion(TableDaysSinceMostRecentIngestionCheckSpec dailyCheckpointDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDaysSinceMostRecentIngestion, dailyCheckpointDaysSinceMostRecentIngestion));
        this.dailyCheckpointDaysSinceMostRecentIngestion = dailyCheckpointDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointDaysSinceMostRecentIngestion, "daily_checkpoint_days_since_most_recent_ingestion");
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
