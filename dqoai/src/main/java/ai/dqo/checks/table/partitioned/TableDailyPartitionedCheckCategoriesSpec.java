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
package ai.dqo.checks.table.partitioned;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.table.partitioned.standard.TableStandardDailyPartitionedChecksSpec;
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
 * Container of table level daily checkpoints. Contains categories of daily checkpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDailyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableDailyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("standard", o -> o.standard);
        }
    };

    @JsonPropertyDescription("Standard daily partitioned data quality checks that verify the quality of every day of data separately")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableStandardDailyPartitionedChecksSpec standard;

    /**
     * Returns the container of daily partitioned checks for standard data quality checks.
     * @return Container of row standard data quality checks.
     */
    public TableStandardDailyPartitionedChecksSpec getStandard() {
        return standard;
    }

    /**
     * Sets the container of standard data quality checks.
     * @param standard New standard checks.
     */
    public void setStandard(TableStandardDailyPartitionedChecksSpec standard) {
        this.setDirtyIf(!Objects.equals(this.standard, standard));
        this.standard = standard;
        this.propagateHierarchyIdToField(standard, "standard");
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
