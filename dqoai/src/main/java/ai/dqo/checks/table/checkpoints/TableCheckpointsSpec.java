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
package ai.dqo.checks.table.checkpoints;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level checkpoints, divided by the time window (daily, monthly, etc.)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableCheckpointsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("daily", o -> o.daily);
            put("monthly", o -> o.monthly);
        }
    };

    @JsonPropertyDescription("Configuration of daily checkpoints evaluated at a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDailyCheckpointCategoriesSpec daily;

    @JsonPropertyDescription("Configuration of monthly checkpoints evaluated at a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMonthlyCheckpointCategoriesSpec monthly;

    // TODO: add other time periods that make sense (hourly, weekly, etc.)

    /**
     * Returns daily checkpoints.
     * @return Daily checkpoints.
     */
    public TableDailyCheckpointCategoriesSpec getDaily() {
        return daily;
    }

    /**
     * Sets the daily check points container.
     * @param daily New daily check points container.
     */
    public void setDaily(TableDailyCheckpointCategoriesSpec daily) {
		this.setDirtyIf(!Objects.equals(this.daily, daily));
        this.daily = daily;
		this.propagateHierarchyIdToField(daily, "daily");
    }

    /**
     * Returns monthly checkpoints.
     * @return Monthly checkpoints.
     */
    public TableMonthlyCheckpointCategoriesSpec getMonthly() {
        return monthly;
    }

    /**
     * Sets the monthly check points container.
     * @param monthly New monthly check points container.
     */
    public void setMonthly(TableMonthlyCheckpointCategoriesSpec monthly) {
        this.setDirtyIf(!Objects.equals(this.monthly, monthly));
        this.monthly = monthly;
        this.propagateHierarchyIdToField(monthly, "monthly");
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
