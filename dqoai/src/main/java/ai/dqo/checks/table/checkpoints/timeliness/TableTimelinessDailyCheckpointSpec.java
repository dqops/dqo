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
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDaysBetweenEventAndIngestionCheckSpec;
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
 * Container of table level daily checkpoints for standard data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_checkpoint_max_days_since_most_recent_event", o -> o.dailyCheckpointMaxDaysSinceMostRecentEvent);
           put("daily_checkpoint_max_days_since_most_recent_ingestion", o -> o.dailyCheckpointMaxDaysSinceMostRecentIngestion);
           put("daily_checkpoint_max_days_between_event_and_ingestion", o -> o.dailyCheckpointMaxDaysBetweenEventAndIngestion);
           put("daily_checkpoint_min_days_between_event_and_ingestion", o -> o.dailyCheckpointMinDaysBetweenEventAndIngestion);
        }
    };

    @JsonPropertyDescription("Calculates maximum days since the most recent event")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent;

    @JsonPropertyDescription("Calculates maximum days since the most recent ingestion")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion;

    @JsonPropertyDescription("Calculates maximum days between event and ingestion")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMaxDaysBetweenEventAndIngestionCheckSpec dailyCheckpointMaxDaysBetweenEventAndIngestion;

    @JsonPropertyDescription("Calculates minimum days between event and ingestion")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMinDaysBetweenEventAndIngestionCheckSpec dailyCheckpointMinDaysBetweenEventAndIngestion;

    /**
     * Returns a maximum days since the most recent event check configuration.
     * @return Maximum days since the most recent event check configuration.
     */
    public TableMaxDaysSinceMostRecentEventCheckSpec getDailyCheckpointMaxDaysSinceMostRecentEvent() {
        return dailyCheckpointMaxDaysSinceMostRecentEvent;
    }

    /**
     * Sets a maximum days since the most recent event.
     * @param dailyCheckpointMaxDaysSinceMostRecentEvent New maximum days since the most recent event check.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentEvent(TableMaxDaysSinceMostRecentEventCheckSpec dailyCheckpointMaxDaysSinceMostRecentEvent) {
		this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentEvent, dailyCheckpointMaxDaysSinceMostRecentEvent));
        this.dailyCheckpointMaxDaysSinceMostRecentEvent = dailyCheckpointMaxDaysSinceMostRecentEvent;
		this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentEvent, "daily_checkpoint_max_days_since_most_recent_event");
    }

    /**
     * Returns a maximum days since the most recent ingestion check configuration.
     * @return Maximum days since the most recent ingestion check configuration.
     */
    public TableMaxDaysSinceMostRecentIngestionCheckSpec getDailyCheckpointMaxDaysSinceMostRecentIngestion() {
        return dailyCheckpointMaxDaysSinceMostRecentIngestion;
    }

    /**
     * Sets a maximum days since the most recent ingestion.
     * @param dailyCheckpointMaxDaysSinceMostRecentIngestion New maximum days since the most recent ingestion check.
     */
    public void setDailyCheckpointMaxDaysSinceMostRecentIngestion(TableMaxDaysSinceMostRecentIngestionCheckSpec dailyCheckpointMaxDaysSinceMostRecentIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysSinceMostRecentIngestion, dailyCheckpointMaxDaysSinceMostRecentIngestion));
        this.dailyCheckpointMaxDaysSinceMostRecentIngestion = dailyCheckpointMaxDaysSinceMostRecentIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDaysSinceMostRecentIngestion, "daily_checkpoint_max_days_since_most_recent_ingestion");
    }

    /**
     * Returns a maximum days between event and ingestion check configuration.
     * @return Maximum days between event and ingestion check configuration.
     */
    public TableMaxDaysBetweenEventAndIngestionCheckSpec getDailyCheckpointMaxDaysBetweenEventAndIngestion() {
        return dailyCheckpointMaxDaysBetweenEventAndIngestion;
    }

    /**
     * Sets a maximum days between event and ingestion check configuration.
     * @param dailyCheckpointMaxDaysBetweenEventAndIngestion New maximum days between event and ingestion check.
     */
    public void setDailyCheckpointMaxDaysBetweenEventAndIngestion(TableMaxDaysBetweenEventAndIngestionCheckSpec dailyCheckpointMaxDaysBetweenEventAndIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxDaysBetweenEventAndIngestion, dailyCheckpointMaxDaysBetweenEventAndIngestion));
        this.dailyCheckpointMaxDaysBetweenEventAndIngestion = dailyCheckpointMaxDaysBetweenEventAndIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointMaxDaysBetweenEventAndIngestion, "daily_checkpoint_max_days_between_event_and_ingestion");
    }

    /**
     * Returns a minimum days between event and ingestion check configuration.
     * @return Minimum days between event and ingestion check configuration.
     */
    public TableMinDaysBetweenEventAndIngestionCheckSpec getDailyCheckpointMinDaysBetweenEventAndIngestion() {
        return dailyCheckpointMinDaysBetweenEventAndIngestion;
    }

    /**
     * Sets a minimum days between event and ingestion check configuration.
     * @param dailyCheckpointMinDaysBetweenEventAndIngestion New minimum days between event and ingestion check.
     */
    public void setDailyCheckpointMinDaysBetweenEventAndIngestion(TableMinDaysBetweenEventAndIngestionCheckSpec dailyCheckpointMinDaysBetweenEventAndIngestion) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinDaysBetweenEventAndIngestion, dailyCheckpointMinDaysBetweenEventAndIngestion));
        this.dailyCheckpointMinDaysBetweenEventAndIngestion = dailyCheckpointMinDaysBetweenEventAndIngestion;
        this.propagateHierarchyIdToField(dailyCheckpointMinDaysBetweenEventAndIngestion, "daily_checkpoint_min_days_between_event_and_ingestion");
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
