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
package ai.dqo.checks.table.timeliness;

import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured timeliness checks executed on a column level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BuiltInTableTimelinessChecksSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<BuiltInTableTimelinessChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("current_delay", o -> o.currentDelay);
            put("column_datetime_difference_percent", o -> o.columnDatetimeDifferencePercent);
            put("average_delay", o -> o.averageDelay);
        }
    };

    @JsonPropertyDescription("Verifies that the difference between current timestamp and latest record in a column meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessCurrentDelayCheckSpec currentDelay;

    @JsonPropertyDescription("Verifies that the time difference between two datetime columns meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessColumnDatetimeDifferencePercentCheckSpec columnDatetimeDifferencePercent;

    @JsonPropertyDescription("Verifies that the timestamp difference between two columns meets the required rules, like a moving average.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessAverageDelayCheckSpec averageDelay;

    /**
     * Returns a minimum current delay check.
     * @return Minimum current delay check.
     */
    public TableTimelinessCurrentDelayCheckSpec getCurrentDelay() {
        return currentDelay;
    }

    /**
     * Sets a new definition of a current delay check.
     * @param currentDelay Row count check.
     */
    public void setCurrentDelay(TableTimelinessCurrentDelayCheckSpec currentDelay) {
        this.setDirtyIf(!Objects.equals(this.currentDelay, currentDelay));
        this.currentDelay = currentDelay;
        propagateHierarchyIdToField(currentDelay, "current_delay");
    }

    /**
     * Returns a column datetime difference percent check.
     * @return Column datetime difference percent check.
     */
    public TableTimelinessColumnDatetimeDifferencePercentCheckSpec getColumnDatetimeDifferencePercent() {
        return columnDatetimeDifferencePercent;
    }

    /**
     * Sets a new definition of a column datetime difference check.
     * @param columnDatetimeDifferencePercent Column datetime difference percent check.
     */
    public void setColumnDatetimeDifferencePercent(TableTimelinessColumnDatetimeDifferencePercentCheckSpec columnDatetimeDifferencePercent) {
        this.setDirtyIf(!Objects.equals(this.columnDatetimeDifferencePercent, columnDatetimeDifferencePercent));
        this.columnDatetimeDifferencePercent = columnDatetimeDifferencePercent;
        propagateHierarchyIdToField(columnDatetimeDifferencePercent, "column_datetime_difference_percent");
    }

    /**
     * Returns average delay check.
     * @return Average delay check.
     */
    public TableTimelinessAverageDelayCheckSpec getAverageDelay() {
        return averageDelay;
    }

    /**
     * Sets a new definition of average delay check.
     * @param averageDelay Average delay check.
     */
    public void setAverageDelay(TableTimelinessAverageDelayCheckSpec averageDelay) {
        this.setDirtyIf(!Objects.equals(this.averageDelay, averageDelay));
        this.averageDelay = averageDelay;
        propagateHierarchyIdToField(averageDelay, "average_delay");
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
