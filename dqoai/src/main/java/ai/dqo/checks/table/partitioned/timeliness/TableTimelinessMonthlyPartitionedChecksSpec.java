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
import ai.dqo.checks.table.checkspecs.timeliness.TableMinDaysBetweenEventAndIngestionCheckSpec;
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
 * Container of table level monthly partitioned standard data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_partition_max_days_since_most_recent_event", o -> o.monthlyPartitionMaxDaysSinceMostRecentEvent);
           put("monthly_partition_max_days_since_most_recent_ingestion", o -> o.monthlyPartitionMaxDaysSinceMostRecentIngestion);
           put("monthly_partition_max_delay_in_data_loading_in_days", o -> o.monthlyPartitionMaxDelayInDataLoadingInDays);
           put("monthly_partition_min_days_between_event_and_ingestion", o -> o.monthlyPartitionMinDaysBetweenEventAndIngestion);
        }
    };

    @JsonPropertyDescription("Monthly partition checkpoint calculating the maximum days since the most recent event")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec monthlyPartitionMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the maximum days since the most recent ingestion")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyPartitionMaxDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the time difference in days between the maximum event timestamp (the most recent transaction timestamp) and the maximum ingestion timestamp (the most recent data loading timestamp)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDelayInDataLoadingInDaysCheckSpec monthlyPartitionMaxDelayInDataLoadingInDays;

    @JsonPropertyDescription("Calculates minimum days between event and ingestion")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMinDaysBetweenEventAndIngestionCheckSpec monthlyPartitionMinDaysBetweenEventAndIngestion;

    /**
     * Returns a maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getMonthlyPartitionMaxDaysSinceMostRecentEvent() {
        return monthlyPartitionMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets a maximum days since the most recent event.
     * @param monthlyPartitionMaxDaysSinceMostRecentEvent New maximum days since the most recent event check.
     */
    public void setMonthlyPartitionMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec monthlyPartitionMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDaysSinceMostRecentEvent, monthlyPartitionMaxDaysSinceMostRecentEvent));
        this.monthlyPartitionMaxDaysSinceMostRecentEvent = monthlyPartitionMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyPartitionMaxDaysSinceMostRecentEvent, "monthly_partition_max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum days since the most recent ingestion check configuration.
     * @return Maximum days since the most recent ingestion check configuration.
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getMonthlyPartitionMaxDaysSinceMostRecentIngestion() {
        return monthlyPartitionMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a maximum days since the most recent ingestion.
     * @param monthlyPartitionMaxDaysSinceMostRecentIngestion New maximum days since the most recent ingestion check.
     */
    public void setMonthlyPartitionMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyPartitionMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDaysSinceMostRecentIngestion, monthlyPartitionMaxDaysSinceMostRecentIngestion));
        this.monthlyPartitionMaxDaysSinceMostRecentIngestion = monthlyPartitionMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(monthlyPartitionMaxDaysSinceMostRecentIngestion, "monthly_partition_max_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum delay in data loading in days check configuration.
     * @return Maximum delay in data loading in days check configuration.
     */
    public TableMaxDelayInDataLoadingInDaysCheckSpec getMonthlyPartitionMaxDelayInDataLoadingInDays() {
        return monthlyPartitionMaxDelayInDataLoadingInDays;
    }

    /**
     * SSets a maximum delay in data loading in days check configuration.
     * @param monthlyPartitionMaxDelayInDataLoadingInDays Maximum delay in data loading in days check configuration.
     */
    public void setMonthlyPartitionMaxDelayInDataLoadingInDays(TableMaxDelayInDataLoadingInDaysCheckSpec monthlyPartitionMaxDelayInDataLoadingInDays) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDelayInDataLoadingInDays, monthlyPartitionMaxDelayInDataLoadingInDays));
        this.monthlyPartitionMaxDelayInDataLoadingInDays = monthlyPartitionMaxDelayInDataLoadingInDays;
        this.propagateHierarchyIdToField(monthlyPartitionMaxDelayInDataLoadingInDays, "monthly_partition_max_delay_in_data_loading_in_days");
    }

    /**
     * Returns a minimum days between event and ingestion check configuration.
     * @return Minimum days between event and ingestion check configuration.
     */
    public TableMinDaysBetweenEventAndIngestionCheckSpec getMonthlyPartitionMinDaysBetweenEventAndIngestion() {
        return monthlyPartitionMinDaysBetweenEventAndIngestion;
    }

    /**
     * Sets a minimum days between event and ingestion check configuration.
     * @param monthlyPartitionMinDaysBetweenEventAndIngestion New minimum days between event and ingestion check.
     */
    public void setMonthlyPartitionMinDaysBetweenEventAndIngestion(TableMinDaysBetweenEventAndIngestionCheckSpec monthlyPartitionMinDaysBetweenEventAndIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinDaysBetweenEventAndIngestion, monthlyPartitionMinDaysBetweenEventAndIngestion));
        this.monthlyPartitionMinDaysBetweenEventAndIngestion = monthlyPartitionMinDaysBetweenEventAndIngestion;
        this.propagateHierarchyIdToField(monthlyPartitionMinDaysBetweenEventAndIngestion, "monthly_partition_min_days_between_event_and_ingestion");
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
