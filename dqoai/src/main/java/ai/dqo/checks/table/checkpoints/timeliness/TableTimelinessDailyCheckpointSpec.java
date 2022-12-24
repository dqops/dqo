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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentIngestionCheckSpec;
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
           put("daily_checkpoint_max_days_since_most_recent_event", o -> o.dailyCheckpointMaxDaysSinceMostRecentEvent);
           put("daily_checkpoint_max_data_ingestion_delay", o -> o.dailyCheckpointMaxDataIngestionDelay);
           put("daily_checkpoint_max_days_since_most_recent_ingestion", o -> o.dailyCheckpointMaxDaysSinceMostRecentIngestion);
        }
    };

    @JsonPropertyDescription("Daily checkpoint calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDataIngestionDelayCheckSpec dailyCheckpointMaxDataIngestionDelay;

    @JsonPropertyDescription("Daily checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getDailyCheckpointMaxDaysSinceMostRecentEvent() {
        return dailyCheckpointMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param dailyCheckpointMaxDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentEvent, dailyCheckpointMaxDaysSinceMostRecentEvent));
        this.dailyCheckpointMaxDaysSinceMostRecentEvent = dailyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentEvent, "daily_checkpoint_max_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableMaxDataIngestionDelayCheckSpec getDailyCheckpointMaxDataIngestionDelay() {
        return dailyCheckpointMaxDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyCheckpointMaxDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyCheckpointMaxDataIngestionDelay(TableMaxDataIngestionDelayCheckSpec dailyCheckpointMaxDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDataIngestionDelay, dailyCheckpointMaxDataIngestionDelay));
        this.dailyCheckpointMaxDataIngestionDelay = dailyCheckpointMaxDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDataIngestionDelay, "daily_checkpoint_max_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getDailyCheckpointMaxDaysSinceMostRecentIngestion() {
        return dailyCheckpointMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dailyCheckpointMaxDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentIngestion, dailyCheckpointMaxDaysSinceMostRecentIngestion));
        this.dailyCheckpointMaxDaysSinceMostRecentIngestion = dailyCheckpointMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentIngestion, "daily_checkpoint_max_days_since_most_recent_ingestion");
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
