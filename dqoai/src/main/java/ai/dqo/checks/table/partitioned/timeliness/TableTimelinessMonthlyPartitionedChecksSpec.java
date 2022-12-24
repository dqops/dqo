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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxRowDataIngestionDelayCheckSpec;
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
 * Container of table level monthly partitioned timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_partition_max_days_since_most_recent_event", o -> o.monthlyPartitionMaxDaysSinceMostRecentEvent);
           put("monthly_partition_max_data_ingestion_delay", o -> o.monthlyPartitionMaxDataIngestionDelay);
           put("monthly_partition_max_days_since_most_recent_ingestion", o -> o.monthlyPartitionMaxDaysSinceMostRecentIngestion);
           put("monthly_partition_max_row_data_ingestion_delay", o -> o.monthlyPartitionMaxRowDataIngestionDelay);
        }
    };

    @JsonPropertyDescription("Monthly partition checkpoint calculating the number of days since the most recent event (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec monthlyPartitionMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDataIngestionDelayCheckSpec monthlyPartitionMaxDataIngestionDelay;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyPartitionMaxDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the longest time a row waited to be load")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxRowDataIngestionDelayCheckSpec monthlyPartitionMaxRowDataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getMonthlyPartitionMaxDaysSinceMostRecentEvent() {
        return monthlyPartitionMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyPartitionMaxDaysSinceMostRecentEvent New  days since the most recent event check.
     */
    public void setMonthlyPartitionMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec monthlyPartitionMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDaysSinceMostRecentEvent, monthlyPartitionMaxDaysSinceMostRecentEvent));
        this.monthlyPartitionMaxDaysSinceMostRecentEvent = monthlyPartitionMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyPartitionMaxDaysSinceMostRecentEvent, "monthly_partition_max_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableMaxDataIngestionDelayCheckSpec getMonthlyPartitionMaxDataIngestionDelay() {
        return monthlyPartitionMaxDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyPartitionMaxDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyPartitionMaxDataIngestionDelay(TableMaxDataIngestionDelayCheckSpec monthlyPartitionMaxDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDataIngestionDelay, monthlyPartitionMaxDataIngestionDelay));
        this.monthlyPartitionMaxDataIngestionDelay = monthlyPartitionMaxDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyPartitionMaxDataIngestionDelay, "monthly_partition_max_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getMonthlyPartitionMaxDaysSinceMostRecentIngestion() {
        return monthlyPartitionMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyPartitionMaxDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setMonthlyPartitionMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyPartitionMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDaysSinceMostRecentIngestion, monthlyPartitionMaxDaysSinceMostRecentIngestion));
        this.monthlyPartitionMaxDaysSinceMostRecentIngestion = monthlyPartitionMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(monthlyPartitionMaxDaysSinceMostRecentIngestion, "monthly_partition_max_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum row data ingestion delay check configuration.
     * @return A maximum row data ingestion delay check configuration.
     */
    public TableMaxRowDataIngestionDelayCheckSpec getMonthlyPartitionMaxRowDataIngestionDelay() {
        return monthlyPartitionMaxRowDataIngestionDelay;
    }

    /**
     * Sets a maximum row data ingestion delay.
     * @param monthlyPartitionMaxRowDataIngestionDelay New maximum row data ingestion delay.
     */
    public void setMonthlyPartitionMaxRowDataIngestionDelay(TableMaxRowDataIngestionDelayCheckSpec monthlyPartitionMaxRowDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxRowDataIngestionDelay, monthlyPartitionMaxRowDataIngestionDelay));
        this.monthlyPartitionMaxRowDataIngestionDelay = monthlyPartitionMaxRowDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyPartitionMaxRowDataIngestionDelay, "monthly_partition_max_row_data_ingestion_delay");
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
