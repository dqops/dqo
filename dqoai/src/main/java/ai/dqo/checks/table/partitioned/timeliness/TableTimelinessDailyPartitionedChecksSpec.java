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
package ai.dqo.checks.table.partitioned.timeliness;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxRowDataIngestionDelayCheckSpec;
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
 * Container of table level date partitioned timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_partition_days_since_most_recent_event", o -> o.dailyPartitionDaysSinceMostRecentEvent);
           put("daily_partition_data_ingestion_delay", o -> o.dailyPartitionDataIngestionDelay);
           put("daily_partition_days_since_most_recent_ingestion", o -> o.dailyPartitionDaysSinceMostRecentIngestion);
           put("daily_partition_max_row_data_ingestion_delay", o -> o.dailyPartitionMaxRowDataIngestionDelay);
        }
    };

    @JsonPropertyDescription("Daily partition checkpoint calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentEventCheckSpec dailyPartitionDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec dailyPartitionDataIngestionDelay;

    @JsonPropertyDescription("Daily partition checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentIngestionCheckSpec dailyPartitionDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Daily partition checkpoint calculating the longest time a row waited to be load")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxRowDataIngestionDelayCheckSpec dailyPartitionMaxRowDataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return The number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getDailyPartitionDaysSinceMostRecentEvent() {
        return dailyPartitionDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param dailyPartitionDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setDailyPartitionDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec dailyPartitionDaysSinceMostRecentEvent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDaysSinceMostRecentEvent, dailyPartitionDaysSinceMostRecentEvent));
        this.dailyPartitionDaysSinceMostRecentEvent = dailyPartitionDaysSinceMostRecentEvent;
        this.propagateHierarchyIdToField(dailyPartitionDaysSinceMostRecentEvent, "daily_partition_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDailyPartitionDataIngestionDelay() {
        return dailyPartitionDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyPartitionDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyPartitionDataIngestionDelay(TableDataIngestionDelayCheckSpec dailyPartitionDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDataIngestionDelay, dailyPartitionDataIngestionDelay));
        this.dailyPartitionDataIngestionDelay = dailyPartitionDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyPartitionDataIngestionDelay, "daily_partition_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDaysSinceMostRecentIngestionCheckSpec getDailyPartitionDaysSinceMostRecentIngestion() {
        return dailyPartitionDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dailyPartitionDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setDailyPartitionDaysSinceMostRecentIngestion(TableDaysSinceMostRecentIngestionCheckSpec dailyPartitionDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDaysSinceMostRecentIngestion, dailyPartitionDaysSinceMostRecentIngestion));
        this.dailyPartitionDaysSinceMostRecentIngestion = dailyPartitionDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyPartitionDaysSinceMostRecentIngestion, "daily_partition_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum row data ingestion delay check configuration.
     * @return A maximum row data ingestion delay check configuration.
     */
    public TableMaxRowDataIngestionDelayCheckSpec getDailyPartitionMaxRowDataIngestionDelay() {
        return dailyPartitionMaxRowDataIngestionDelay;
    }

    /**
     * Sets a maximum row data ingestion delay.
     * @param dailyPartitionMaxRowDataIngestionDelay New maximum row data ingestion delay.
     */
    public void setDailyPartitionMaxRowDataIngestionDelay(TableMaxRowDataIngestionDelayCheckSpec dailyPartitionMaxRowDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxRowDataIngestionDelay, dailyPartitionMaxRowDataIngestionDelay));
        this.dailyPartitionMaxRowDataIngestionDelay = dailyPartitionMaxRowDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyPartitionMaxRowDataIngestionDelay, "daily_partition_max_row_data_ingestion_delay");
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
