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
 * Container of table level monthly checkpoints for timeliness data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_checkpoint_max_days_since_most_recent_event", o -> o.monthlyCheckpointMaxDaysSinceMostRecentEvent);
           put("monthly_checkpoint_max_data_ingestion_delay", o -> o.monthlyCheckpointMaxDataIngestionDelay);
           put("monthly_checkpoint_max_days_since_most_recent_ingestion", o -> o.monthlyCheckpointMaxDaysSinceMostRecentIngestion);
        }
    };

    @JsonPropertyDescription("Monthly checkpoint calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDataIngestionDelayCheckSpec monthlyCheckpointMaxDataIngestionDelay;

    @JsonPropertyDescription("Monthly checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyCheckpointMaxDaysSinceMostRecentIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return The number of days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getMonthlyCheckpointMaxDaysSinceMostRecentEvent() {
        return monthlyCheckpointMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyCheckpointMaxDaysSinceMostRecentEvent New days since the most recent event check.
     */
    public void setMonthlyCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec monthlyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDaysSinceMostRecentEvent, monthlyCheckpointMaxDaysSinceMostRecentEvent));
        this.monthlyCheckpointMaxDaysSinceMostRecentEvent = monthlyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(monthlyCheckpointMaxDaysSinceMostRecentEvent, "monthly_checkpoint_max_days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableMaxDataIngestionDelayCheckSpec getMonthlyCheckpointMaxDataIngestionDelay() {
        return monthlyCheckpointMaxDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyCheckpointMaxDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyCheckpointMaxDataIngestionDelay(TableMaxDataIngestionDelayCheckSpec monthlyCheckpointMaxDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDataIngestionDelay, monthlyCheckpointMaxDataIngestionDelay));
        this.monthlyCheckpointMaxDataIngestionDelay = monthlyCheckpointMaxDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyCheckpointMaxDataIngestionDelay, "monthly_checkpoint_max_data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getMonthlyCheckpointMaxDaysSinceMostRecentIngestion() {
        return monthlyCheckpointMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyCheckpointMaxDaysSinceMostRecentIngestion A number of days since the last data ingestion check configuration.
     */
    public void setMonthlyCheckpointMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec monthlyCheckpointMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDaysSinceMostRecentIngestion, monthlyCheckpointMaxDaysSinceMostRecentIngestion));
        this.monthlyCheckpointMaxDaysSinceMostRecentIngestion = monthlyCheckpointMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(monthlyCheckpointMaxDaysSinceMostRecentIngestion, "monthly_checkpoint_max_days_since_most_recent_ingestion");
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
