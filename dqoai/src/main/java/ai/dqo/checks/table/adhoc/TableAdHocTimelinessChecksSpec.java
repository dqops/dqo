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
package ai.dqo.checks.table.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceLastDataIngestionCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured standard data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAdHocTimelinessChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAdHocTimelinessChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("days_since_most_recent_event", o -> o.daysSinceMostRecentEvent);
            put("data_ingestion_delay", o -> o.dataIngestionDelay);
            put("days_since_last_data_ingestion", o -> o.daysSinceLastDataIngestion);
        }
    };

    @JsonPropertyDescription("Calculates the number of days since the most recent event timestamp (freshness)")
    private TableDaysSinceMostRecentEventCheckSpec daysSinceMostRecentEvent;

    @JsonPropertyDescription("Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    private TableDataIngestionDelayCheckSpec dataIngestionDelay;

    @JsonPropertyDescription("Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    private TableDaysSinceLastDataIngestionCheckSpec daysSinceLastDataIngestion;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDaysSinceMostRecentEventCheckSpec getDaysSinceMostRecentEvent() {
        return daysSinceMostRecentEvent;
    }

    /**
     * Sets the number of days since the most recent event check configuration.
     * @param daysSinceMostRecentEvent Maximum days since the most recent event check configuration.
     */
    public void setDaysSinceMostRecentEvent(TableDaysSinceMostRecentEventCheckSpec daysSinceMostRecentEvent) {
        this.setDirtyIf(!Objects.equals(this.daysSinceMostRecentEvent, daysSinceMostRecentEvent));
        this.daysSinceMostRecentEvent = daysSinceMostRecentEvent;
        propagateHierarchyIdToField(daysSinceMostRecentEvent, "days_since_most_recent_event");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDataIngestionDelay() {
        return dataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDataIngestionDelay(TableDataIngestionDelayCheckSpec dataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dataIngestionDelay, dataIngestionDelay));
        this.dataIngestionDelay = dataIngestionDelay;
        propagateHierarchyIdToField(dataIngestionDelay, "data_ingestion_delay");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration.
     */
    public TableDaysSinceLastDataIngestionCheckSpec getDaysSinceLastDataIngestion() {
        return daysSinceLastDataIngestion;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param daysSinceLastDataIngestion  A number of days since the last load check configuration.
     */
    public void setDaysSinceLastDataIngestion(TableDaysSinceLastDataIngestionCheckSpec daysSinceLastDataIngestion) {
        this.setDirtyIf(!Objects.equals(this.daysSinceLastDataIngestion, daysSinceLastDataIngestion));
        this.daysSinceLastDataIngestion = daysSinceLastDataIngestion;
        propagateHierarchyIdToField(daysSinceLastDataIngestion, "days_since_last_data_ingestion");
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
