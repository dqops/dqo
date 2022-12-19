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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentEventCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysSinceMostRecentIngestionCheckSpec;
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
            put("max_days_since_most_recent_event", o -> o.maxDaysSinceMostRecentEvent);
            put("max_days_since_most_recent_ingestion", o -> o.maxDaysSinceMostRecentIngestion);
        }
    };

    @JsonPropertyDescription("Calculates maximum days since the most recent event")
    private TableMaxDaysSinceMostRecentEventCheckSpec maxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Calculates maximum days since the most recent ingestion")
    private TableMaxDaysSinceMostRecentIngestionCheckSpec maxDaysSinceMostRecentIngestion;

    /**
     * Returns a maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getMaxDaysSinceMostRecentEvent() {
        return maxDaysSinceMostRecentEvent;
    }

    /**
     * Sets a maximum days since the most recent event check configuration.
     * @param maxDaysSinceMostRecentEvent Maximum days since the most recent event check configuration.
     */
    public void setMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec maxDaysSinceMostRecentEvent) {
        this.setDirtyIf(!Objects.equals(this.maxDaysSinceMostRecentEvent, maxDaysSinceMostRecentEvent));
        this.maxDaysSinceMostRecentEvent = maxDaysSinceMostRecentEvent;
        propagateHierarchyIdToField(maxDaysSinceMostRecentEvent, "max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum days since the most recent ingestion check configuration.
     * @return Maximum days since the most recent ingestion check configuration.
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getMaxDaysSinceMostRecentIngestion() {
        return maxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a maximum days since the most recent ingestion check configuration.
     * @param maxDaysSinceMostRecentIngestion Maximum days since the most recent ingestion check configuration.
     */
    public void setMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec maxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.maxDaysSinceMostRecentIngestion, maxDaysSinceMostRecentIngestion));
        this.maxDaysSinceMostRecentIngestion = maxDaysSinceMostRecentIngestion;
        propagateHierarchyIdToField(maxDaysSinceMostRecentIngestion, "max_days_since_most_recent_ingestion");
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
