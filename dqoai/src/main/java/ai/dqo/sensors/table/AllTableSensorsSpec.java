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
package ai.dqo.sensors.table;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import ai.dqo.sensors.table.consistency.TableConsistencyRowCountSensorParametersSpec;
import ai.dqo.sensors.table.relevance.TableRelevanceMovingWeekAverageSensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessAverageDelaySensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessColumnDatetimeDifferencePercentSensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessCurrentDelaySensorParametersSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * All table sensor definitions. Just pick one.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AllTableSensorsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AllTableSensorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("consistency_row_count", o -> o.consistencyRowCount);
            put("relevance_moving_week_average", o -> o.relevanceMovingWeekAverage);
            put("timeliness_column_datetime_difference_percent", o -> o.timelinessColumnDatetimeDifferencePercent);
            put("timeliness_average_delay", o -> o.timelinessAverageDelay);
            put("timeliness_current_delay", o -> o.timelinessCurrentDelay);
        }
    };

    @JsonPropertyDescription("Column datetime difference sensor that calculates the percent of datetime differences of two datetime columns which are above defined threshold.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessColumnDatetimeDifferencePercentSensorParametersSpec timelinessColumnDatetimeDifferencePercent;

    @JsonPropertyDescription("Average delay sensor that calculates the percent of datetime differences of two datetime columns which are above defined threshold.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessAverageDelaySensorParametersSpec timelinessAverageDelay;

    @JsonPropertyDescription("Row count sensors for checking the consistency of row counts.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableConsistencyRowCountSensorParametersSpec consistencyRowCount;

    @JsonPropertyDescription("Current delay sensor that calculates the timestamp difference between current timestamp and the latest record in provided clumn.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessCurrentDelaySensorParametersSpec timelinessCurrentDelay;

    // TODO update description
    @JsonPropertyDescription("Moving week average sensor...")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRelevanceMovingWeekAverageSensorParametersSpec relevanceMovingWeekAverage;

    // TODO: add remaining test, the names are "category" + "_" + sensor name

    /**
     * Returns a row count sensor that counts rows.
     * @return Row count sensor.
     */
    public TableConsistencyRowCountSensorParametersSpec getConsistencyRowCount() {
        return consistencyRowCount;
    }

    /**
     * Returns a moving week average sensor...
     * @return
     */
    public TableRelevanceMovingWeekAverageSensorParametersSpec getRelevanceMovingWeekAverage() {
        return relevanceMovingWeekAverage;
    }

    /**
     * Returns a column datetime difference percent.
     * @return  Column datetime difference percent.
     */
    public TableTimelinessColumnDatetimeDifferencePercentSensorParametersSpec getTimelinessColumnDatetimeDifferencePercent() {
        return timelinessColumnDatetimeDifferencePercent;
    }

    /**
     * Returns a current delay sensor.
     * @return Current delay sensor.
     */
    public TableTimelinessCurrentDelaySensorParametersSpec getTimelinessCurrentDelay() {
        return timelinessCurrentDelay;
    }

    /**
     * Sets a datetime difference percent sensor that should be used.
     * @param timelinessColumnDatetimeDifferencePercent
     */
    public void setTimelinessColumnDatetimeDifferencePercent(TableTimelinessColumnDatetimeDifferencePercentSensorParametersSpec timelinessColumnDatetimeDifferencePercent) {
        this.setDirtyIf(!Objects.equals(this.timelinessColumnDatetimeDifferencePercent, timelinessColumnDatetimeDifferencePercent));
        this.timelinessColumnDatetimeDifferencePercent = timelinessColumnDatetimeDifferencePercent;
        propagateHierarchyIdToField(timelinessColumnDatetimeDifferencePercent, "timeliness_column_datetime_difference_percent");
    }

    /**
     * Returns a average delay.
     * @return  Average delay.
     */
    public TableTimelinessAverageDelaySensorParametersSpec getTimelinessAverageDelay() {
        return timelinessAverageDelay;
    }

    /**
     * Sets average delay sensor that should be used.
     * @param timelinessAverageDelay
     */
    public void setTimelinessAverageDelay(TableTimelinessAverageDelaySensorParametersSpec timelinessAverageDelay) {
        this.setDirtyIf(!Objects.equals(this.timelinessAverageDelay, timelinessAverageDelay));
        this.timelinessAverageDelay = timelinessAverageDelay;
        propagateHierarchyIdToField(timelinessAverageDelay, "timeliness_average_delay");
    }

    /**
     * Sets a row count sensor that should be used.
     * @param consistencyRowCount Row count sensor.
     */
    public void setConsistencyRowCount(TableConsistencyRowCountSensorParametersSpec consistencyRowCount) {
		this.setDirtyIf(!Objects.equals(this.consistencyRowCount, consistencyRowCount));
        this.consistencyRowCount = consistencyRowCount;
		propagateHierarchyIdToField(consistencyRowCount, "consistency_row_count");
    }

    public void setRelevanceMovingWeekAverage(TableRelevanceMovingWeekAverageSensorParametersSpec relevanceMovingWeekAverage) {
        this.setDirtyIf(!Objects.equals(this.relevanceMovingWeekAverage, relevanceMovingWeekAverage));
        this.relevanceMovingWeekAverage = relevanceMovingWeekAverage;
        propagateHierarchyIdToField(relevanceMovingWeekAverage, "relevance_moving_week_average");
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

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
