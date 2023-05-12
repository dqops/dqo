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
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentIngestionCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TablePartitionReloadLagCheckSpec;
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
           put("monthly_partition_days_since_most_recent_event", o -> o.monthlyPartitionDaysSinceMostRecentEvent);
           put("monthly_partition_data_ingestion_delay", o -> o.monthlyPartitionDataIngestionDelay);
           put("monthly_partition_days_since_most_recent_ingestion", o -> o.monthlyPartitionDaysSinceMostRecentIngestion);
           put("monthly_partition_reload_lag", o -> o.monthlyPartitionReloadLag);
        }
    };

    @JsonPropertyDescription("Monthly partition checkpoint calculating the number of days since the most recent event (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentEventCheckSpec monthlyPartitionDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec monthlyPartitionDataIngestionDelay;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentIngestionCheckSpec monthlyPartitionDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Monthly partition checkpoint calculating the longest time a row waited to be load")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionReloadLagCheckSpec monthlyPartitionReloadLag;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getMonthlyPartitionDaysSinceMostRecentEvent() {
        return monthlyPartitionDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyPartitionDaysSinceMostRecentEvent New  days since the most recent event check.
     */
    public void setMonthlyPartitionDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec monthlyPartitionDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionDaysSinceMostRecentEvent, monthlyPartitionDaysSinceMostRecentEvent));
        this.monthlyPartitionDaysSinceMostRecentEvent = monthlyPartitionDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyPartitionDaysSinceMostRecentEvent, "monthly_partition_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getMonthlyPartitionDataIngestionDelay() {
        return monthlyPartitionDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyPartitionDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyPartitionDataIngestionDelay(TableDataIngestionDelayCheckSpec monthlyPartitionDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDataIngestionDelay, monthlyPartitionDataIngestionDelay));
        this.monthlyPartitionDataIngestionDelay = monthlyPartitionDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyPartitionDataIngestionDelay, "monthly_partition_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDaysSinceMostRecentIngestionCheckSpec getMonthlyPartitionDaysSinceMostRecentIngestion() {
        return monthlyPartitionDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyPartitionDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setMonthlyPartitionDaysSinceMostRecentIngestion(TableDaysSinceMostRecentIngestionCheckSpec monthlyPartitionDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDaysSinceMostRecentIngestion, monthlyPartitionDaysSinceMostRecentIngestion));
        this.monthlyPartitionDaysSinceMostRecentIngestion = monthlyPartitionDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(monthlyPartitionDaysSinceMostRecentIngestion, "monthly_partition_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum row data ingestion delay check configuration.
     * @return A maximum row data ingestion delay check configuration.
     */
    public TablePartitionReloadLagCheckSpec getMonthlyPartitionReloadLag() {
        return monthlyPartitionReloadLag;
    }

    /**
     * Sets a maximum row data ingestion delay.
     * @param monthlyPartitionReloadLag New maximum row data ingestion delay.
     */
    public void setMonthlyPartitionReloadLag(TablePartitionReloadLagCheckSpec monthlyPartitionReloadLag) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionReloadLag, monthlyPartitionReloadLag));
        this.monthlyPartitionReloadLag = monthlyPartitionReloadLag;
        this.propagateHierarchyIdToField(monthlyPartitionReloadLag, "monthly_partition_reload_lag");
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
