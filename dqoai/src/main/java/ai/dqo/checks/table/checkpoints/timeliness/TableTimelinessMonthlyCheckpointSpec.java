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
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceLastDataIngestionCheckSpec;
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
           put("monthly_checkpoint_days_since_most_recent_event", o -> o.monthlyCheckpointDaysSinceMostRecentEvent);
           put("monthly_checkpoint_data_ingestion_delay", o -> o.monthlyCheckpointDataIngestionDelay);
           put("monthly_checkpoint_days_since_last_data_ingestion", o -> o.monthlyCheckpointDaysSinceLastDataIngestion);
        }
    };

    @JsonPropertyDescription("Monthly checkpoint calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceMostRecentEventCheckSpec monthlyCheckpointDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec monthlyCheckpointDataIngestionDelay;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDaysSinceLastDataIngestionCheckSpec monthlyCheckpointDaysSinceLastDataIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return The number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getMonthlyCheckpointDaysSinceMostRecentEvent() {
        return monthlyCheckpointDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyCheckpointDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setMonthlyCheckpointDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec monthlyCheckpointDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDaysSinceMostRecentEvent, monthlyCheckpointDaysSinceMostRecentEvent));
        this.monthlyCheckpointDaysSinceMostRecentEvent = monthlyCheckpointDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyCheckpointDaysSinceMostRecentEvent, "monthly_checkpoint_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getMonthlyCheckpointDataIngestionDelay() {
        return monthlyCheckpointDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyCheckpointDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyCheckpointDataIngestionDelay(TableDataIngestionDelayCheckSpec monthlyCheckpointDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDataIngestionDelay, monthlyCheckpointDataIngestionDelay));
        this.monthlyCheckpointDataIngestionDelay = monthlyCheckpointDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyCheckpointDataIngestionDelay, "monthly_checkpoint_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDaysSinceLastDataIngestionCheckSpec getMonthlyCheckpointDaysSinceLastDataIngestion() {
        return monthlyCheckpointDaysSinceLastDataIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyCheckpointDaysSinceLastDataIngestion A number of days since the last data ingestion check configuration.
     */
    public void setMonthlyCheckpointDaysSinceLastDataIngestion(TableDaysSinceLastDataIngestionCheckSpec monthlyCheckpointDaysSinceLastDataIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDaysSinceLastDataIngestion, monthlyCheckpointDaysSinceLastDataIngestion));
        this.monthlyCheckpointDaysSinceLastDataIngestion = monthlyCheckpointDaysSinceLastDataIngestion;
        this.propagateHierarchyIdToField(monthlyCheckpointDaysSinceLastDataIngestion, "monthly_checkpoint_days_since_last_data_ingestion");
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
