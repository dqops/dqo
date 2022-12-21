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
 * Container of table level date partitioned standard data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_partition_max_days_since_most_recent_event", o -> o.dailyPartitionMaxDaysSinceMostRecentEvent);
           put("daily_partition_max_days_since_most_recent_ingestion", o -> o.dailyPartitionMaxDaysSinceMostRecentIngestion);
           put("daily_partition_max_delay_in_data_loading_in_days", o -> o.dailyPartitionMaxDelayInDataLoadingInDays);
           put("daily_partition_days_since_last_load", o -> o.dailyPartitionDaysSinceLastLoad);
        }
    };

    @JsonPropertyDescription("Daily partition checkpoint calculating the maximum days since the most recent event timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec dailyPartitionMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Daily partition checkpoint calculating the maximum days since the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec dailyPartitionMaxDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Daily partition checkpoint calculating the time difference in days between the maximum event timestamp (the most recent transaction timestamp) and the maximum ingestion timestamp (the most recent data loading timestamp)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDelayInDataLoadingInDaysCheckSpec dailyPartitionMaxDelayInDataLoadingInDays;

    @JsonPropertyDescription("Daily partition checkpoint calculating the time difference in days between the current date and the maximum ingestion timestamp (the most recent data loading timestamp) (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceLastLoadCheckSpec dailyPartitionDaysSinceLastLoad;

    /**
     * Returns a maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getDailyPartitionMaxDaysSinceMostRecentEvent() {
        return dailyPartitionMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets a maximum days since the most recent event.
     * @param dailyPartitionMaxDaysSinceMostRecentEvent New maximum days since the most recent event check.
     */
    public void setDailyPartitionMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec dailyPartitionMaxDaysSinceMostRecentEvent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxDaysSinceMostRecentEvent, dailyPartitionMaxDaysSinceMostRecentEvent));
        this.dailyPartitionMaxDaysSinceMostRecentEvent = dailyPartitionMaxDaysSinceMostRecentEvent;
        this.propagateHierarchyIdToField(dailyPartitionMaxDaysSinceMostRecentEvent, "daily_partition_max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum days since the most recent ingestion check configuration.
     * @return Maximum days since the most recent ingestion check configuration.
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getDailyPartitionMaxDaysSinceMostRecentIngestion() {
        return dailyPartitionMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a maximum days since the most recent ingestion.
     * @param dailyPartitionMaxDaysSinceMostRecentIngestion New maximum days since the most recent ingestion.
     */
    public void setDailyPartitionMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec dailyPartitionMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxDaysSinceMostRecentIngestion, dailyPartitionMaxDaysSinceMostRecentIngestion));
        this.dailyPartitionMaxDaysSinceMostRecentIngestion = dailyPartitionMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyPartitionMaxDaysSinceMostRecentIngestion, "daily_partition_max_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum delay in data loading in days check configuration.
     * @return Maximum delay in data loading in days check configuration.
     */
    public TableMaxDelayInDataLoadingInDaysCheckSpec getDailyPartitionMaxDelayInDataLoadingInDays() {
        return dailyPartitionMaxDelayInDataLoadingInDays;
    }

    /**
     * Sets a maximum delay in data loading in days check configuration.
     * @param dailyPartitionMaxDelayInDataLoadingInDays Maximum delay in data loading in days check configuration.
     */
    public void setDailyPartitionMaxDelayInDataLoadingInDays(TableMaxDelayInDataLoadingInDaysCheckSpec dailyPartitionMaxDelayInDataLoadingInDays) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxDelayInDataLoadingInDays, dailyPartitionMaxDelayInDataLoadingInDays));
        this.dailyPartitionMaxDelayInDataLoadingInDays = dailyPartitionMaxDelayInDataLoadingInDays;
        this.propagateHierarchyIdToField(dailyPartitionMaxDelayInDataLoadingInDays, "daily_partition_max_delay_in_data_loading_in_days");
    }

    /**
     * Returns a number of days since the last load check configuration.
     * @return A number of days since the last load check configuration..
     */
    public TableDaysSinceLastLoadCheckSpec getDailyPartitionDaysSinceLastLoad() {
        return dailyPartitionDaysSinceLastLoad;
    }

    /**
     * Sets a number of days since the last load check configuration.
     * @param dailyPartitionDaysSinceLastLoad A number of days since the last load check configuration.
     */
    public void setDailyPartitionDaysSinceLastLoad(TableDaysSinceLastLoadCheckSpec dailyPartitionDaysSinceLastLoad) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDaysSinceLastLoad, dailyPartitionDaysSinceLastLoad));
        this.dailyPartitionDaysSinceLastLoad = dailyPartitionDaysSinceLastLoad;
        this.propagateHierarchyIdToField(dailyPartitionDaysSinceLastLoad, "daily_partition_days_since_last_load");
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
