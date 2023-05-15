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
package ai.dqo.checks.table.recurring.timeliness;
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
 * Container of table level daily recurring for timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_days_since_most_recent_event", o -> o.dailyDaysSinceMostRecentEvent);
           put("daily_data_ingestion_delay", o -> o.dailyDataIngestionDelay);
           put("daily_days_since_most_recent_ingestion", o -> o.dailyDaysSinceMostRecentIngestion);
        }
    };

    @JsonPropertyDescription("Daily  calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentEventCheckSpec dailyDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec dailyDataIngestionDelay;

    @JsonPropertyDescription("Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentIngestionCheckSpec dailyDaysSinceMostRecentIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getDailyDaysSinceMostRecentEvent() {
        return dailyDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param dailyDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setDailyDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec dailyDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.dailyDaysSinceMostRecentEvent, dailyDaysSinceMostRecentEvent));
        this.dailyDaysSinceMostRecentEvent = dailyDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(dailyDaysSinceMostRecentEvent, "daily_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDailyDataIngestionDelay() {
        return dailyDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyDataIngestionDelay(TableDataIngestionDelayCheckSpec dailyDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyDataIngestionDelay, dailyDataIngestionDelay));
        this.dailyDataIngestionDelay = dailyDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyDataIngestionDelay, "daily_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDaysSinceMostRecentIngestionCheckSpec getDailyDaysSinceMostRecentIngestion() {
        return dailyDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dailyDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setDailyDaysSinceMostRecentIngestion(TableDaysSinceMostRecentIngestionCheckSpec dailyDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyDaysSinceMostRecentIngestion, dailyDaysSinceMostRecentIngestion));
        this.dailyDaysSinceMostRecentIngestion = dailyDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyDaysSinceMostRecentIngestion, "daily_days_since_most_recent_ingestion");
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
